package com.example.demo.controller;

import com.example.demo.core.utils.openoffice.DocumentConverterService2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("officeToPdf")
@Api(value = "/officeToPdf", description = "office转换为PDF")
public class TestOpenOffice {

    @Autowired
    private DocumentConverterService2 documentConverterService2;

    /**
     * office转换为PDF
     */
    @RequestMapping(value = "/officeToPdf", method = RequestMethod.GET)
    @ApiOperation(value = "office转换为PDF")
    @ApiImplicitParam(name = "filePath", paramType = "query", value = "文件路径地址", required = true, dataType = "String", defaultValue = "/2020/3/23/word.docx")
    public Map<String, Object> officeToPdf(@RequestParam(name = "filePath", required = true) String filePath) throws Exception {
        return documentConverterService2.wordToPdf(filePath);
    }


}
