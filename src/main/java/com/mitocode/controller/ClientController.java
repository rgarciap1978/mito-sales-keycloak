package com.mitocode.controller;

import com.mitocode.dto.ClientDTO;
import com.mitocode.model.Client;
import com.mitocode.service.IClientService;
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
@RequestMapping("/v1/clients")
public class ClientController {

    private final IClientService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() throws Exception{

        List<ClientDTO> list = service.findAll().stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) throws Exception{
        Client obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> save(@Valid @RequestBody ClientDTO dto) throws Exception{
        Client obj = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@Valid @PathVariable Integer id, @RequestBody ClientDTO dto)
            throws Exception{

        Client obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ClientDTO convertToDTO(Client entity) {
        return modelMapper.map(entity, ClientDTO.class);
    }

    private Client convertToEntity(ClientDTO dto) {
        return modelMapper.map(dto, Client.class);
    }
}

