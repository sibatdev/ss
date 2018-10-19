package com.itcwt.ss.util;

import java.util.UUID;

/**
 * @author cwt
 * @create by cwt on 2018-10-17 11:02
 */
public class UuidUtil {

    private static final String BAR_EN = "-";

    /**
     * 获取UUID
     * @return
     */
    public static UUID randomUUID(){
        return UUID.randomUUID();
    }

    /**
     * 去掉UUID中间的【-】 去掉之后长度为 32 个字符
     * @return
     */
    public static String getUUID(){
        String uuid = randomUUID().toString();
        return uuid.replace(BAR_EN, "");
    }

}
