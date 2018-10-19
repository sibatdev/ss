package com.itcwt.ss.util;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 21:00
 */
public class JsonUtil {

    public static JSONReader readJsonFile(String path) throws FileNotFoundException{
        System.out.println(path);
        return new JSONReader(new FileReader(path));
    }

    public static void writeJsonFile(String path, Object obj) throws IOException {
        JSONWriter writer = new JSONWriter(new FileWriter(path));
        writer.writeObject(obj);
        writer.close();
    }

    public static void closeJSONReader(JSONReader reader){
        reader.close();
    }

}
