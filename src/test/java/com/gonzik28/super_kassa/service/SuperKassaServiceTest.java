package com.gonzik28.super_kassa.service;

import com.gonzik28.super_kassa.TestBase;
import com.gonzik28.super_kassa.dto.ModifyRequestDto;
import com.gonzik28.super_kassa.dto.ModifyResponseDto;
import com.gonzik28.super_kassa.entity.JsonbObj;
import com.gonzik28.super_kassa.entity.SkExampleEntity;
import com.gonzik28.super_kassa.repository.SkExampleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuperKassaServiceTest extends TestBase {
    @Autowired
    private SuperKassaService superKassaService;

    @Autowired
    private SkExampleRepository skExampleRepository;

    @Test
    public void addBDTest() {

        JsonbObj json = new JsonbObj();
        json.setCurrent(1);

        SkExampleEntity entity = new SkExampleEntity();
        entity.setId(7);
        entity.setObj(json);
        skExampleRepository.save(entity);

        ModifyRequestDto modifyRequestDto = new ModifyRequestDto();
        modifyRequestDto.setId(7);
        modifyRequestDto.setAdd(5);

        ModifyResponseDto responseDto = superKassaService.modify(modifyRequestDto);

        assertNotNull(entity);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getCurrent());
        assertEquals(6, responseDto.getCurrent());

    }

    @Test
    public void addBDNegativeTest() {

        JsonbObj json = new JsonbObj();
        json.setCurrent(6);

        SkExampleEntity entity = new SkExampleEntity();
        entity.setId(7);
        entity.setObj(json);
        skExampleRepository.save(entity);

        ModifyRequestDto modifyRequestDto = new ModifyRequestDto();
        modifyRequestDto.setId(7);
        modifyRequestDto.setAdd(-9);

        ModifyResponseDto responseDto = superKassaService.modify(modifyRequestDto);

        assertNotNull(entity);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getCurrent());
        assertEquals(-3, responseDto.getCurrent());

    }

    @Test
    public void addBDNullTest_mustFail() {

        JsonbObj json = new JsonbObj();
        json.setCurrent(6);

        SkExampleEntity entity = new SkExampleEntity();
        entity.setId(7);
        entity.setObj(json);
        skExampleRepository.save(entity);

        ModifyRequestDto modifyRequestDto = new ModifyRequestDto();
        modifyRequestDto.setId(7);
        modifyRequestDto.setAdd(null);


        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> superKassaService.modify(modifyRequestDto));

        assertEquals(thrown.getClass(), IllegalArgumentException.class);
        assertEquals(thrown.getMessage(), "add parameter must be not null");
    }

    @Test
    void idNotInDb_mustFail() {
        ModifyRequestDto modifyRequestDto = new ModifyRequestDto();
        modifyRequestDto.setId(3);
        modifyRequestDto.setAdd(5);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> superKassaService.modify(modifyRequestDto));

        assertEquals(thrown.getClass(), IllegalArgumentException.class);
        assertEquals(thrown.getMessage(), "No entity with id = 3");
    }


    @Test
    void idNull_mustFail() {
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> superKassaService.modify(new ModifyRequestDto()));
        assertEquals(thrown.getClass(), IllegalArgumentException.class);
        assertEquals(thrown.getMessage(), "id parameter must be not null");
    }

    @Test
    void nullRequest_mustFail() {
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> superKassaService.modify(null));
        assertEquals(thrown.getClass(), IllegalArgumentException.class);
        assertEquals(thrown.getMessage(), "request must be not null");
    }

}