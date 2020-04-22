package com.example.demo.controller;

import com.example.demo.core.utils.ftp.FTPFileUploadUtils;
import io.swagger.annotations.*;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;


@RestController
@RequestMapping("file")
@Api(value = "/file", description = "测试文件上传下载")
public class fileUploadOfDonload {

    @Autowired(required = false)
    private FTPFileUploadUtils fTPFileUploadUtils;


    /**
     * 文件预览
     */
    @RequestMapping(value = "/pdfFileView", method = RequestMethod.GET)
    @ApiOperation(value = "pdf文件预览")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", paramType = "query", value = "根据文件路径地址进行文件预览", required = true, dataType = "String", defaultValue = "/2020/3/23/8fc83812-103c-4309-a2b2-1a07778ce19d.pdf"),
            @ApiImplicitParam(name = "type", paramType = "query", value = "文件类型", required = true, dataType = "String", defaultValue = "pdf"),
    })
    public void pdfFileView(@RequestParam(name = "filePath", required = true) String filePath,
                            @RequestParam(name = "type", required = true) String type,
                            HttpServletResponse response) throws Exception {
        String contentType = "";
        switch (type) {
            case "image":
                contentType = "image/*";
                break;
            case "word":
                contentType = "application/msword";
                break;
            case "pdf":
                contentType = "application/pdf";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            default:
                contentType = "text/html";
                break;
        }
        response.setContentType(contentType);
        InputStream fileInputStream = fTPFileUploadUtils.getViewInputStream(filePath);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            IOUtils.copy(fileInputStream, os);
        } finally {
            IOUtils.closeQuietly(os);
        }

    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/fileDown", method = RequestMethod.GET)
    @ApiOperation(value = "文件下载")
    @ApiImplicitParam(name = "filePath", paramType = "query", value = "根据文件路径地址", required = true, dataType = "String", defaultValue = "/2020_3_21/d44e533e-6cf8-4b23-a30b-f5d88ffa65af.docx")
    public void fileDown(@RequestParam(name = "filePath", required = true) String filePath, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data;charset=UTF-8");
        //设置以流的方式传送
        response.setContentType("application/octet-stream");
        //设置响应头
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("d44e533e-6cf8-4b23-a30b-f5d88ffa65af.docx".getBytes("UTF-8"), "UTF-8"));
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            fTPFileUploadUtils.downFile(os, filePath);
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }

    }

    /**
     * 文件上传
     */
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "文件上传")
    public String fileUpload(@ApiParam(value = "file", required = true) MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                //获取图片或文件的后缀名
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //拼接图片或文件名称，32位随机uuid+后缀名
                String fileName = UUID.randomUUID().toString() + suffix;
                InputStream inputStream = file.getInputStream();
                // 以 年/月/日/ 作为ftp文件夹名称
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DATE);
                String filePath = "/" + year + "/" + month + "/" + day + "/";
                //调用ftp上传文件工具类，返回true成功，返回false失败
                Boolean flag = fTPFileUploadUtils.uploadFile(fileName, inputStream, filePath);
                if (flag == true) {
                    //这里按功能需求做相应处理即可
                    return filePath+fileName;
                }
            }
            return "上传失败";
        } catch (Exception e) {
            return "上传异常";
        }

    }

}
