package com.hd._01;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class GatheringWrite {
    public static void main(String[] args) {
        ByteBuffer a= StandardCharsets.UTF_8.encode("你好");
        ByteBuffer b= StandardCharsets.UTF_8.encode("我是");
        ByteBuffer c= StandardCharsets.UTF_8.encode("胡鼎");
        try {
            FileChannel channel = new RandomAccessFile("src/data.txt","rw").getChannel();
            channel.write(new ByteBuffer[]{a,b,c});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
