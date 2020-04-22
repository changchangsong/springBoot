package com.example.demo.service;

import com.example.demo.core.service.BaseService;
import com.example.demo.dao.AbnormalTableDao;
import com.example.demo.entity.AbnormalTableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2020/4/8.
 */
@Service
public class TestWangYingSpringMVCService  extends BaseService {

    @Autowired(required = false)
    private AbnormalTableDao abnormalTableDao;

    public  void queryObject(String id){
        AbnormalTableEntity abnormalTableEntity = abnormalTableDao.queryObject(id);
        this.setAttr("status", true);
        this.setAttr("data", abnormalTableEntity);
    }
}
