package com.example.demo.core.utils.easypoi;

import com.example.demo.po.BottomElevationExcel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelImportAndExportService {
    /**
     * excel导出
     */
    public void exportExcelList(HttpServletResponse response) {
        List<BottomElevationExcel> List = new ArrayList<>();
        BottomElevationExcel bottomElevationExcel = new BottomElevationExcel("2019-02-03","1321","1223");
        List.add(bottomElevationExcel);
        EasypoiFileUtil.exportExcel(List, BottomElevationExcel.class, "水库最低水底高程.xls", response);
    }

    /**
     * excel导入
     */
    public List<Map<String, Object>> importExcelList(MultipartFile file) {
        Map map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Map verifyMap = new HashMap<>();
        List<BottomElevationExcel> bottomElevationExcel = EasypoiFileUtil.importExcel(file, 0, 1,
                BottomElevationExcel.class);
        verifyMap = verifyOrNot(bottomElevationExcel);

        map.put("error", verifyMap.get("errorList"));
        map.put("success", verifyMap.get("successList"));

        list.add(map);
        return list;
    }

    // 字段验证
    public Map<String, Object> verifyOrNot(List<BottomElevationExcel> bottomElevationExcel) {
        Map map = new HashMap<>();
        List<BottomElevationExcel> errorList = new ArrayList<>();
        List<BottomElevationExcel> successList = new ArrayList<>();
        DataValidationRulesUtil dataValidationRulesUtil = new DataValidationRulesUtil();
        for (int i = 0; i < bottomElevationExcel.size(); i++) {
            BottomElevationExcel h = bottomElevationExcel.get(i);
            try {
                if (dataValidationRulesUtil.DataValidation(h)) {
                    successList.add(h);
                } else {
                    errorList.add(h);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        map.put("errorList", errorList);
        map.put("successList", successList);
        return map;
    }


}
