package com.hd._01;

import java.nio.ByteBuffer;

public class ByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','4'});
        buffer.flip();
        //从头开始读：
        byte [] a =new byte[4];
        //mark & reset    对于rewind的增强
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.mark();//做标记
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.reset();//重置到mark的位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        // get(i) 方法，不会改变position读指针

    }

}
