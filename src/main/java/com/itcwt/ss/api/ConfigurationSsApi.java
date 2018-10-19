package com.itcwt.ss.api;

import com.alibaba.fastjson.JSONReader;
import com.itcwt.ss.constant.Constants;
import com.itcwt.ss.entity.SsConf;
import com.itcwt.ss.service.GuavaCacheService;
import com.itcwt.ss.service.impl.KeyGuavaCacheService;
import com.itcwt.ss.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 14:53
 */
@RestController("conf")
public class ConfigurationSsApi {

    @Value("${ss.conf.path}")
    public String ssPath;

    private static final GuavaCacheService<String, String> cache = KeyGuavaCacheService.getCache();

    private static final String KEY = "SK";

    @PostMapping("key")
    public R getKey(@RequestParam String password){
        if (password == null || password.length() == 0){
            return R.ok().setMessage("就算你是哥，也不能不给密码吧？");
        }
        if (!password.equals(TimeUtil.parseDateToString(new Date(), TimeUtil.PATTERN_MMddHHmm + Constants.PWD_SUFIX))){
            return R.ok().setMessage("哥，密码也不能给个错误的吧？");
        }
        String sk = UuidUtil.getUUID();
        cache.put(KEY, sk);
        return R.ok().setMessage("成功拿到key").setData(sk);
    }

    /**
     * （1）公认端口（WellKnownPorts）：从0到1023，它们紧密绑定（binding）于一些服务。通常这些端口的通讯明确表明了某种服务的协议。例如：80端口实际上总是HTTP通讯。
     * （2）注册端口（RegisteredPorts）：从1024到49151。它们松散地绑定于一些服务。也就是说有许多服务绑定于这些端口，这些端口同样用于许多其它目的。例如：许多系统处理动态端口从1024左右开始。
     * （3）动态和/或私有端口（Dynamicand/orPrivatePorts）：从49152到65535。理论上，不应为服务分配这些端口。实际上，机器通常从1024起分配动态端口。但也有例外：SUN的RPC端口从32768开始。
     * @param portPassword
     * @return
     */
    @PostMapping(value = "spp/{sk}")
    public R setPortPassword(@RequestBody Map<String, String> portPassword, @PathVariable("sk") String sk){

        if (!checkSk(sk)){
            return R.error().setMessage("sk 验证失败，请重新获取");
        }

        if (portPassword == null || portPassword.size() == 0){
            return R.error().setMessage("需要配置的端口和密码不能为空！");
        }

        String confPath = ssPath + Constants.SS_CONFIG_FILE_NAME;

        // 读取ss配置文件
        JSONReader reader = null;
        try {
            reader = JsonUtil.readJsonFile(confPath);
        } catch (FileNotFoundException e) {
            return R.error().setMessage("配置文件不见了！");
        }
        SsConf ssConf = reader.readObject(SsConf.class);

        // 关闭JSONReader
        JsonUtil.closeJSONReader(reader);

        Map<String, String> oldPortPassword = ssConf.getPortPassword();

        for (Map.Entry<String, String> entry : portPassword.entrySet()){
            String key = entry.getKey();
            String password = entry.getValue();
            if (oldPortPassword.containsKey(key)){
                return R.error().setMessage("已存在 端口" + key);
            }
            int keyParse = StringUtil.getInt(key, -1);
            if (-1 == keyParse){
                return R.error().setMessage("请输入正确的端口");
            }else if (1024 > keyParse || keyParse > 49151){
                return R.error().setMessage("请将端口范围设置为 1024-49151 之间");
            }
            if (!Pattern.matches("^[A-Za-z0-9]\\w{5,17}$", password)){
                return R.error().setMessage("端口:" + key + " 密码格式不对,密码只能数字或字母或数字和字母的组合，长度为6 - 18");
            }
            oldPortPassword.put(key, password);
        }

        try {
            JsonUtil.writeJsonFile(confPath, ssConf);
        } catch (IOException e) {
            return R.error().setMessage("重写配置文件出错");
        }

        // 调用sh脚本

        return R.ok().setMessage("配置成功");
    }

    @PostMapping("update/{sk}")
    public R updatePassword(@PathVariable("sk") String sk){
        if (!checkSk(sk)){
            return R.error().setMessage("sk 验证失败，请重新获取");
        }

        return R.ok();
    }

    public R deletePortPassword(){
        return R.ok();
    }

    private boolean checkSk(String sk){
        String skValidation = cache.get(KEY);
        if (skValidation == null || !sk.equals(skValidation)){
            return false;
        }
        // 验证通过
        return true;
    }

}
