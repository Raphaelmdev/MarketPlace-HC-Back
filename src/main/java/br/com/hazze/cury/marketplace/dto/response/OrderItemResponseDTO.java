package br.com.hazze.cury.marketplace.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        @Schema(example = "1")
        Long id,

        @Schema(example = "1")
        Long productId,

        @Schema(example = "Notebook Dell")
        String productName,

        @Schema(example = "2")
        Integer quantity,

        @Schema(example = "4500.00")
        BigDecimal unitPrice,

        @Schema(example = "9000.00")
        BigDecimal subTotal
) {
}
