package com.hd._03;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.java2d.pipe.SolidTextRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class server {
    public final static Logger log = LoggerFactory.getLogger(server.class);
    private static void spite(ByteBuffer source) {
        ByteBuffer b=ByteBuffer.allocate(100);
        source.flip();
        for(int i=0;i<source.limit();i++) {
            if(source.get(i)=='\n'){
                int length=i+1-source.position();
                for(int j=0;j<length;j++){
                    b.put(source.get());
                }
                if(b.position()>0) {
                    b.flip();
                    System.out.println(StandardCharsets.UTF_8.decode(b));
                    b.compact();
                }
            }
        }
        source.compact();
    }
    public static void main(String[] args) throws IOException {
        //创建selector
        Selector selector = Selector.open();

        ServerSocketChannel socket = ServerSocketChannel.open();
        socket.configureBlocking(false);
        //注册selector，建立 ServerSocketChannel和Selector的联系
        //SelectionKey是事件发生后通过SelectionKey得到事件的信息
        SelectionKey ssck = socket.register(selector, 0, null);
        //指明了ssck只关注accept事件
        ssck.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("SelectionKey:{}", ssck);
        socket.bind(new InetSocketAddress(8888));
        socket.configureBlocking(false);
        while (true) {
            //有事件才运行，无事件则阻塞,在事件未处理是，不会阻塞
            selector.select();
            //处理时间，selectedKeys集合内部包含了所有发生事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    log.debug("key:{}", key);
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    //关闭阻塞，并且添加行的key，关注与读事件
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(5);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("sc..{}", sc);
                } else if (key.isReadable()) {
                  try {
                      SocketChannel channel = (SocketChannel) key.channel();
                      //取出附件
                      ByteBuffer buffer=(ByteBuffer)key.attachment();
                      int read = channel.read(buffer);
                      if(read<0){
                          //此时服务器已经正常关闭
                          key.cancel();
                          log.debug("客户端正常关闭");
                      }
                      else {
                         spite(buffer);
                         if(buffer.position()==buffer.capacity())
                         {
                             ByteBuffer buffer1=ByteBuffer.allocate(buffer.capacity()*2);
                             buffer.flip();
                             buffer1.put(buffer);
                             //放回附件
                             key.attach(buffer1);
                         }
                      }
                  }catch (IOException e){
                        e.printStackTrace();
                        key.cancel();
                      log.debug("客户端异常关闭");
                  }
                }
            }
        }
    }
}
