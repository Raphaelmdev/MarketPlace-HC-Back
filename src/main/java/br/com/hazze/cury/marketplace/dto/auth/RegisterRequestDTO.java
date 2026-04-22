package br.com.hazze.cury.marketplace.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record RegisterRequestDTO(

        @Schema(example = "Seu Nome")
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String name,

        @Schema(example = "seuemail@email.com")
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve ser válido.")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
        String email,

        @Schema(example = "abc123")
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "A senha deve conter pelo menos uma letra e um número."
        )
        String password,

        @Schema(example = "11999999999")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "O telefone deve conter 10 ou 11 números."
        )
        String phone,

        @Schema(example = "12345678901")
        @Pattern(
                regexp = "^\\d{11}$",
                message = "O CPF deve conter exatamente 11 números."
        )
        String cpf,

        @Schema(example = "12345678")
        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(
                regexp = "^\\d{8}$",
                message = "O CEP deve conter 8 números."
        )
        String cep
) {
}