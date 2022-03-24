package com.hd._01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {
    public static void main(String[] args) {

        try(
                //input是对于channel而言的，channel读进来
                FileChannel from= new FileInputStream("src/data.txt").getChannel();
                //output也是对于channel而言的，文件写出去
                FileChannel to= new FileOutputStream("src/to.txt").getChannel();
                ){
            //效率高，底层会利用os的零拷贝进行优化，有上限2g，可改进为多次传输，可以用循环

            long size = from.size();
            for(long left =size; left>0;) {
                left-=from.transferTo((size-left), size, to);
            }
        }catch (Exception o){
           o.printStackTrace();
        }
    }
}
