package com.linjun;

import com.linjun.utils.Util;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

/**
 * @author 林俊
 * @create 2018/9/9.
 * @desc
 **/
public class DealHandler implements MessageHandler {
    private Connection connection;
    private ExecutorService executorService;

    public DealHandler(Connection connection, ExecutorService executorService) {
        this.connection = connection;
        this.executorService = executorService;
    }

    @Override
    public void onMessage(Message message) {
        System.out.println(message.getSubject());
        this.executorService.submit(()->{
            if (message.getSubject() == null)
                return;
            String channelNo = message.getSubject().substring(19);
            String className = Util.SnakeToCamel(channelNo);
            try {
                Class signer = Class.forName("com.linjun.deal.DealWithImg");
                Method sumMethod = signer.getDeclaredMethod("result", String.class);
                String result = (String) sumMethod.invoke(signer.newInstance(), new String(message.getData()));
                if (result == null)
                    return;
                connection.publish(message.getReplyTo(), result.getBytes());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("找不到可用的签名对象");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("回复 nats 请求失败");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.out.println("找不到 sum 方法,Sign" + className + " 应该实现 Signature 接口");
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                System.out.println("无法实例化签名者对象");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                System.out.println("调用 sum 方法出错");
            }

        });
    }
}
