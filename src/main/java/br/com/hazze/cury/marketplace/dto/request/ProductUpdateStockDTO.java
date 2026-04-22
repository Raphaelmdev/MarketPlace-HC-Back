package br.com.hazze.cury.marketplace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdateStockDTO(

        @Schema(example = "25")
        @NotNull
        @PositiveOrZero
        Integer stock

) {}