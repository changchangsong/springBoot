package com.example.demo.controller;

import com.example.demo.core.utils.easypoi.ExcelImportAndExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("excelImportAndExport")
@Api(value = "/excelImportAndExport", description = "测试excel批量导入导出")
public class TestExcelImportAndExport {

    @Autowired
    private ExcelImportAndExportService excelImportAndExportService;

    /**
     * excel导入
     * @throws Exception
     */
    @PostMapping("/importExcel")
    @ApiOperation(value = "excel导入")
    public Map<String, Object> importExcel(@ApiParam(value = "file", required = true) MultipartFile file) throws Exception {
        List<Map<String, Object>> result =  excelImportAndExportService.importExcelList(file);
        Map map = new HashMap();
        map.put("code","1");
        map.put("msg","导入成功");
        map.put("rows",result);
        return map;
    }

    /**
     * excel导出
     * @throws Exception
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    @ApiOperation(value = "excel导出")
    public String exportExcel( HttpServletResponse response) throws Exception {
        excelImportAndExportService.exportExcelList(response);
        return "成功";
    }

}
