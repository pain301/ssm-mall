package com.pain.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface IFileService {

    public String upload(MultipartFile file, String path);
}
