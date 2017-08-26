package com.pain;

import org.springframework.core.io.*;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/8/23.
 */
public class TestResource {

    public static void test_classpath_resource() throws IOException {
        Resource res = new ClassPathResource("com/pain/ioc.properties");
        EncodedResource encRes = new EncodedResource(res, "UTF-8");
        String content = FileCopyUtils.copyToString(encRes.getReader());
        System.out.println(content);
    }

    public static void test_filepath_resource() throws IOException {
        String filepath = "E://DS.txt";
        WritableResource res = new PathResource(filepath);
        OutputStream out = res.getOutputStream();
        out.write("file path resource".getBytes());
        out.close();

        InputStream in = res.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int tmp;
        while (-1 != (tmp = in.read())) {
            baos.write(tmp);
        }
        in.close();
        baos.close();
        System.out.println(baos.toString());
        System.out.println(res.getFilename());
    }

    public static void test_resource_loader() throws IOException {
        InputStream in = new DefaultResourceLoader()
                .getResource("classpath:/com/pain/ioc.properties")
                .getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int tmp;
        while (-1 != (tmp = in.read())) {
            baos.write(tmp);
        }
        System.out.println(baos.toString());
        in.close();
        baos.close();

        baos = new ByteArrayOutputStream();
        in = new DefaultResourceLoader()
                .getResource("file:E://DS.txt")
                .getInputStream();
        while (-1 != (tmp = in.read())) {
            baos.write(tmp);
        }
        System.out.println(baos.toString());
        in.close();
        baos.close();
    }

    public static void main(String[] args) throws IOException {
        test_resource_loader();
    }
}
