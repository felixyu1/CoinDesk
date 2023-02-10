package com.cathaybk.coindesk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COIN_DESC")
public class CoinDesc extends AbstractModel {

    @Id
    @Column(name = "CODE")
    String code;

    @Column(name = "ZHTW_DESC")
    String zhtwDesc;

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
