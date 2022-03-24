package com.hd._04;

import org.jcp.xml.dsig.internal.MacOutputStream;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc=SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8888));
        int count=0;
       while(true) {
           ByteBuffer buffer=ByteBuffer.allocate(1024*1024);
           count+=sc.read(buffer);
           System.out.println(count);
       }

    }
}