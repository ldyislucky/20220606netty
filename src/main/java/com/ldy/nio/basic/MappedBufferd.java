package com.ldy.nio.basic;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : ldy
 * @version : 1.0
 * 用于修改文档中的内容
 */
public class MappedBufferd {
    public static void main(String[] args) throws Exception {
        //创建修改文档的入口,参考fileinputstream就行；
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\D\\documen" +
                "t\\javaprograms1\\caogao\\c3增删源文档\\1.txt", "rw");
        //从创建文档入口创建通道
        FileChannel fileChannel = randomAccessFile.getChannel();
        /**
         * 在fileChannel上标记映射map(即修改规则）,并生成一个方便操作的文件的缓存区
         * mapped是映射的意思、mapmode映射方式
         * 0是起始修改位置，10是最大修改位置
         * mappedByteBuffer目前的试验结果来看 是操作不了中文的
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.
                READ_WRITE, 0, 10);
        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(8,(byte) 'H');
        //mappedByteBuffer.put(10,(byte) '狗');
        randomAccessFile.close();
    }
}
