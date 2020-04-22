package com.example.demo.core.utils.openoffice;

import com.example.demo.core.utils.ftp.ConvertUtil;
import com.example.demo.core.utils.ftp.FTPFileUploadUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.document.DocumentFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DocumentConverterService2 {
    @Autowired(required = false)
    private DocumentConverter documentConverter;

    @Autowired
    private FTPFileUploadUtils ftpFileUploadUtils;


    /**
     * office转pdf
     * @param remoteFileName ftp文件路径（包含文件名）
     * @return
     * @throws Exception
     */
    @Transactional
    public Map<String,Object> wordToPdf(String remoteFileName) throws Exception {

        Map<String,Object> result=new HashMap<>();

        if(remoteFileName!=null) {
            DocumentFormat inFormat = DefaultDocumentFormatRegistry.DOC;
            if (remoteFileName.endsWith(".doc")) {
                inFormat = DefaultDocumentFormatRegistry.DOC;
            } else if (remoteFileName.endsWith(".docx")) {
                inFormat = DefaultDocumentFormatRegistry.DOCX;
            } else if (remoteFileName.endsWith(".ppt")) {
                inFormat = DefaultDocumentFormatRegistry.PPT;
            } else if (remoteFileName.endsWith(".pptx")) {
                inFormat = DefaultDocumentFormatRegistry.PPTX;
            } else if (remoteFileName.endsWith(".xls")) {
                inFormat = DefaultDocumentFormatRegistry.XLS;
            } else if (remoteFileName.endsWith(".xlsx")) {
                inFormat = DefaultDocumentFormatRegistry.XLSX;
            } else if (remoteFileName.endsWith(".txt")) {
                inFormat = DefaultDocumentFormatRegistry.TXT;
            }

            //ftp下载文件
            InputStream inputStream = ftpFileUploadUtils.down(remoteFileName);

            OutputStream outputStream = new ByteArrayOutputStream();
            //将office转换为pdf
            documentConverter.convert(inputStream, true).as(inFormat).to(outputStream).as(DefaultDocumentFormatRegistry.PDF)
                    .execute();

            InputStream parse = ConvertUtil.parse(outputStream);
            outputStream.flush();
            outputStream.close();

            // 以 年/月/日/ 作为ftp文件夹名称
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DATE);
            String filePath = "/" + year + "/" + month + "/" + day + "/";
            String suffix = ".pdf";
            String originFileName = filePath+UUID.randomUUID().toString() + suffix;

            // 上传
            ftpFileUploadUtils.uploadFile(originFileName, parse,filePath);

            IOUtils.closeQuietly(parse);

            result.put("storePath", originFileName);
            result.put("suffix", suffix);
        }else {
            result.put("storePath", remoteFileName);
            String suffix = remoteFileName.substring(remoteFileName.lastIndexOf("."));
            result.put("suffix", suffix);
        }
        return result;
    }
}
