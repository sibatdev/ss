package com.itcwt.ss.service.impl;

import com.itcwt.ss.service.GuavaCacheService;

import java.util.concurrent.TimeUnit;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 19:24
 */
public class KeyGuavaCacheService extends GuavaCacheService<String,String> {

    private static GuavaCacheService cache;

    public static GuavaCacheService getCache(){
        if (cache == null){
            synchronized (KeyGuavaCacheService.class){
                if (cache == null){
                    cache = new KeyGuavaCacheService().setExpire(1, TimeUnit.MINUTES);
                }
            }
        }
        return cache;
    }
}
