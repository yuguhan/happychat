package com.xuyp.happychat.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WSServer {

    private static class SingletionWSServer{
        static final WSServer instance = new WSServer();
    }
    public static WSServer getInstance() {
        return SingletionWSServer.instance;
    }

    private final EventLoopGroup main;
    private final EventLoopGroup sub;
    private final ServerBootstrap server;
    private ChannelFuture future;

    public WSServer(){
        main = new NioEventLoopGroup();
        sub = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(main, sub)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        //http的编解码器
                        channelPipeline.addLast(new HttpServerCodec());
                        //写大数据流的支持
                        channelPipeline.addLast(new ChunkedWriteHandler());
                        //对httpMessage进行聚合，成为FullHttpRequest或response
                        channelPipeline.addLast(new HttpObjectAggregator(1024 * 64));
                        //空闲状态处理
                        channelPipeline.addLast(new IdleStateHandler(120, 120, 120));

                        channelPipeline.addLast(new HeartBeatHandler());
                        //路由，处理繁重复杂的事，比如握手
                        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                        //自定义handler
                        channelPipeline.addLast(new ChatHandler());

                    }
                });
    }
    public void start() {
        this.future = server.bind(8089);
        log.info("netty webSocket server 启动成功。。。");
    }
}
