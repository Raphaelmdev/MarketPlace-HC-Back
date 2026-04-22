package br.com.hazze.cury.marketplace.dto.request;

import br.com.hazze.cury.marketplace.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record UserAdminRequestDTO(

        @Schema(example = "Administrador")
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100)
        String name,

        @Schema(example = "admin@email.com")
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email
        @Size(max = 150)
        String email,

        @Schema(example = "admin123")
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 255)
        String password,

        @Schema(example = "11988888888")
        @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido.")
        String phone,

        @Schema(example = "12345678")
        @Pattern(regexp = "^\\d{8}$", message = "O CEP deve conter 8 números.")
        String cep,

        @Schema(example = "12345678901")
        @Pattern(regexp = "^\\d{11}$", message = "CPF inválido.")
        String cpf,

        @Schema(example = "true")
        @NotNull(message = "O status ativo é obrigatório.")
        Boolean active,

        @Schema(example = "ADMIN")
        @NotNull(message = "O papel do usuário é obrigatório.")
        Role role
) {}
