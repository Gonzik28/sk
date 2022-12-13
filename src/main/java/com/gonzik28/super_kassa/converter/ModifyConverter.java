package com.gonzik28.super_kassa.converter;

import com.gonzik28.super_kassa.dto.ModifyResponseDto;
import com.gonzik28.super_kassa.entity.SkExampleEntity;

public class ModifyConverter {
    public static ModifyResponseDto entityToDto(SkExampleEntity entity) {
        ModifyResponseDto responseDto = new ModifyResponseDto();
        responseDto.setCurrent(entity.getObj().getCurrent());
        return responseDto;
    }
}
