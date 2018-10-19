package com.itcwt.ss.util;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 17:46
 */
public class StringUtil {

    public static int getInt(String value, int defaultValue){
        Integer parse;
        try {
            parse = Integer.valueOf(value);
        }catch (Exception e){
            parse = defaultValue;
        }
        return parse;
    }

}
