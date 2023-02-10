package com.cathaybk.coindesk.dto;

import javax.validation.constraints.NotBlank;

public class CoinDescDto extends AbstractDto{

    @NotBlank
    private String code;

    @NotBlank
    private String zhtwDesc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getZhtwDesc() {
        return zhtwDesc;
    }

    public void setZhtwDesc(String zhtwDesc) {
        this.zhtwDesc = zhtwDesc;
    }
}
