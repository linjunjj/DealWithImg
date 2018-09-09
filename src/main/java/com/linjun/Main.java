package com.linjun;

import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 林俊
 * @create 2018/9/9.
 * @desc
 **/
public class Main {
 public static  void main(String[] args){
     System.out.println("启动图片处理");
     ExecutorService executorService= Executors.newCachedThreadPool();
     try {
         Connection connection= Nats.connect("nats://localhost:4222");
         System.out.println("nats://localhost:4222");
         connection.subscribe("channels.dealwith.*",new DealHandler(connection,executorService));
     }catch (IOException e){
         System.out.println("连接nats服务器失败");
         e.printStackTrace();
     }

 }


}
