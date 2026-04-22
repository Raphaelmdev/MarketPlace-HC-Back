package br.com.hazze.cury.marketplace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateDTO(

        @Schema(example = "false")
        @NotNull(message = "O status ativo é obrigatório.")
                Boolean active
) {
}
