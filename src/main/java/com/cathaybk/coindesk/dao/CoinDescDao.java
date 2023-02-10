package com.cathaybk.coindesk.dao;

import com.cathaybk.coindesk.model.CoinDesc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinDescDao extends JpaRepository<CoinDesc, String> {

}
