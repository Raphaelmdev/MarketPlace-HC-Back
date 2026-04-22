package br.com.hazze.cury.marketplace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequestDTO(

        @Schema(example = "1")
        @NotNull(message = "O ID do usuário é obrigatório.")
        @Min(value = 1, message = "O ID do produto deve ser maior que zero.")
        Long userId,

        @NotEmpty(message = "O pedido deve ter pelo menos 1 item.")
        @Size(min = 1, message = "O pedido deve ter pelo menos 1 item.")
        List<@Valid OrderItemRequestDTO> items
) {
}
