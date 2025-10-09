package com.mitocode.controller;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.dto.SaleDTO;
import com.mitocode.model.Sale;
import com.mitocode.service.ISaleService;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sales")
public class SaleController {

    private final ISaleService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> findAll() throws Exception{

        List<SaleDTO> list = service.findAll().stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable Integer id) throws Exception{
        Sale obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<SaleDTO> save(@Valid @RequestBody SaleDTO dto) throws Exception{
        Sale obj = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@Valid @PathVariable Integer id, @RequestBody SaleDTO dto)
            throws Exception{

        Sale obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mostexpensive")
    public ResponseEntity<SaleDTO> findMostExpensive() throws Exception{
        Sale obj = service.getSaleMostExpensive();
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @GetMapping("/bestseller")
    public ResponseEntity<String> getBestSeller() throws Exception{
        String username = service.getBestSellerUsername();
        return ResponseEntity.ok(username);
    }

    @GetMapping("/sellercount")
    public ResponseEntity<Map<String, Long>> getSalesCountBySeller() throws Exception{
        return ResponseEntity.ok(service.getSalesCountBySeller());
    }

    @GetMapping("/bestproduct")
    public ResponseEntity<Map<String, Double>> getBestProduct() throws Exception{
        Map<String, Double> byProduct = service.getMostSoldProduct();

        return ResponseEntity.ok(byProduct);
    }

    @GetMapping("/resume1")
    public ResponseEntity<List<ProcedureDTO>> getResume1() throws Exception{
        return ResponseEntity.ok(service.callFunction1());
    }

    @GetMapping("/resume2")
    public ResponseEntity<List<IProcedureDTO>> getResume2() throws Exception{
        return ResponseEntity.ok(service.callFunction2());
    }

    @GetMapping("/resume3")
    public ResponseEntity<List<ProcedureDTO>> getResume3() throws Exception{
        return ResponseEntity.ok(service.callFunction3());
    }

    @GetMapping("/resume4")
    public ResponseEntity<Void> getResume4() throws Exception{
        service.callProcedure1();
        return ResponseEntity.ok().build();
    }

    private SaleDTO convertToDTO(Sale entity) {
        return modelMapper.map(entity, SaleDTO.class);
    }

    private Sale convertToEntity(SaleDTO dto) {
        return modelMapper.map(dto, Sale.class);
    }
}

