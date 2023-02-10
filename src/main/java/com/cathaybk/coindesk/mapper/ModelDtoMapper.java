package com.cathaybk.coindesk.mapper;

import com.cathaybk.coindesk.dto.AbstractDto;
import com.cathaybk.coindesk.model.AbstractModel;

public interface ModelDtoMapper {

    public AbstractDto convertToDto(AbstractModel model);
}
