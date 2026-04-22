package br.com.hazze.cury.marketplace.controller;

import br.com.hazze.cury.marketplace.dto.request.ProductAdminRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.ProductUpdatePriceDTO;
import br.com.hazze.cury.marketplace.dto.request.ProductUpdateStockDTO;
import br.com.hazze.cury.marketplace.dto.response.ErrorResponseDTO;
import br.com.hazze.cury.marketplace.dto.response.ProductResponseDTO;
import br.com.hazze.cury.marketplace.service.ProductService;
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

@Tag(name = "Products", description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Criar produto", description = "Cadastra um novo produto. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductAdminRequestDTO dto) {
        ProductResponseDTO response = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "Listar produtos ativos", description = "Retorna apenas produtos ativos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/active")
    public ResponseEntity<List<ProductResponseDTO>> findAllActive() {
        return ResponseEntity.ok(productService.findAllActive());
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Listar produtos por categoria", description = "Retorna produtos vinculados a uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> findByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto ou categoria não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProductAdminRequestDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Atualizar preço do produto", description = "Atualiza apenas o preço. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preço atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponseDTO> updatePrice(@PathVariable Long id, @RequestBody @Valid ProductUpdatePriceDTO dto) {
        return ResponseEntity.ok(productService.updatePrice(id, dto));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Atualizar estoque do produto", description = "Atualiza apenas o estoque. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateStock(@PathVariable Long id, @RequestBody @Valid ProductUpdateStockDTO dto) {
        return ResponseEntity.ok(productService.updateStock(id, dto));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Remover produto", description = "Remove um produto. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
