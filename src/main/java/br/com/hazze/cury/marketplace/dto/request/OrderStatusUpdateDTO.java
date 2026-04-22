package br.com.hazze.cury.marketplace.dto.request;

import br.com.hazze.cury.marketplace.entities.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateDTO(

        @Schema(example = "PAID")
        @NotNull(message = "O status é obrigatório.")
        OrderStatus status

) {}
