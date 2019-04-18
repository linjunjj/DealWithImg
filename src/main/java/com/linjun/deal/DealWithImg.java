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
//测试部分
    /**
     * 多车牌识别
     * @param mat
     * @return
     */
    public static String[] mutiPlateRecognise(opencv_core.Mat mat){
        PlateDetect plateDetect = new PlateDetect();
        plateDetect.setPDLifemode(true);
        Vector<opencv_core.Mat> matVector = new Vector<opencv_core.Mat>(10);
        if (0 == plateDetect.plateDetect(mat, matVector)) {
            CharsRecognise cr = new CharsRecognise();
            String[] results=new String[matVector.size()];
            for (int i = 0; i < matVector.size(); ++i) {
                String result = cr.charsRecognise(matVector.get(i));
                results[i]=result;
            }
            return results;
        }
        return null;
    }

    public static void main(String[] args){
        int sum=100;
        int errNum=0;
        int sumTime=0;
        long longTime=0;
        for(int i=sum;i>0;i--){
            String imgPath = "res/image/test_image/plate_judge.jpg";
            opencv_core.Mat src = opencv_imgcodecs.imread(imgPath);
            long now =System.currentTimeMillis();
            String ret=plateRecognise(src);
            System.err.println(ret);
            long s=System.currentTimeMillis()-now;
            if(s>longTime){
                longTime=s;
            }
            sumTime+=s;
            if(!"川A0CP56".equals(ret)){
                errNum++;
            }
        }
        System.err.println("总数量："+sum);
        System.err.println("单次最长耗时："+longTime+"ms");

        BigDecimal errSum=new BigDecimal(errNum);
        BigDecimal sumNum=new BigDecimal(sum);
        BigDecimal c=sumNum.subtract(errSum).divide(sumNum).multiply(new BigDecimal(100));
        System.err.println("总耗时："+sumTime+"ms,平均处理时长："+sumTime/sum+"ms,错误数量："+errNum+"，正确识别率："+c+"%");
    }


    public   String recognition(Map<String,String> datas){
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
    private  String currentURL(){
        System.getProperty("user.dir");

        return  System.getProperty("user.dir");
    }


}
