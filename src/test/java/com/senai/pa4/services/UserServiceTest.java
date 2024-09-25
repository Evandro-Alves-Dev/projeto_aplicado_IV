package com.senai.pa4.services;

import com.senai.pa4.dto.UserDTO;
import com.senai.pa4.entities.User;
import com.senai.pa4.exceptions.ResourceNotFoundException;
import com.senai.pa4.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.clearAllCaches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        clearAllCaches();
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    MocksTest mocksTest = new MocksTest();
    List<User> userList = mocksTest.mockUsers();
    List<User> userListEmpty = mocksTest.mockUsersEmpty();

    @Test
    void deveRetornarUmaListaDeUsuarios() {

        List<User> usuariosEsperados = userList;

        when(userRepository.findAll()).thenReturn(usuariosEsperados);

        List<UserDTO> usuariosRetornados = userService.findAll();

        assertNotNull(usuariosRetornados);
        assertEquals(usuariosEsperados.size(), usuariosRetornados.size());

        for (int i = 0; i < usuariosEsperados.size(); i++) {
            assertEquals(usuariosEsperados.get(i).getId(), usuariosRetornados.get(i).getId());
            assertEquals(usuariosEsperados.get(i).getUsername(), usuariosRetornados.get(i).getUsername());
            assertEquals(usuariosEsperados.get(i).getPosition(), usuariosRetornados.get(i).getPosition());
            assertEquals(usuariosEsperados.get(i).getRoleType(), usuariosRetornados.get(i).getRoleType());
        }
    }

    @Test
    void deveRetornarUmaListaVaziaDeUsuarios() {
        List<User> usuariosEsperados = userListEmpty;

        when(userRepository.findAll()).thenReturn(usuariosEsperados);

        List<UserDTO> usuariosRetornados = userService.findAll();

        assertTrue(usuariosRetornados.isEmpty());
    }

    @Test
    void deveRetornarUmUsuarioPorId() {
        User usuarioEsperado = userList.get(0);
        Long id = usuarioEsperado.getId();

        when(userRepository.findById(id)).thenReturn(of(usuarioEsperado));

        UserDTO usuarioRetornado = userService.findById(id);

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioEsperado.getId(), usuarioRetornado.getId());
        assertEquals(usuarioEsperado.getUsername(), usuarioRetornado.getUsername());
        assertEquals(usuarioEsperado.getPosition(), usuarioRetornado.getPosition());
        assertEquals(usuarioEsperado.getRoleType(), usuarioRetornado.getRoleType());
    }

    @Test
    void deveRetornarResourceNotFoundExceptionQuandoNaoEncontrarUsuarioPorId() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(id);
        });
    }


}