package com.hd._07;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;


public class NettyServer {
    public static void main(String[] args) {
        //1、启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2、BossEventLoopGroup,WorkerEventLoopGroup
                .group(new NioEventLoopGroup())
                //3、选择一个服务器ServerSocketChannel的实现
                .channel(NioServerSocketChannel.class)
                //4、boss负责处理连接，Worker（child）负责处理读写,handler决定worker能做那些操作
                .childHandler(
                        //channel代表和客户端进行数据读写的通道，Initializer 初始化，负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        //将bytebuf的转换成字符串
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        //自定义handler
                        nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            //打印上一步绑定的字符串
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //绑定的监听端口
                .bind(8888);//4
    }
}
