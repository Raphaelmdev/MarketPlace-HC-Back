package br.com.hazze.cury.marketplace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryAdminRequestDTO(

        @Schema(example = "Eletrônicos")
        @NotBlank(message = "O nome da categoria é obrigatório.")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String name,

        @Schema(example = "Produtos eletrônicos em geral")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
        String description

) {
}
