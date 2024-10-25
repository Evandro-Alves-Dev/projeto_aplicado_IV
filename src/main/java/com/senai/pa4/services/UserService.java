package com.senai.pa4.services;

import com.senai.pa4.dto.UserDTO;
import com.senai.pa4.entities.User;
import com.senai.pa4.enums.TypeEnum;
import com.senai.pa4.exceptions.ResourceNotFoundException;
import com.senai.pa4.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
        return new UserDTO(user);
    }

    // INSERIDO NO PA4

//    @Transactional(readOnly = true)
//    public UserDTO findByName(String name) {
//        User user = userRepository.findByName(name)
//                .orElseThrow(() -> new ResourceNotFoundException("Username " + name + " não encontrado"));
//        return new UserDTO(user);
//    }

    // ATÉ AQUI

    @Transactional
    public UserDTO insert(UserDTO userDTO) {
        User user = new User();
        copyDtoToEntity(userDTO, user);
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        try {
            var entity = userRepository.getOne(id);
            copyDtoToEntity(userDTO, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e ) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
        userRepository.deleteById(id);
    }

    private void copyDtoToEntity(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setPosition(userDTO.getPosition());
        user.setRoleType(TypeEnum.parse(userDTO.getRoleType()));
        user.setPassword(userDTO.getPassword());
    }

    public Optional<User> getUserByUsername(String username) {
        Optional<User> users = userRepository.findByUsername(username);
        return users;
    }

}
