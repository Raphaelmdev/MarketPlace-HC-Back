package br.com.hazze.cury.marketplace.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryResponseDTO(
        @Schema(example = "1")
        Long id,

        @Schema(example = "Eletrônicos")
        String name,

        @Schema(example = "Produtos eletrônicos em geral")
        String description
) {
}
