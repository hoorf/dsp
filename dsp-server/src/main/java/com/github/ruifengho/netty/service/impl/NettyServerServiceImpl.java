package com.github.ruifengho.netty.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ruifengho.config.ConfigReader;
import com.github.ruifengho.netty.handler.DspServerHandler;
import com.github.ruifengho.netty.service.NettyServerService;
import com.github.ruifengho.netty.service.NettyService;

import io.netty.bootstrap.ServerBootstrap;
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

@Service
public class NettyServerServiceImpl implements NettyServerService {

	private static final Logger log = LoggerFactory.getLogger(NettyServerServiceImpl.class);

	private ExecutorService threadpool = Executors.newFixedThreadPool(100);

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	@Autowired
	private ConfigReader configReader;
	@Autowired
	private NettyService nettyService;

	@Override
	public synchronized void start() {
		try {

			bossGroup = new NioEventLoopGroup(50);
			workerGroup = new NioEventLoopGroup();
			int heart = configReader.getHeart();
			int port = configReader.getPort();
			String host = configReader.getHost();
			DspServerHandler handler = new DspServerHandler(threadpool, nettyService);

			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup, workerGroup);
			boot.channel(NioServerSocketChannel.class);
			boot.option(ChannelOption.SO_BACKLOG, 100);
			boot.handler(new LoggingHandler(LogLevel.INFO));
			boot.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
//					sc.pipeline().addLast("timeout", new IdleStateHandler(10, 0, 0));
					sc.pipeline().addLast(new LengthFieldPrepender(4, false));
					sc.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
					sc.pipeline().addLast(handler);

				}
			});

			log.info("connection socket-> host:" + host + ",port:" + port);
			boot.bind(port);
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
