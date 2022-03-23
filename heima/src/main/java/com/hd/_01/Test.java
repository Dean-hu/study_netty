package com.hd._01;

import io.netty.channel.ChannelFuture;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {
    public static void main(String[] args) {
        //FileChannel 获取 1.输入输出流 2.RandomAccessFile
        try {
            FileChannel channel = new FileInputStream("src/data.txt").getChannel();
            //准备缓冲区
            ByteBuffer bu=ByteBuffer.allocate(5);
            //从Channel 读取数据,向buffer写入
            while(channel.read(bu)!=-1) {
                //获取数据
                bu.flip();//切换到读模式
                while (bu.hasRemaining()) {//是否还有数据
                    byte b = bu.get();
                    System.out.println((char) b);
                }
                //切换回写模式
                bu.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
