package com.github.ruifengho.netty.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ruifengho.config.ConfigLoader;
import com.github.ruifengho.config.Constants;
import com.github.ruifengho.netty.NettyClientService;
import com.github.ruifengho.netty.NettyControlService;
import com.github.ruifengho.netty.handler.DspClientConnectHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

@Service
public class NettyClientServiceImpl implements NettyClientService {

	private static volatile boolean isStarting = false;

	private EventLoopGroup workerGroup;

	private static final Logger log = LoggerFactory.getLogger(NettyClientServiceImpl.class);

	@Autowired
	private ConfigLoader configLoader;
	@Autowired
	private NettyControlService nettyControlService;

	@Override
	public void connect() {

		if (isStarting) {
			return;
		}

		// 加载配置
		configLoader.load();
		int heart = Constants.config.getHeart();
		String host = Constants.config.getHost();
		int port = Constants.config.getPort();

		DspClientConnectHandler handler = new DspClientConnectHandler(nettyControlService);

		try {

			workerGroup = new NioEventLoopGroup();

			Bootstrap boot = new Bootstrap();
			boot.group(workerGroup);
			boot.channel(NioSocketChannel.class);
			boot.option(ChannelOption.SO_KEEPALIVE, false);
			boot.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("timeout", new IdleStateHandler(18, 15, 10));
					ch.pipeline().addLast(new LengthFieldPrepender(4, false));
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
					ch.pipeline().addLast(handler);
				}

			});
			log.info("connect to dsp-server");
			ChannelFuture channelFuture = boot.connect(host, port);
			log.info("connect success");
			// 断线重连
			channelFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						System.err.println("why lose");
						future.channel().eventLoop().schedule(new Runnable() {
							@Override
							public void run() {
								isStarting = false;
								connect();
							}
						}, 5, TimeUnit.SECONDS);
					}
				}

			});
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

	}

	@Override
	public void disconnect() {
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}

	}

}
