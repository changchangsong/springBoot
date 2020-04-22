package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * ${comments}
 * 
 * @author Song
 * @email 852146603@qq.com
 * @date 2019-03-20 10:37:40
 */
public class AbnormalTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 主键
	@ApiModelProperty(value = "主键", dataType = "String", required = false, hidden = false)
	private String id;
	// 异常部位ID
	@ApiModelProperty(value = "异常部位ID", dataType = "String", required = false, hidden = false)
	private String positionId;
	// 水下检查ID
	@ApiModelProperty(value = "水下检查ID", dataType = "String", required = false, hidden = false)
	private String underWaterInspectId;
	// 异常桩号
	@ApiModelProperty(value = "异常桩号", dataType = "String", required = false, hidden = false)
	private String abnormalPileNumber;
	// 异常体积
	@ApiModelProperty(value = "异常体积", dataType = "String", required = false, hidden = false)
	private String abnormalVolume;
	// 异常面积
	@ApiModelProperty(value = "异常面积", dataType = "String", required = false, hidden = false)
	private String abnormalAcreage;
	// 异常描述
	@ApiModelProperty(value = "异常描述", dataType = "String", required = false, hidden = false)
	private String abnormalDescribe;
	// 添加日期
	@ApiModelProperty(value = "添加日期", dataType = "Date", required = false, hidden = false)
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date addTime;
	// 添加人ID
	@ApiModelProperty(value = "添加人ID", dataType = "String", required = false, hidden = false)
	private String addUserid;
	// 最后操作日期
	@ApiModelProperty(value = "最后操作日期", dataType = "Date", required = false, hidden = false)
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date lastOperateTime;
	// 最后操作人ID
	@ApiModelProperty(value = "最后操作人ID", dataType = "String", required = false, hidden = false)
	private String lastOperateUserid;
	// 是否删除(1:删:0未删)
	@ApiModelProperty(value = "是否删除(1:删:0未删)", dataType = "String", required = false, hidden = false)
	private String isDelete;

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置：异常部位ID
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * 获取：异常部位ID
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * 设置：水下检查ID
	 */
	public void setUnderWaterInspectId(String underWaterInspectId) {
		this.underWaterInspectId = underWaterInspectId;
	}

	/**
	 * 获取：水下检查ID
	 */
	public String getUnderWaterInspectId() {
		return underWaterInspectId;
	}

	/**
	 * 设置：异常桩号
	 */
	public void setAbnormalPileNumber(String abnormalPileNumber) {
		this.abnormalPileNumber = abnormalPileNumber;
	}

	/**
	 * 获取：异常桩号
	 */
	public String getAbnormalPileNumber() {
		return abnormalPileNumber;
	}

	/**
	 * 设置：异常体积
	 */
	public void setAbnormalVolume(String abnormalVolume) {
		this.abnormalVolume = abnormalVolume;
	}

	/**
	 * 获取：异常体积
	 */
	public String getAbnormalVolume() {
		return abnormalVolume;
	}

	/**
	 * 设置：异常面积
	 */
	public void setAbnormalAcreage(String abnormalAcreage) {
		this.abnormalAcreage = abnormalAcreage;
	}

	/**
	 * 获取：异常面积
	 */
	public String getAbnormalAcreage() {
		return abnormalAcreage;
	}

	/**
	 * 设置：异常描述
	 */
	public void setAbnormalDescribe(String abnormalDescribe) {
		this.abnormalDescribe = abnormalDescribe;
	}

	/**
	 * 获取：异常描述
	 */
	public String getAbnormalDescribe() {
		return abnormalDescribe;
	}

	/**
	 * 设置：添加日期
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * 获取：添加日期
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * 设置：添加人ID
	 */
	public void setAddUserid(String addUserid) {
		this.addUserid = addUserid;
	}

	/**
	 * 获取：添加人ID
	 */
	public String getAddUserid() {
		return addUserid;
	}

	/**
	 * 设置：最后操作日期
	 */
	public void setLastOperateTime(Date lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}

	/**
	 * 获取：最后操作日期
	 */
	public Date getLastOperateTime() {
		return lastOperateTime;
	}

	/**
	 * 设置：最后操作人ID
	 */
	public void setLastOperateUserid(String lastOperateUserid) {
		this.lastOperateUserid = lastOperateUserid;
	}

	/**
	 * 获取：最后操作人ID
	 */
	public String getLastOperateUserid() {
		return lastOperateUserid;
	}

	/**
	 * 设置：是否删除(1:删:0未删)
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * 获取：是否删除(1:删:0未删)
	 */
	public String getIsDelete() {
		return isDelete;
	}
}
