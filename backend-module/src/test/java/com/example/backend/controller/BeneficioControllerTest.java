package com.example.backend.controller;

import com.example.backend.application.dto.BeneficioDTO;
import com.example.backend.application.dto.TransferRequest;
import com.example.backend.application.service.BeneficioAppService;
import com.example.backend.integration.BeneficioRemoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficioController.class)
class BeneficioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BeneficioAppService appService;

  @MockBean
  private BeneficioRemoteService remoteService;

  @Test
  void listar_shouldReturnOk() throws Exception {
    BeneficioDTO dto = new BeneficioDTO();
    dto.setId(1L);

    when(appService.listar()).thenReturn(List.of(dto));

    mockMvc.perform(get("/beneficios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
  }

  @Test
  void criar_shouldReturnCreated() throws Exception {
    BeneficioDTO dto = new BeneficioDTO();
    dto.setId(2L);
    dto.setNome("VA");

    when(appService.criar(any())).thenReturn(dto);

    mockMvc.perform(post("/beneficios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                             {"nome": "VA"}
                        """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2));
  }

  @Test
  void buscar_shouldReturnOk() throws Exception {
    BeneficioDTO dto = new BeneficioDTO();
    dto.setId(3L);

    when(appService.buscar(3L)).thenReturn(dto);

    mockMvc.perform(get("/beneficios/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(3));
  }

  @Test
  void delete_shouldReturnNoContent() throws Exception {
    mockMvc.perform(delete("/beneficios/5"))
            .andExpect(status().isOk());

    verify(appService).deletar(5L);
  }

  @Test
  void transfer_shouldCallRemoteService() throws Exception {
    mockMvc.perform(post("/beneficios/transfer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                             {"fromId":1,"toId":2,"amount":100.00,"lockingMode":"PESSIMISTIC_WRITE"}
                        """))
            .andExpect(status().isOk())
            .andExpect(content().string("Transfer ok"));

    verify(remoteService).transferir(any(TransferRequest.class));
  }
}
