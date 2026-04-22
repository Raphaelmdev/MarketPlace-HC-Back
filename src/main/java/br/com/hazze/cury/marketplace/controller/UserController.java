package br.com.hazze.cury.marketplace.controller;

import br.com.hazze.cury.marketplace.dto.request.UserAdminRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.UserRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.UserStatusUpdateDTO;
import br.com.hazze.cury.marketplace.dto.response.ErrorResponseDTO;
import br.com.hazze.cury.marketplace.dto.response.UserResponseDTO;
import br.com.hazze.cury.marketplace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário comum")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO response = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Cadastrar usuário admin", description = "Cria usuário com permissões administrativas. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário administrador criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/admin")
    public ResponseEntity<UserResponseDTO> createAdmin(@RequestBody @Valid UserAdminRequestDTO dto) {
        UserResponseDTO response = userService.createAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Atualizar usuário", description = "Atualiza dados de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Atualizar status do usuário", description = "Ativa ou desativa um usuário. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio inválida",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid UserStatusUpdateDTO dto) {
        return ResponseEntity.ok(userService.updateStatus(id, dto));
    }

    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Remover usuário", description = "Remove um usuário. Acesso: ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
