package com.cathaybk.coindesk.mapper.impl;

import com.cathaybk.coindesk.dto.BitCoinPriceIndexDto;
import com.cathaybk.coindesk.dto.BitCoinPriceIndexesDto;
import com.cathaybk.coindesk.mapper.ModelDtoMapper;
import com.cathaybk.coindesk.model.AbstractModel;
import com.cathaybk.coindesk.model.BpiPojo;
import com.cathaybk.coindesk.model.BpisPojo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BpisPojoBitCoinPriceIndexesDtoMapper implements ModelDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public BitCoinPriceIndexesDto convertToDto(AbstractModel model) {
        BpisPojo bpisPojo = (BpisPojo) model;

        //複製BPI值至DTO
        BitCoinPriceIndexesDto bpisDto = new BitCoinPriceIndexesDto();
        List<BitCoinPriceIndexDto> bpiDtoList = new ArrayList<>();

        for (String k: bpisPojo.getBpi().keySet()) {
            BpiPojo bpip = bpisPojo.getBpi().get(k);
            BitCoinPriceIndexDto bpiDto =
                    modelMapper.map(bpip, BitCoinPriceIndexDto.class);
            bpiDtoList.add(bpiDto);
        }

        bpisDto.setBpis(bpiDtoList);

        //處理BPI更新時間，日期時間格式化為系統所在時區 yyyy/MM/dd HH:mm:ss
        Map<String, Object> timeMap = bpisPojo.getTime();
        String timeStr = (String)timeMap.get("updatedISO");
        ZonedDateTime z0 = ZonedDateTime.parse(timeStr);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String updatedDateTimeStr =
                z0.withZoneSameInstant(ZoneId.systemDefault()).format(formatter1);

        bpisDto.setUpdatedDateTime(updatedDateTimeStr);

        return bpisDto;
    }
}
