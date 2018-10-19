package com.itcwt.ss.util;

import java.io.IOException;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 20:52
 */
public class ShellUtil {

    public void runShell(String shPath){
        Process exec = null;
        try {
            exec = Runtime.getRuntime().exec(shPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
