package com.mitocode.controller;

import com.mitocode.dto.ProviderDTO;
import com.mitocode.model.Provider;
import com.mitocode.service.IProviderService;
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
@RequestMapping("/v1/providers")
public class ProviderController {

    private final IProviderService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProviderDTO>> findAll() throws Exception{

        List<ProviderDTO> list = service.findAll().stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> findById(@PathVariable Integer id) throws Exception{
        Provider obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> save(@Valid @RequestBody ProviderDTO dto) throws Exception{
        Provider obj = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> update(@Valid @PathVariable Integer id, @RequestBody ProviderDTO dto)
            throws Exception{

        Provider obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProviderDTO convertToDTO(Provider entity) {
        return modelMapper.map(entity, ProviderDTO.class);
    }

    private Provider convertToEntity(ProviderDTO dto) {
        return modelMapper.map(dto, Provider.class);
    }
}

