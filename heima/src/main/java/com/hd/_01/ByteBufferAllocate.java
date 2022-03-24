package com.hd._01;

import java.nio.ByteBuffer;
//讨论对于内存的分配
public class ByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        //class java.nio.HeapByteBuffer   堆内存，读写效率低，受到java垃圾回收的影响
        //垃圾回收会涉及到数据的移动，降低效率
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        //class java.nio.DirectByteBuffer 直接内存，不受java垃圾回收的影响
        //由操作系统分配内存，分配的效率低一点，不及时释放，有可能会造成内存溢出

    }
}
