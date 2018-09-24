package com.linjun;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.net.HttpResponse;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.omg.CORBA.NameValuePair;

import java.io.IOException;
import java.util.*;
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
     register();
//     ExecutorService executorService= Executors.newCachedThreadPool();
//     try {
//         Connection connection= Nats.connect("nats://nats.databases.svc.cluster.local:4222");
//         System.out.println("nats://localhost:4222");
//         connection.subscribe("channels.dealwith.*",new DealHandler(connection,executorService));
//     }catch (IOException e){
//         System.out.println("连接nats服务器失败");
//         e.printStackTrace();
//     }

 }

    public static void register() {
        try {
            // 接入商编号
            String accessId = "000000009";
            // 秘钥
            String key = "36eb71b756c544459dbf712377a67d13";
            Map<String, Object> params = new HashMap<String, Object>();
            Calendar cal = Calendar.getInstance();
            String requestid ="432342312313242342342343";
//            params.put("requestId", requestid);
//            params.put("customerType", "PERSON");
//            params.put("bindMobile", "18759147252");
//            params.put("signedName", "苏永权");
//            params.put("linkman", "苏永权");
////            params.put("minsettleamount", "1");
//            params.put("bankAccountNumber", "6225886002193333");
//            params.put("bankName", "招商银行股份有限公司福州华林支行");
//            params.put("idcard", "360731199601297635");
//            params.put("bankHeadOfficeName", "工商银行");
//            params.put("accountName", "林");
//            params.put("bankAccountType", "PrivateCash");
//            params.put("bankProvince", "350000");
//            params.put("bankCity", "350100");
//            params.put("bankArea", "350102");
////            params.put("deposit", "100");
//            params.put("callbackUrl", "http://127.127.30.131:7080/ammngweb/");
            params.put("merchantId","000000330");
            params.put("tradeType","UNIONPAY");
            params.put("T0Rate","0.003");
            params.put("T1Rate","0");
            params.put("Fee","200");
            String sign = Signature.getSign(params, key);
            System.out.println(sign);
            params.put("sign", sign);

            String jsonStr = JSON.toJSONString(params);

            String ccc=AESUtil.encrypt(jsonStr,key);
System.out.println(ccc);
            String encryptData = AESUtil.decrypt("C456B03C4BA9FC1B530094970A3318534C93A33F2FDA1EC2F8F2E485F9880F524CA893B15415178953AB061BDFF75F8CEE64380F52DE0BA442F5290EBE226DCB94896707C8DD61A02E8EC90CCF7DCF0884879E5D65561D7AD3D5460C43496A59", key);
            System.out.println(encryptData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
