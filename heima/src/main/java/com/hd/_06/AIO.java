package com.hd._06;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
@Slf4j
public class AIO {
    public static void main(String[] args)  {
        try {
            AsynchronousFileChannel channel=AsynchronousFileChannel
                    .open(Paths.get("src/data.txt"), StandardOpenOption.READ);
            ByteBuffer buffer=ByteBuffer.allocate(20);
            log.debug("read begin");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                     log.debug("read begin");
                     attachment.flip();
                    System.out.println(StandardCharsets.UTF_8.decode(attachment));
                    log.debug("read end");
                }
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                }
            });
            log.debug("read end");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
