package com.linjun.controller;

import com.linjun.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class HelloController {
    @RequestMapping("/identify")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }
    @RequestMapping("/")
    public String index(ModelMap map) {

        return "Test4";
    }


    public JsonResult upload(
            @RequestParam("goodsID")long goodsID,
            @RequestParam("iskey")int key,
            @RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            return new JsonResult("500","文件为空");
        }
        String fileName=file.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        String filepath="E://text//";
        File dest=new File(filepath+fileName);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try{
            file.transferTo(dest);


        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult("600","文件上传失败");
    }
}

