package br.com.hazze.cury.marketplace.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductAdminRequestDTO(

        @Schema(example = "Notebook Dell")
        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String name,

        @Schema(example = "Notebook i7 16GB RAM")
        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
        String description,

        @Schema(example = "4500.00")
        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal price,

        @Schema(example = "10")
        @NotNull(message = "O estoque é obrigatório.")
        @PositiveOrZero(message = "O estoque deve ser zero ou maior.")
        Integer stock,

        @Schema(example = "true")
        @NotNull(message = "O status ativo é obrigatório.")
        Boolean active,

        @Schema(example = "1")
        @NotNull(message = "O ID da categoria é obrigatório.")
        Long categoryId

) {
}
