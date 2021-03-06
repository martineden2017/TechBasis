package com.xter.ifornetty;

import com.xter.util.L;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateEvent;
@ChannelHandler.Sharable
public class NormalClientHandler extends SimpleChannelInboundHandler<NormalMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, NormalMessage normalMessage) throws Exception {
		L.d(Thread.currentThread().getName());
		L.d(normalMessage.toString());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		L.d("------active-------"+ctx.channel().remoteAddress().toString());
		L.d(Thread.currentThread().getName()+","+this.hashCode());
		for (int i = 0; i < 3; i++) {
			String content = "data" + i;
			ctx.writeAndFlush(new NormalMessage(1, content.length(), content));
			TimeUnit.MILLISECONDS.sleep(200);
		}
//		ctx.disconnect();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		L.d("--------"+ctx.channel().isActive());

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}
