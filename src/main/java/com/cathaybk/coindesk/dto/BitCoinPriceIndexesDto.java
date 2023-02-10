package com.cathaybk.coindesk.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class BitCoinPriceIndexesDto extends AbstractDto {

    @NotNull
    private String updatedDateTime;

    @NotEmpty
    private List<BitCoinPriceIndexDto> bpis;

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(String updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public List<BitCoinPriceIndexDto> getBpis() {
        return bpis;
    }

    public void setBpis(List<BitCoinPriceIndexDto> bpis) {
        this.bpis = bpis;
    }
}
