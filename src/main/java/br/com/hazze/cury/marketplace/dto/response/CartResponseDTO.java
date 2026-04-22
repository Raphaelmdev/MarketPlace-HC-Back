package br.com.hazze.cury.marketplace.dto.response;

import br.com.hazze.cury.marketplace.entities.CartStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        @Schema(example = "1")
        Long id,

        @Schema(example = "9000.00")
        BigDecimal total,

        @Schema(example = "ACTIVE")
        CartStatus status,

        @Schema(example = "1")
        Long userId,

        List<CartItemResponseDTO> items
) {
}
