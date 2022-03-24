package com.hd._01;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class ScatteringRead {
    public static void main(String[] args) {
        try {
            FileChannel fileChannel = new RandomAccessFile("src/data.txt", "r").getChannel();
            ByteBuffer a=ByteBuffer.allocate(3);
            ByteBuffer b=ByteBuffer.allocate(4);
            ByteBuffer c=ByteBuffer.allocate(5);
            fileChannel.read(new ByteBuffer[]{a,b,c});
            a.flip();
            b.flip();
            c.flip();
            System.out.println(StandardCharsets.UTF_8.decode(a));
            System.out.println(StandardCharsets.UTF_8.decode(b));
            System.out.println(StandardCharsets.UTF_8.decode(c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
