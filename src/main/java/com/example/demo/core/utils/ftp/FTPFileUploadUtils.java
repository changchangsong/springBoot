package com.example.demo.core.utils.ftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author Wang JinLei
 * @Description：ftp文件上传工具类
 * @date 2019年5月14日 上午9:51:26
 */
@Component
public class FTPFileUploadUtils {

    //ftp服务器ip地址
    @Value("${ftp.host}")
    private String FTP_ADDRESS;
    //端口号
    @Value("${ftp.port}")
    private int FTP_PORT;
    //用户名
    @Value("${ftp.username}")
    private String FTP_USERNAME;
    //密码
    @Value("${ftp.password}")
    private String FTP_PASSWORD;


    /**
     * 文件预览
     * @Title: getViewInputStream @Description: 获取ftp文件输入流 @param type @param
     * id @throws Exception @return void @author wanyin @throws
     */
    public InputStream getViewInputStream(String filePath) throws Exception {
        InputStream inputStream = down(filePath);
        String str= Base64.encodeBase64String(IOUtils.toByteArray(inputStream));
        InputStream fileInputStream = new ByteArrayInputStream(Base64.decodeBase64(str));
        return fileInputStream;
    }

    /**
     * 下载文件
     *
     * @param remoteFileName ftp文件路径（包含文件名）
     * @return 是否下载成功
     */
    public InputStream down(String remoteFileName) throws Exception {
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
        ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
        ftp.enterLocalPassiveMode();//启用被动模式
        try {
            System.out.println("文件【" + remoteFileName + "】准备下载.....");
            ftp.enterLocalPassiveMode();//启用被动模式
            InputStream in = ftp.retrieveFileStream(remoteFileName);
            byte[] target = IOUtils.toByteArray(in);
            InputStream inputStream = new ByteArrayInputStream(target);
            System.out.println("文件【" + remoteFileName + "】下载" + (inputStream != null ? "成功" : "失败"));
            in.close();
            ftp.completePendingCommand();
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * 下载文件
     *
     * @param filePath ftp文件路径（包含文件名）
     * @return 是否下载成功
     */
    public void downFile(OutputStream os, String filePath) throws Exception {
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
//            ftp.enterLocalActiveMode();//启用主动模式
            ftp.enterLocalPassiveMode();//启用被动模式
            boolean flag = ftp.retrieveFile(filePath, os);
            System.out.println("文件【" + filePath + "】下载" + (flag ? "成功" : "失败"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * 文件上传
     *
     * @param originFileName 文件名（包含原始文件及其后缀名（a.txt））
     * @param input          文件流
     * @param filePath       文件在ftp的存储路径（"/file/"）
     * @return
     */
    public boolean uploadFile(String originFileName, InputStream input, String filePath) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

            makeDirectorys(filePath);
            ftp.changeWorkingDirectory(filePath);
            ftp.storeFile(originFileName, input);
            input.close();
            ftp.logout();
            success = true;
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 创建目录，支持递归创建
     *
     * @param remotedir
     * @return Boolean
     * @throws Exception
     */
    public boolean makeDirectorys(String remotedir) throws Exception {
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            if (StringUtils.isNotBlank(remotedir)) {
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory("/");// 重点，不然有可能建在ftpClient的当前路下了
                String[] pathes = remotedir.split("/");
                for (String onepath : pathes) {
                    if (StringUtils.isBlank(onepath)) {
                        continue;
                    }
                    if (!ftp.changeWorkingDirectory(onepath)) {
                        ftp.makeDirectory(onepath);
                        ftp.changeWorkingDirectory(onepath);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }

    }

}
