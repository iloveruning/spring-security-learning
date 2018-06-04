package com.cll.security.web.utils;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/6/2
 */
public class R implements Serializable {

    private boolean flag;

    private String msg;

    private Object data;


    public R(boolean flag, String msg, Object data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public static R success(Object data){
        return new R(true,"ok",data);
    }

    public static R ok(){
        return success(null);
    }

    public static R fail(String msg){
        return new R(false,msg,null);
    }

    public static R result(boolean flag,String msg,Object data){
        return new R(flag, msg, data);
    }

    public R() {
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


}
