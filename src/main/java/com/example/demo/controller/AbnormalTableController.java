package com.example.demo.controller;

import com.example.demo.entity.AbnormalTableEntity;
import com.example.demo.service.AbnormalTableService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * ${comments}
 *
 * @author Song
 * @email 852146603@qq.com
 * @date 2019-03-20 10:37:40
 */
@RestController
@RequestMapping("abnormalTable")
@Api(value = "/abnormalTable", description = "异常表")
public class AbnormalTableController {
    @Autowired
    private AbnormalTableService abnormalTableService;

    /**
     * 分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "返回分页数据，参数:page,rows以及其他需要的查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", value = "分页参数，页码，从1开始，默认第1页", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "rows", paramType = "query", value = "分页参数，每页记录数，默认10条", required = false, dataType = "String", defaultValue = "10"),
    })
    public Object page(@RequestParam(name = "page", required = false) Integer page,
                       @RequestParam(name = "rows", required = false) Integer rows) {
        PageHelper.startPage(page, rows);
        PageHelper.orderBy("ID");
        List<AbnormalTableEntity> list = abnormalTableService.queryList();
        PageInfo<AbnormalTableEntity> pageInfo = new PageInfo<>(list);
        Map<String, Object> data = new HashMap<>();
        data.put("total_size", pageInfo.getTotal());//总条数
        data.put("total_page", pageInfo.getPages());//总页数
        data.put("current_page", page);//当前页
        data.put("data", pageInfo.getList());//数据
        return data;
    }

    /**
     * list列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "返回数据列表")
    @ApiImplicitParam(name = "id", paramType = "query", value = "编号", required = true, dataType = "String", defaultValue = "0001qqfrwYoDqCy4yQaxia")
    public Object list(@RequestParam(name = "id", required = true) String id) {
        List<AbnormalTableEntity> abnormalTableList = abnormalTableService.queryList();
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("code", "1");
        map.put("msg", "查询成功");
        map.put("rows", abnormalTableList);
        return map;
    }

    /**
     * 信息
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    @ApiOperation(value = "根据id返回一条数据，参数:id")
    @ApiImplicitParam(name = "id", paramType = "query", value = "编号", required = true, dataType = "String", defaultValue = "0001qqfrwYoDqCy4yQaxia")
    public Object info(@RequestParam(name = "id", required = true) String id) {
//		AbnormalTableEntity abnormalTable = abnormalTableService.queryObject(id);
        AbnormalTableEntity abnormalTable = abnormalTableService.findById(id);
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("code", "1");
        map.put("msg", "查询成功");
        map.put("rows", abnormalTable);
        return map;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParam(name = "abnormalTableEntity", value = "AbnormalTableEntity实体类", required = true, dataType = "AbnormalTableEntity", defaultValue = "")
    public int save(@RequestBody AbnormalTableEntity abnormalTableEntity) {
        return abnormalTableService.update(abnormalTableEntity);
    }
}
