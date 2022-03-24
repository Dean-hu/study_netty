package com.hd._03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc=SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8888));
        sc.write(StandardCharsets.UTF_8.encode("你好我是胡鼎，你想和我玩嘛》》》？？？？？\n"));
        sc.close();
    }
}
