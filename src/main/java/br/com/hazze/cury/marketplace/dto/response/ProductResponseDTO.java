package br.com.hazze.cury.marketplace.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        @Schema(example = "1")
        Long id,

        @Schema(example = "Notebook Dell")
        String name,

        @Schema(example = "Notebook i7 16GB RAM")
        String description,

        @Schema(example = "4500.00")
        BigDecimal price,

        @Schema(example = "10")
        Integer stock,

        @Schema(example = "true")
        Boolean active,

        @Schema(example = "https://site.com/imagem.png")
        String imageUrl,

        @Schema(example = "1")
        Long categoryId,

        @Schema(example = "Eletrônicos")
        String categoryName,

        @Schema(example = "2026-04-21T10:30:00")
        LocalDateTime createdAt
) {
}
