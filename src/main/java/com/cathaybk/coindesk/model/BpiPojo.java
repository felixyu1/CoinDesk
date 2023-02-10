package com.cathaybk.coindesk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpiPojo extends AbstractModel {

    private String code;
    private String description;
    @JsonProperty("rate_float")
    private Float exchangeRate;
    private String zhtwDesc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getZhtwDesc() {
        return zhtwDesc;
    }

    public void setZhtwDesc(String zhtwDesc) {
        this.zhtwDesc = zhtwDesc;
    }
}
