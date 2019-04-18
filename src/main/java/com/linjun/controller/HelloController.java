package com.linjun.controller;

import com.linjun.deal.DealWithImg;
import com.linjun.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/filerquire")
@RestController
public class HelloController {

    @PostMapping(value = "")
    public JsonResult upload(
            @RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            return new JsonResult("500","文件为空");
        }
        String fileName=file.getOriginalFilename();
        System.out.println(fileName);
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        String filepath="/Users/linjun/IdeaProjects/DealWithImg/res/temp/";
        File dest=new File(filepath+fileName);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        String carNum =null;
        try{
            file.transferTo(dest);
            Map<String, String> map = new HashMap<String, String>();
            map.put("path", filepath+fileName);
            DealWithImg img = new DealWithImg();
             carNum = img.recognition(map);
            System.out.println(carNum);
            dest.delete();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult("200","",carNum);
    }
}

