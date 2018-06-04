package com.cll.security.web.common;

/**
 * @author chenliangliang
 * @date 2018/6/5
 */
public class Constant {

    public static String getAppId(String key) {
        return Long.toHexString(key.hashCode());
    }
}
