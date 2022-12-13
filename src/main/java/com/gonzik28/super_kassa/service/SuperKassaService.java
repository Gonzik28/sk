package com.gonzik28.super_kassa.service;

import com.gonzik28.super_kassa.converter.ModifyConverter;
import com.gonzik28.super_kassa.dto.ModifyRequestDto;
import com.gonzik28.super_kassa.dto.ModifyResponseDto;
import com.gonzik28.super_kassa.entity.SkExampleEntity;
import com.gonzik28.super_kassa.repository.SkExampleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.Optional;

@Service
@Transactional
public class SuperKassaService {
    private final SkExampleRepository skExampleRepository;

    public SuperKassaService(SkExampleRepository skExampleRepository) {
        this.skExampleRepository = skExampleRepository;
    }

    public ModifyResponseDto modify(ModifyRequestDto request) {
        checkRequest(request);
        Optional<SkExampleEntity> entities = skExampleRepository.findWithLockingById(request.getId());
        if (entities.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No entity with id = {0}", request.getId()));
        }
        SkExampleEntity entity = entities.get();
        entity.getObj().setCurrent(entity.getObj().getCurrent() + request.getAdd());
        skExampleRepository.save(entity);
        return ModifyConverter.entityToDto(entity);
    }

    private void checkRequest(ModifyRequestDto request) {
        if (request == null) {
            throw new IllegalArgumentException("request must be not null");
        }
        if (request.getId() == null) {
            throw new IllegalArgumentException("id parameter must be not null");
        }
        if (request.getAdd() == null) {
            throw new IllegalArgumentException("add parameter must be not null");
        }
    }
}
