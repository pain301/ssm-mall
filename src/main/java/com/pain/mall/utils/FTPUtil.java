package com.pain.mall.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getString("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getString("ftp.user");
    private static String ftpPassword = PropertiesUtil.getString("ftp.password");

    private String ip;
    private int port;
    private String user;
    private String password;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    // 让业务检测到异常
    public static boolean uploadFile(List<File> fileList) throws IOException {

        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPassword);
        logger.info("开始连接上传");
        boolean result = ftpUtil.uploadFile("img", fileList);
        logger.info("结束上传，上传结果：{}", result);
        return result;
    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {

        // FIXME false
        boolean uploaded = true;
        FileInputStream fis = null;

        if (connectServer(this.getIp(), this.getPort(), this.getUser(), this.getPassword())) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");

                // 防止乱码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

                // 进入被动模式
                ftpClient.enterLocalPassiveMode();

                for (File file : fileList) {
                    fis = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(), fis);
                }
            } catch (IOException e) {
                logger.error("上传文件失败", e);
                uploaded = false;
            } finally {
                if (null != fis) {
                    fis.close();
                }
                // TODO
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } else {
            logger.error("连接服务器失败");
        }

        return uploaded;
    }

    private boolean connectServer(String ip, int port, String user, String password) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();

        try {
            // ftpClient.connect(ip);
            ftpClient.connect(ip, port);
            isSuccess = ftpClient.login(user, password);
        } catch (IOException e) {
            logger.error("连接 FTP 服务器失败", e);
        }

        return isSuccess;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
