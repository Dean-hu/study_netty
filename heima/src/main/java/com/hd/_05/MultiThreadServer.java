package com.hd._05;

import lombok.extern.slf4j.Slf4j;
import sun.java2d.opengl.WGLSurfaceData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc =ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss=Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT,null);
        ssc.bind(new InetSocketAddress(8888));
        Worker[] worker= new Worker[Runtime.getRuntime().availableProcessors()];
        for(int i=0;i<worker.length;i++){
            worker[i] =new Worker("worker_"+i);
        }
        AtomicInteger index =new AtomicInteger(0);
        while(true){
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key=iter.next();
                iter.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel channel =(ServerSocketChannel) key.channel();
                    log.debug("connecting ...");
                    SocketChannel sc = channel.accept();
                    log.debug("connecting down");
                    sc.configureBlocking(false);
                    //2.关联
                    log.debug("关联");
                    //轮询分配
                    worker[index.getAndIncrement()%worker.length].register(sc);
                }
            }
        }
    }
    static  class  Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String  name ;
        private  boolean start=false;
        private  ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();
        public Worker(String name) {
            this.name = name;
        }
       //初始化线程和Selector
        public void register(SocketChannel sc) throws IOException {
           if(!start) {
               log.debug("start");
               thread = new Thread(this, name);
               selector =Selector.open();
               thread.start();
               start=true;
           }
           //向队列添加了任务，但未执行
           queue.add(()->{
               try {
                   sc.register(selector,SelectionKey.OP_READ,null);
               } catch (ClosedChannelException e) {
                   e.printStackTrace();
               }
           });
           //换新selector,让它不在阻塞
         selector.wakeup();
        }

        @Override
        public void run() {
            try {
                while(true) {
                    selector.select();
                    //取出任务执行
                    log.debug("注册成功");
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run();
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel sc = (SocketChannel) key.channel();
                            log.debug("before read");
                            if(sc.read(buffer)>0) {
                                buffer.flip();
                                if (buffer.limit() != 0) {
                                    System.out.println(StandardCharsets.UTF_8.decode(buffer));
                                }
                                log.debug("after read");
                            }else{
                                sc.close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
