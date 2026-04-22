package br.com.hazze.cury.marketplace.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponseDTO(
        @Schema(example = "400")
        int status,

        @Schema(example = "Bad Request")
        String error,

        @Schema(example = "email: O e-mail deve ser válido.")
        String message,

        @Schema(example = "/auth/login")
        String path
) {}