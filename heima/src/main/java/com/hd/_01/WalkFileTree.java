package com.hd._01;

import com.sun.xml.internal.ws.api.message.SuppressAutomaticWSARequestHeadersFeature;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class WalkFileTree {
    public static void main(String[] args) throws IOException {
        FileWalkTree();
    }

    private static void FileWalkTree() throws IOException {
        AtomicInteger dirCount =new AtomicInteger(0);
        AtomicInteger fileCount =new AtomicInteger(0);
        Files.walkFileTree(Paths.get("E:\\迅雷下载"),new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        dirCount.incrementAndGet();
                        System.out.println("--->" + dir);
                        return super.preVisitDirectory(dir, attrs);
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        System.out.println(file);
                        fileCount.incrementAndGet();
                        return super.visitFile(file, attrs);
                    }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("<-----" + dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
        System.out.println(dirCount);
        System.out.println(fileCount);
    }

}
