package com.cll.security.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2018/6/2
 */

@Entity(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String password;

    private String salt;

    private String ip;

    private Integer role;

    private Integer state;

    private Date createTime;

    private Date lastLogin;

    public User() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getIp() {
        return ip;
    }

    public Integer getRole() {
        return role;
    }

    public Integer getState() {
        return state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", ip='" + ip + '\'' +
                ", role=" + role +
                ", state=" + state +
                ", createTime=" + createTime +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
