package com.hd._04;

import lombok.extern.slf4j.Slf4j;
import org.jcp.xml.dsig.internal.MacOutputStream;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc=SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8888));
        sc.write(StandardCharsets.UTF_8.encode("hello"));
        sc.close();
    }
}
