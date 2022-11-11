package com.fch.buffetorder.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-11-04 22:14
 **/
@Component
public class UploadImgUtil {

    public String uploadImg(String childPath, String imgName, MultipartFile file, HttpServletRequest request) throws IOException {
        //1.创建上传的文件存放的文件夹
        //得到存放文件的路径:静态资源路径+那你自己定义的"upload/2022-04-13/"
        //静态资源路径：如果resources下面有public或者static文件夹，那么我们就把它当成静态资源路径（idea需要设置），如过没有，才会用那个临时文件夹
        String path = request.getServletContext().getRealPath(childPath);
        //创建文件夹，但凡你要操作文件或者文件夹，你都要先创建它对应的File对象
        File desDir = new File(path);
        if (!desDir.exists()) {
            //mkdirs()和mkdir()区别
            desDir.mkdirs();
        }
        //2.上传文件
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String desFileName = imgName + suffix;
        //但凡你要操作文件或者文件夹，你都要先创建它对应的File对象
        File desFile = new File(path, desFileName);
        try {
            file.transferTo(desFile);
        } catch (IOException e) {
            throw e;
        }
        String reFilePath = childPath + desFileName;
        return reFilePath;
    }
}
