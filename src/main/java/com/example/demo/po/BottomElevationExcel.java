package com.example.demo.po;

import java.util.UUID;

import com.example.demo.core.utils.easypoi.Regular;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.example.demo.core.utils.easypoi.Verification;

/**
 * ${comments}
 *
 * @author Song
 * @email 852146603@qq.com
 * @date 2019-03-27 17:57:47
 */
public class BottomElevationExcel {
    // 主键
    private String id = UUID.randomUUID().toString();
    // 探测日期
    @Verification(regular = Regular.IS_DATE)
    @Excel(name = "探测日期", orderNum = "0")
    private String dateOfDetection;
    // 高程（m）
    @Verification(regular = Regular.IS_DECIMAL)
    @Excel(name = "高程（m）", orderNum = "1")
    private String altitude;
    // 距坝里程（km）
    @Verification(regular = Regular.IS_DECIMAL)
    @Excel(name = "距坝里程（km）", orderNum = "2")
    private String distanceFromDam;

    public BottomElevationExcel() {
        super();
    }

    public BottomElevationExcel(String dateOfDetection, String altitude, String distanceFromDam) {
        this.dateOfDetection = dateOfDetection;
        this.altitude = altitude;
        this.distanceFromDam = distanceFromDam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateOfDetection() {
        return dateOfDetection;
    }

    public void setDateOfDetection(String dateOfDetection) {
        this.dateOfDetection = dateOfDetection;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getDistanceFromDam() {
        return distanceFromDam;
    }

    public void setDistanceFromDam(String distanceFromDam) {
        this.distanceFromDam = distanceFromDam;
    }

    @Override
    public String toString() {
        return "BottomElevationExcel [id=" + id + ", dateOfDetection=" + dateOfDetection + ", altitude=" + altitude
                + ", distanceFromDam=" + distanceFromDam + "]";
    }

}
