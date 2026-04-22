package br.com.hazze.cury.marketplace.controller;

import br.com.hazze.cury.marketplace.dto.auth.AuthenticationRequestDTO;
import br.com.hazze.cury.marketplace.dto.auth.LoginResponseDTO;
import br.com.hazze.cury.marketplace.dto.auth.RegisterRequestDTO;
import br.com.hazze.cury.marketplace.dto.auth.RegisterResponseDTO;
import br.com.hazze.cury.marketplace.dto.response.ErrorResponseDTO;
import br.com.hazze.cury.marketplace.entities.Role;
import br.com.hazze.cury.marketplace.entities.User;
import br.com.hazze.cury.marketplace.exceptions.BusinessException;
import br.com.hazze.cury.marketplace.repositories.UserRepository;
import br.com.hazze.cury.marketplace.Security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Autenticação e cadastro)")
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna o token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),

            @ApiResponse(responseCode = "403", description = "Credenciais inválidas",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        String token = jwtService.generateToken(user);

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                "Bearer",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = RegisterResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO data) {
        if (repository.existsByEmail(data.email())) {
            throw new BusinessException("Email já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());

        User newUser = new User(data.email(), encryptedPassword);
        newUser.setRole(Role.CLIENT);
        newUser.setActive(true);

        newUser.setName(data.name());
        newUser.setCpf(data.cpf());
        newUser.setCep(data.cep());
        newUser.setPhone(data.phone());

        User savedUser = repository.save(newUser);

        RegisterResponseDTO response = new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getCpf(),
                savedUser.getCep(),
                savedUser.getActive(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}