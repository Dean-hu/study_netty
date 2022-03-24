package com.hd._02;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class server {
    public final static Logger log = LoggerFactory.getLogger(server.class);
    public static void main(String[] args) throws IOException {

        ByteBuffer buffer =ByteBuffer.allocate(16);
        //理解nio的阻塞模式,单线程
        //创建一个服务器
        ServerSocketChannel socket=ServerSocketChannel.open();
        //绑定监听端口
        socket.bind(new InetSocketAddress(8888));
        //3 accept集合
        List<SocketChannel> channels=new ArrayList<>();
        socket.configureBlocking(false);
        while(true) {
            // SocketChannel用来与客户端通信
            SocketChannel channel= socket.accept();

            if(channel!=null) {
                channel.configureBlocking(false);
                channels.add(channel);
            }
                for (SocketChannel c : channels) {
                    //接受数据
//                    log.debug("before read ...{}", channel);
                    int read= c.read(buffer);
                    if(read>0) {
                        buffer.flip();
                        System.out.println(StandardCharsets.UTF_8.decode(buffer));
                        buffer.clear();
//                        log.debug("after read ...{}", channel);
                    }
                }
            }
        }
    }
