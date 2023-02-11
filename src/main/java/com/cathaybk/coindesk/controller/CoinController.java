package com.cathaybk.coindesk.controller;

import com.cathaybk.coindesk.dto.BitCoinPriceIndexesDto;
import com.cathaybk.coindesk.dto.CoinDescDto;
import com.cathaybk.coindesk.mapper.ModelDtoMapper;
import com.cathaybk.coindesk.model.BpisPojo;
import com.cathaybk.coindesk.model.CoinDesc;
import com.cathaybk.coindesk.service.CoinService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CoinController {

    @Autowired
    private CoinService coinService;

    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    @Qualifier("bpisPojoBitCoinPriceIndexesDtoMapper")
    private ModelDtoMapper bpisMapper;

    @PostMapping("/coinDesc")
    public ResponseEntity<CoinDescDto> createCoinDesc(
            @RequestBody @Valid CoinDescDto coinDescDto) {
        CoinDesc coinDesc = modelMapper.map(coinDescDto, CoinDesc.class);
        coinService.createCoinDesc(coinDesc);
        return ResponseEntity.status(HttpStatus.CREATED).body(coinDescDto);
    }

    @PutMapping("/coinDesc/{code}")
    public ResponseEntity<CoinDescDto> updateCoinDesc(
            @PathVariable String code,
            @RequestBody @Valid CoinDescDto coinDescDto) {


        CoinDesc coinDesc = modelMapper.map(coinDescDto, CoinDesc.class);
        coinService.updateCoinDesc(code, coinDesc);
        return ResponseEntity.status(HttpStatus.OK).body(coinDescDto);
    }

    @DeleteMapping("/coinDesc/{code}")
    public ResponseEntity<?> deleteCoinDesc(@PathVariable String code) throws Exception {
        coinService.deleteCoinDesc(code);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/coinDescs")
    public ResponseEntity<List<CoinDescDto>> getAllCoinDesc() {
        List<CoinDesc> coinDescList = coinService.getAllCoinDesc();
        if(coinDescList != null) {
            List<CoinDescDto> dtos = coinDescList
                    .stream()
                    .map(coinDesc -> modelMapper.map(coinDesc, CoinDescDto.class))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @GetMapping("/coinDesc/{code}")
    public ResponseEntity<CoinDescDto> getCoinDesc(@PathVariable String code) {
        CoinDesc coinDesc = coinService.getCoinDesc(code);
        if(coinDesc != null) {
            CoinDescDto dto = modelMapper.map(coinDesc, CoinDescDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/bpis")
    public ResponseEntity<BitCoinPriceIndexesDto> getBPIs() {
        BitCoinPriceIndexesDto bpisDto = null;

        BpisPojo bpisPojo = coinService.getBpisFromCoinDesk();

        coinService.fillBpisWithCoinDesc(bpisPojo);
        bpisDto = (BitCoinPriceIndexesDto)bpisMapper.convertToDto(bpisPojo);

        return ResponseEntity.status(HttpStatus.OK).body(bpisDto);
    }
}
