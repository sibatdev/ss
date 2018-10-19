package com.itcwt.ss.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 15:43
 */
public class SsConf {

    private static final String DEFAULT_SERVER = "0.0.0.0";
    private static final String DEFAULT_LOCAL_ADDRESS = "127.0.0.1";
    private static final Integer DEFAULT_LOCAL_PORT = 1080;
    private static final Long DEFAULT_TIMEOUT = 300L;
    private static final String DEFAULT_METHOD = "aes-256-cfb";

    private String server;

    @JSONField(name = "local_address")
    private String localAddress;

    @JSONField(name = "local_port")
    private Integer localPort;

    @JSONField(name = "port_password")
    public Map<String, String> portPassword;

    private Long timeout;

    private String method;

    public String getServer() {
        return server;
    }

    public SsConf setServer(String server) {
        this.server = server;
        return this;
    }


    public String getLocalAddress() {
        return localAddress;
    }

    public SsConf setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public SsConf setLocalPort(Integer localPort) {
        this.localPort = localPort;
        return this;
    }

    public Map<String, String> getPortPassword() {
        return portPassword;
    }

    public SsConf setPortPassword(Map<String, String> portPassword) {
        this.portPassword = portPassword;
        return this;
    }

    public Long getTimeout() {
        return timeout;
    }

    public SsConf setTimeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public SsConf setMethod(String method) {
        this.method = method;
        return this;
    }

    public static SsConf of(Map<String, String> portPassword){
        SsConf ssConf = new SsConf();
        ssConf.setServer(DEFAULT_SERVER).
                setLocalAddress(DEFAULT_LOCAL_ADDRESS).
                setLocalPort(DEFAULT_LOCAL_PORT).
                setTimeout(DEFAULT_TIMEOUT).
                setPortPassword(portPassword).
                setMethod(DEFAULT_METHOD);
        return ssConf;
    }

}
