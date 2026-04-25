package br.com.hazze.cury.marketplace.dto.request;

import br.com.hazze.cury.marketplace.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UserAdminRequestDTO(

        @Schema(
                description = "Nome do administrador.",
                example = "Administrador do Sistema",
                requiredMode = REQUIRED
        )
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String name,

        @Schema(
                description = "E-mail do administrador.",
                example = "admin@alfaiataria.com",
                requiredMode = REQUIRED
        )
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
        String email,

        @Schema(
                description = "Senha do administrador.",
                example = "Senha@123",
                requiredMode = REQUIRED
        )
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres.")
        String password,

        @Schema(
                description = "Telefone do administrador (opcional, somente números).",
                example = "11999999999"
        )
        @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido.")
        String phone,

        @Schema(
                description = "CEP do administrador (opcional, somente números).",
                example = "01001000"
        )
        @Pattern(regexp = "^\\d{8}$", message = "O CEP deve conter 8 números.")
        String cep,

        @Schema(
                description = "CPF do administrador (opcional, somente números).",
                example = "12345678901"
        )
        @Pattern(regexp = "^\\d{11}$", message = "CPF inválido.")
        String cpf,

        @Schema(
                description = "Indica se o administrador está ativo.",
                example = "true",
                requiredMode = REQUIRED
        )
        @NotNull(message = "O status ativo é obrigatório.")
        Boolean active,

        @Schema(
                description = "Papel do usuário (ADMIN ou CLIENT).",
                example = "ADMIN",
                allowableValues = {"ADMIN", "CLIENT"},
                requiredMode = REQUIRED
        )
        @NotNull(message = "O papel do usuário é obrigatório.")
        Role role

) {}