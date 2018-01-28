package com.pain.mall.service.impl;

import com.google.common.collect.Lists;
import com.pain.mall.service.IFileService;
import com.pain.mall.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service
public class FileService implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileService.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + extName;

        logger.info("上传文件名: {}, 上传路径: {}, 新文件名: {}", fileName, path, uploadFileName);

        // TODO 多线程问题
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            logger.error("文件上传失败", e);
        }

        return targetFile.getName();
    }

    public static void main(String[] args) throws IOException {
        File targetFile = new File("log.md");
        FTPUtil.uploadFile(Lists.newArrayList(targetFile));
//        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("hello.txt"));
//        osw.write("hello");
//        osw.close();
//        System.out.println(targetFile.getAbsolutePath());
    }
}
