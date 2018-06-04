package com.cll.security.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2018/6/4
 */

@Entity(name = "sys_log")
public class SysLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sevid;

    private String appid;

    private String level;

    private String data;

    private Date startTime;

    private Integer consume;


    public SysLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSevid() {
        return sevid;
    }

    public void setSevid(String sevid) {
        this.sevid = sevid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getConsume() {
        return consume;
    }

    public void setConsume(Integer consume) {
        this.consume = consume;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", sevid='" + sevid + '\'' +
                ", appid='" + appid + '\'' +
                ", level='" + level + '\'' +
                ", data='" + data + '\'' +
                ", startTime=" + startTime +
                ", consume=" + consume +
                '}';
    }
}
