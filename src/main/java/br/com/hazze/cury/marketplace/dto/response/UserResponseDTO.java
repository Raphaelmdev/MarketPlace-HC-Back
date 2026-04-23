package br.com.hazze.cury.marketplace.dto.response;

import br.com.hazze.cury.marketplace.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserResponseDTO(
        @Schema(example = "1")
        Long id,

        @Schema(example = "Raphael Martins")
        String name,

        @Schema(example = "raphael@email.com")
        String email,

        @Schema(example = "11999999999")
        String phone,

        @Schema(example = "12345678901")
        String cpf,
        @Schema(example = "12345678")
        String cep,

        @Schema(example = "Rua Exemplo")
        String street,

        @Schema(example = "123")
        String number,

        @Schema(example = "Apto 12, Bloco B")
        String complement,

        @Schema(example = "Centro")
        String neighborhood,

        @Schema(example = "São Paulo")
        String city,

        @Schema(example = "SP")
        String state,

        @Schema(example = "true")
        Boolean active,

        @Schema(example = "CLIENT")
        Role role,

        @Schema(example = "2026-04-21T10:30:00")
        LocalDateTime createdAt
) {
}
