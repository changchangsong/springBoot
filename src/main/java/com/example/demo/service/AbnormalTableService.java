package com.example.demo.service;

import com.example.demo.dao.AbnormalTableDao;
import com.example.demo.entity.AbnormalTableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbnormalTableService {

	@Autowired(required = false)
	private AbnormalTableDao abnormalTableDao;

	public List<AbnormalTableEntity> queryList(){
		return abnormalTableDao.findAll();
	}

	public AbnormalTableEntity findById(String id) {
		return abnormalTableDao.findById(id);
	}

	public AbnormalTableEntity queryObject(String id) {
		return abnormalTableDao.queryObject(id);
	}

	public int update(AbnormalTableEntity abnormalTableEntity){
		return abnormalTableDao.update(abnormalTableEntity);
	};

}
