package com.github.ruifengho.netty.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.ruifengho.config.Constants;
import com.github.ruifengho.netty.service.NettyService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

@Service
public class NettyServiceImpl implements NettyService {

	private static final Logger log = LoggerFactory.getLogger(NettyServiceImpl.class);

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	@Override
	public synchronized void start() {
		try {

			bossGroup = new NioEventLoopGroup(50);
			workerGroup = new NioEventLoopGroup();

			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup, workerGroup);
			boot.channel(NioServerSocketChannel.class);
			boot.option(ChannelOption.SO_BACKLOG, 100);
			boot.handler(new LoggingHandler(LogLevel.INFO));
			boot.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast("timeout",
							new IdleStateHandler(Constants.heart, Constants.heart, Constants.heart, TimeUnit.SECONDS));
					sc.pipeline().addLast(new LengthFieldPrepender(4, false));
					sc.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

				}
			});

			log.info("connection socket-> host:" + Constants.nettyHost + ",port:" + Constants.nettyPort);
			boot.bind(Constants.nettyPort);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		log.info("netty 已启动");

	}

	@Override
	public synchronized void close() {
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();

		}

	}

}
