package com.hd._01;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ByteBufferString {
      //字符串转为ByteBuffer
      public static void main(String[] args) {
          ByteBuffer buffer=ByteBuffer.allocate(16);
          //put字节数组
          buffer.put("hello".getBytes());
          //charSet   直接转换为读模式
          ByteBuffer buffer1=StandardCharsets.UTF_8.encode("hello");
          //warp方法    直接转换为读模式
          ByteBuffer.wrap("hello".getBytes());
              buffer.flip();
          System.out.println(StandardCharsets.UTF_8.decode(buffer).toString());
      }
}
