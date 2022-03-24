package com.hd._04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc=ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector s=Selector.open();
        ssc.register(s, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8888));
        while(true){
            //阻塞方法，监听是否有连接请求
            s.select();
            Iterator<SelectionKey> iter = s.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key=iter.next();
                iter.remove();
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel sc=channel.accept();
                StringBuilder a=new StringBuilder();
                for(int i=0;i<500000000;i++)
                     a.append('a');
                ByteBuffer buffer =StandardCharsets.UTF_8.encode(a.toString());
                while(buffer.hasRemaining()) {
                    System.out.println(sc.write(buffer));
                }
            }
        }
    }
}
