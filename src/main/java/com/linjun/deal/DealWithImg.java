package com.linjun.deal;

import com.alibaba.fastjson.JSON;
import com.linjun.core.CharsRecognise;
import com.linjun.core.PlateDetect;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Vector;

/**
 * @author 林俊
 * @create 2018/9/9.
 * @desc
 **/
public class DealWithImg implements DealWithInterface {
      static PlateDetect plateDetect=null;
      static CharsRecognise cr=null;
      static {
          plateDetect=new PlateDetect();
          plateDetect.setPDLifemode(true);
          cr=new CharsRecognise();

      }
//单张图片识别
    public static String plateRecognise(opencv_core.Mat mat){
        Vector<opencv_core.Mat> matVector = new Vector<opencv_core.Mat>(1);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            if(matVector.size()>0){
                return cr.charsRecognise(matVector.get(0));
            }
        }
        return null;
    }


    @Override
    public String result(String path) {
        Map<String,String> payReq= (Map<String, String>) JSON.parse(path);
        String result="";
          try {
            result=  recognition(payReq);
          }catch (Exception e){
              System.out.println("出现处理错误:" + e);
              return "";
          }


        return result;
    }

    private  String recognition(Map<String,String> datas){
//        获取识别图片的路径
         String path=datas.get("path");
//         识别车牌场景类型
         String type=datas.get("type");
           long longTime=0;
        opencv_core.Mat src=opencv_imgcodecs.imread(path);
        long now=System.currentTimeMillis();
        String ret=plateRecognise(src);
        System.out.println(ret);
        long s=System.currentTimeMillis()-now;
        if (s>longTime){
            longTime=s;
        }
        System.err.println("单次最长耗时："+longTime+"ms");
        return  ret;
    }

}
