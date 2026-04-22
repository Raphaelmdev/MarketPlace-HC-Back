package br.com.hazze.cury.marketplace.dto.response;

import br.com.hazze.cury.marketplace.entities.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        @Schema(example = "1")
        Long id,

        @Schema(example = "9000.00")
        BigDecimal total,

        @Schema(example = "PENDING")
        OrderStatus status,

        @Schema(example = "1")
        Long userId,

        List<OrderItemResponseDTO> items,

        @Schema(example = "2026-04-21T10:30:00")
        LocalDateTime createdAt
) {
}
