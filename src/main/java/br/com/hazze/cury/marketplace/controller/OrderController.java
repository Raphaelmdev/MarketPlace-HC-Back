package br.com.hazze.cury.marketplace.controller;


import br.com.hazze.cury.marketplace.dto.request.OrderRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.OrderStatusUpdateDTO;
import br.com.hazze.cury.marketplace.dto.response.ErrorResponseDTO;
import br.com.hazze.cury.marketplace.dto.response.OrderResponseDTO;
import br.com.hazze.cury.marketplace.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "Pedidos")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido a partir dos itens enviados. Acesso: CLIENT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Estoque insuficiente ou dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(responseCode = "404", description = "Usuário ou produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO dto) {
        OrderResponseDTO response = orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Listar pedidos por usuário", description = "Retorna os pedidos de um usuário. Acesso: CLIENT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada"),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id,
                                                         @RequestBody @Valid OrderStatusUpdateDTO dto) {
        return ResponseEntity.ok(orderService.updateStatus(id, dto));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Remover pedido", description = "Remove um pedido. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
