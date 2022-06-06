package com.ldy.nio.basic;

import java.nio.IntBuffer;

/**
 * @author : ldy
 * @version : 1.0
 */
public class BasicBuffer {
  public static void main(String[] args) {
    //allocate是分配的意思， 创建一个可以存放5个int的buffer
    IntBuffer intBuffer = IntBuffer.allocate(5);
    //向intBuffer之中存放int；
    for (int i = 0; i < 5; i++) {
      intBuffer.put(i*2);
    }
    //转换形态，由写入改为读取；
    intBuffer.flip();
    //读取并输出intBuffer中的内容，Remaining是 剩下的 的意思；
    while (intBuffer.hasRemaining()){
      System.out.println(intBuffer.get());//会自动向后获取数据
    }
  }
}
