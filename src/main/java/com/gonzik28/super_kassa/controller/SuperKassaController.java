package com.gonzik28.super_kassa.controller;

import com.gonzik28.super_kassa.dto.ModifyRequestDto;
import com.gonzik28.super_kassa.dto.ModifyResponseDto;
import com.gonzik28.super_kassa.service.SuperKassaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuperKassaController {
    private final SuperKassaService superKassaService;

    public SuperKassaController(SuperKassaService superKassaService) {
        this.superKassaService = superKassaService;
    }

    @PostMapping("/modify")
    public ResponseEntity<ModifyResponseDto> modify(@RequestBody ModifyRequestDto request) {
        return ResponseEntity.ok(superKassaService.modify(request));
    }

}
