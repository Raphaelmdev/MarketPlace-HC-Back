package br.com.hazze.cury.marketplace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @Schema(example = "Seu Nome")
        @NotBlank(message = "O Nome é obrigatório.")
        @Size(min = 3,max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String name,

        @Schema(example = "seuemail@email.com")
        @NotBlank(message = "O Email é obrigatório.")
        @Email(message = "O email deve ser válido.")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
        String email,

        @Schema(example = "abc123")
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)\\S{6,}$", message = "Senha deve ter no mínimo 6 caracteres, com letras e números")
        String password,

        @Schema(example = "11999999999")
        @Size(max = 11)
        @Pattern(regexp = "^\\d{10,11}$", message = "O telefone deve conter 10 ou 11 números.")
        String phone,

        @Schema(example = "12345678901")
        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(regexp = "^\\d{8}$", message = "O CEP deve conter 8 números.")
        String cep,

        @Schema(example = "12345678")
        @Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter exatamente 11 números.")
        String cpf
) {
}
