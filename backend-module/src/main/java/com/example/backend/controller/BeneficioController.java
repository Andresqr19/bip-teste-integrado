package com.example.backend.controller;

import com.example.backend.application.dto.BeneficioDTO;
import com.example.backend.application.dto.TransferRequest;
import com.example.backend.application.exception.NotFoundException;
import com.example.backend.application.service.BeneficioAppService;
import com.example.backend.integration.BeneficioRemoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/beneficios")
@RequiredArgsConstructor
@Tag(name = "Benefícios", description = "Operações com benefícios e transferências via integração EJB")
public class BeneficioController {
  private final BeneficioAppService service;

  private final BeneficioRemoteService beneficioRemoteService;

  @Operation(summary = "Listar benefícios")
  @ApiResponse(
          responseCode = "200",
          description = "Lista de benefícios",
          content = @Content(mediaType = "application/json")
  )
  @GetMapping
  public List<BeneficioDTO> listar() { return service.listar(); }

  @Operation(summary = "Criar benefício")
  @ApiResponse(responseCode = "201", description = "Benefício criado")
  @PostMapping
  public BeneficioDTO criar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Dados do benefício",
          required = true,
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BeneficioDTO.class),
                  examples = @ExampleObject(
                          value = """
                                  { \"nome\": \"Vale Alimentação\", \"descricao\": \"Cartão alimentação\",
                                   \"valor\": 500.00, \"ativo\": true }
                                  """
                  )
          )
  )
                            @RequestBody BeneficioDTO dto) {
    return service.criar(dto);
  }

  @Operation(summary = "Buscar benefício por ID")
  @GetMapping("/{id}")
  public BeneficioDTO buscar(@PathVariable Long id) { return service.buscar(id); }

  @PutMapping("/{id}")
  @Operation(
          summary = "Atualizar benefício existente",
          description = "Atualiza um benefício pelo ID",
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = BeneficioDTO.class),
                          examples = @ExampleObject(
                                  value = """
                    { "nome": "Vale Refeição", "descricao": "Cartão refeição",
                      "valor": 600.00, "ativo": true }
                    """
                          )
                  )
          )
  )
  public ResponseEntity<?> atualizar(
          @PathVariable Long id,
          @RequestBody BeneficioDTO dto) {
    try {
      BeneficioDTO atualizado = service.atualizar(id, dto);
      return ResponseEntity.ok(atualizado);
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(Map.of("error", "Benefício não encontrado", "details", e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest()
              .body(Map.of("error", "Dados inválidos", "details", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
              .body(Map.of("error", "Falha ao atualizar benefício", "details", e.getMessage()));
    }
  }



  @Operation(summary = "Deletar benefício")
  @DeleteMapping("/{id}")
  public void deletar(@PathVariable Long id) { service.deletar(id); }

  @Operation(
          summary = "Transferir saldo entre benefícios",
          description = "Executa transferência via EJB remoto",
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = TransferRequest.class),
                          examples = @ExampleObject(
                                  value = "{\n" +
                                          "  \"fromId\": 1,\n" +
                                          "  \"toId\": 2,\n" +
                                          "  \"amount\": 150.00,\n" +
                                          "  \"lockingMode\": \"PESSIMISTIC_WRITE\"\n" +
                                          "}"
                          )
                  )
          )
  )
  @PostMapping("/transfer")
  public ResponseEntity<?> transfer(@RequestBody TransferRequest req) {
    try {
      beneficioRemoteService.transferir(req);
      return ResponseEntity.ok(Map.of("message", "Transfer ok"));
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(Map.of("error", "Benefício não encontrado", "details", e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest()
              .body(Map.of("error", "Dados inválidos", "details", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
              .body(Map.of("error", "Falha interna", "details", e.getMessage()));
    }
  }

}
