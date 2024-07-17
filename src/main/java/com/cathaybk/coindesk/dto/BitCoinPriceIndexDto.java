package com.cathaybk.coindesk.dto;

import javax.validation.constraints.NotBlank;

public class BitCoinPriceIndexDto extends AbstractDto{

    @NotBlank
    private String code;
    @NotBlank
    private Float exchangeRate;
//
    private String description;
    private String zhtwDesc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZhtwDesc() {
        return zhtwDesc;
    }

    public void setZhtwDesc(String zhtwDesc) {
        this.zhtwDesc = zhtwDesc;
    }
}
