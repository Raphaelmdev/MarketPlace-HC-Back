package br.com.hazze.cury.marketplace.service;

import br.com.hazze.cury.marketplace.dto.request.UserAdminRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.UserRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.UserStatusUpdateDTO;
import br.com.hazze.cury.marketplace.dto.response.UserResponseDTO;
import br.com.hazze.cury.marketplace.entities.User;
import br.com.hazze.cury.marketplace.exceptions.BusinessException;
import br.com.hazze.cury.marketplace.exceptions.ResourceNotFoundException;
import br.com.hazze.cury.marketplace.mappers.UserMapper;
import br.com.hazze.cury.marketplace.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um usuário com esse e-mail.");
        }

        if (dto.cpf() != null && userRepository.existsByCpf(dto.cpf())) {
            throw new BusinessException("Já existe um usuário com esse CPF.");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO createAdmin(UserAdminRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um usuário com esse e-mail.");
        }

        if (dto.cpf() != null && userRepository.existsByCpf(dto.cpf())) {
            throw new BusinessException("Já existe um usuário com esse CPF.");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if (!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Já existe um usuário com esse e-mail.");
        }

        if (dto.cpf() != null && !dto.cpf().equals(user.getCpf()) && userRepository.existsByCpf(dto.cpf())) {
            throw new BusinessException("Já existe um usuário com esse CPF.");
        }

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setPhone(dto.phone());
        user.setCpf(dto.cpf());

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO updateStatus(Long id, UserStatusUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        user.setActive(dto.active());

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        userRepository.delete(user);
    }
}





