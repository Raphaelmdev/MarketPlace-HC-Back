package br.com.hazze.cury.marketplace.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartRequestDTO(

        @Schema(example = "1")
        @NotNull(message = "O ID do usuário é obrigatório.")
        @Min(value = 1, message = "O ID do produto deve ser maior que zero.")
        Long userId
) {
}
