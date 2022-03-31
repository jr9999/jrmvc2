package com.jr.config;

public class RedisConfiguration {

    private Integer port;

    private String primaryEndpoint;

    private String readerEndpoint;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPrimaryEndpoint() {
        return primaryEndpoint;
    }

    public void setPrimaryEndpoint(String primaryEndpoint) {
        this.primaryEndpoint = primaryEndpoint;
    }

    public String getReaderEndpoint() {
        return readerEndpoint;
    }

    public void setReaderEndpoint(String readerEndpoint) {
        this.readerEndpoint = readerEndpoint;
    }
    
}
