package com.gonzik28.super_kassa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonzik28.super_kassa.dto.ModifyRequestDto;
import com.gonzik28.super_kassa.dto.ModifyResponseDto;
import com.gonzik28.super_kassa.service.SuperKassaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuperKassaController.class)
class SuperKassaControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private SuperKassaService superKassaService;

    @Test
    public void testControllerWithCorrectResponse() throws Exception {
        ModifyResponseDto response = new ModifyResponseDto();
        response.setCurrent(5);
        Mockito.when(superKassaService.modify(Mockito.any())).thenReturn(response);

        ModifyRequestDto request = new ModifyRequestDto();
        request.setAdd(5);
        request.setId(1);

        mvc.perform(MockMvcRequestBuilders
                        .post("/modify")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.current").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.current").value(5));
    }

    @Test
    public void testControllerWithError() throws Exception {
        ModifyRequestDto request = new ModifyRequestDto();
        request.setAdd(5);
        request.setId(1);
        Mockito.when(superKassaService.modify(Mockito.any())).thenThrow(new IllegalArgumentException("Some error"));

        mvc.perform(MockMvcRequestBuilders
                        .post("/modify")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(418))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error_message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unable perform sum operation"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error_message").value("Some error"));
    }

}