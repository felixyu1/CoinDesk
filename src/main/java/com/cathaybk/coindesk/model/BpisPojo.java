package com.cathaybk.coindesk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpisPojo extends AbstractModel {
    private Map<String, Object> time;
    private Map<String, BpiPojo> bpi;

    public Map<String, Object> getTime() {
        return time;
    }

    public void setTime(Map<String, Object> time) {
        this.time = time;
    }

    public Map<String, BpiPojo> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, BpiPojo> bpi) {
        this.bpi = bpi;
    }
}
