package com.hd._01;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferExam {
    public static void main(String[] args) {
         ByteBuffer source = ByteBuffer.allocate(50);
         source.put("hello world\nI'm zhangsan\nhow ".getBytes(StandardCharsets.UTF_8));
         spite(source);
        source.put("are you\n".getBytes(StandardCharsets.UTF_8));
        spite(source);
    }

    private static void spite(ByteBuffer source) {
        ByteBuffer b=ByteBuffer.allocate(100);
             source.flip();
         for(int i=0;i<source.limit();i++) {
                 if(source.get(i)=='\n'){
                     int length=i+1-source.position();
                     for(int j=0;j<length;j++){
                         b.put(source.get());
                     }
                     b.flip();
                     System.out.println(StandardCharsets.UTF_8.decode(b));
                     System.out.println(b.position());
                     System.out.println(b.limit());
                     b.compact();
                     System.out.println(b.position());
                     System.out.println(b.limit());
                 }
         }
         source.compact();
    }
}
