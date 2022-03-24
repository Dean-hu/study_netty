package com.hd._01;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileCopy {
    public static void main(String[] args) throws IOException {
        String source="E:\\迅雷下载 - 副本";
       Files.walkFileTree(Paths.get(source),new SimpleFileVisitor<Path>(){
           @Override
           public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
               return super.visitFile(file, attrs);
           }

           @Override
           public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
               Files.delete(dir);
               return super.postVisitDirectory(dir, exc);
           }
       });

    }
}
