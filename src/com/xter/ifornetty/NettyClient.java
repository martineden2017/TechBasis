package com.xter.ifornetty;

import com.xter.ifornetty.codec.NormalClientEncoder;
import com.xter.ifornetty.common.NettyConnector;
import com.xter.util.L;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient {
	public static void main(String[] args) throws InterruptedException {
		NettyConnector connector = new NettyConnector.Builder()
				.group(new NioEventLoopGroup())
				.handler(new IdleStateHandler(10, 10, 10),
						new NormalClientEncoder(),
						new NormalClientHeartBeatHandler(),
						new NormalClientHandler())
				.build();

		connector.setRemoteAddress("192.168.0.102", 8000);
		connector.setChannelStateListener(new NettyConnector.IChannelStateListener() {
			@Override
			public void onConnectSuccess(Channel channel) {
				L.d("连接"+channel.remoteAddress().toString());
			}

			@Override
			public void onConnectFailed() {

			}

			@Override
			public void onDisconnect() {
				L.d("已断开");
				connector.reconnect(5000,25);
			}
		});
		connector.connect();


//		NioEventLoopGroup group = new NioEventLoopGroup();
//
//		try {
//			Bootstrap bootstrap = new Bootstrap()
//					.group(group)
//					.channel(NioSocketChannel.class)
//					.handler(new ChannelInitializer<Channel>() {
//						@Override
//						protected void initChannel(Channel ch) {
//							ch.pipeline().addLast(new IdleStateHandler(10, 10, 10));
//							ch.pipeline().addLast(new NormalClientEncoder());
//							ch.pipeline().addLast(new NormalClientHeartBeatHandler());
//							ch.pipeline().addLast(new NormalClientHandler());
//						}
//					});
//
//			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8000);
//			L.d("done?");
//
//			channelFuture.channel().closeFuture().sync();
//			L.d("done?");

//		}finally {
//			group.shutdownGracefully();
//		}

	}
}