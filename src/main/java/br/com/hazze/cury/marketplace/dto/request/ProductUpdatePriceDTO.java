package br.com.hazze.cury.marketplace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductUpdatePriceDTO(

        @Schema(example = "3999.90")
        @NotNull
        @DecimalMin("0.01")
        BigDecimal price

) {}
