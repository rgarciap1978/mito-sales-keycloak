package com.mitocode.controller;

import com.mitocode.dto.RoleDTO;
import com.mitocode.model.Role;
import com.mitocode.service.IRoleService;
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
@RequestMapping("/v1/roles")
public class RoleController {

    private final IRoleService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() throws Exception{

        List<RoleDTO> list = service.findAll().stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable Integer id) throws Exception{
        Role obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> save(@Valid @RequestBody RoleDTO dto) throws Exception{
        Role obj = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@Valid @PathVariable Integer id, @RequestBody RoleDTO dto)
            throws Exception{

        Role obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private RoleDTO convertToDTO(Role entity) {
        return modelMapper.map(entity, RoleDTO.class);
    }

    private Role convertToEntity(RoleDTO dto) {
        return modelMapper.map(dto, Role.class);
    }
}

