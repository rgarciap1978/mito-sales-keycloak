package com.mitocode.controller;

import com.mitocode.dto.UserDTO;
import com.mitocode.model.User;
import com.mitocode.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final IUserService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() throws Exception{

        List<UserDTO> list = service.findAll().stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) throws Exception{
        User obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO dto) throws Exception{
        User obj = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable Integer id, @RequestBody UserDTO dto)
            throws Exception{

        User obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private UserDTO convertToDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    private User convertToEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}

