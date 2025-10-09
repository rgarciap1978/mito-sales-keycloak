package com.mitocode.controller;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.model.Category;
import com.mitocode.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {

    private final ICategoryService service;

    @Qualifier("categoryMapper")
    private final ModelMapper modelMapper;

    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PreAuthorize("@authService.hasAccess()")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() throws Exception{

        List<CategoryDTO> list = service.findAll().stream()
                //.map(e -> new CategoryDTO(e.getIdCategory(), e.getName(), e.getDescription(), e.isEnabled())).toList();
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Integer id) throws Exception{
        Category obj = service.findById(id);
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto) throws Exception{
        Category obj = service.save(convertToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @PathVariable Integer id, @RequestBody CategoryDTO dto)
            throws Exception{

        Category obj = service.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Queries derivados
    @GetMapping("/find/name/{name}")
    public ResponseEntity<List<CategoryDTO>> findByName(@PathVariable String name) throws Exception{
        List<CategoryDTO> list = service.findByName(name).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/find/name/like/{name}")
    public ResponseEntity<List<CategoryDTO>> findByNameLike(@PathVariable String name) throws Exception{
        List<CategoryDTO> list = service.findByNameLikeIgnoreCase(name).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/find/name/contains/{name}")
    public ResponseEntity<List<CategoryDTO>> findByNameContains(@PathVariable String name) throws Exception{
        List<CategoryDTO> list = service.findByNameContains(name).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/find/name/starts/{name}")
    public ResponseEntity<List<CategoryDTO>> findByNameStartsWith(@PathVariable String name) throws Exception{
        List<CategoryDTO> list = service.findByNameStartsWith(name).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/find/name/ends/{name}")
    public ResponseEntity<List<CategoryDTO>> findByNameEndsWith(@PathVariable String name) throws Exception{
        List<CategoryDTO> list = service.findByNameEndsWith(name).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/find/name/and/enabled")
    public ResponseEntity<List<CategoryDTO>> findByNameAndEnabled(@RequestParam("name") String name,
                                                            @RequestParam("enabled") boolean enabled) throws Exception{
        List<CategoryDTO> list = service.findByNameAndEnabled(name, enabled).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/find/name/or/enabled")
    public ResponseEntity<List<CategoryDTO>> findByNameOrEnabled(@RequestParam("name") String name,
                                                            @RequestParam("enabled") boolean enabled) throws Exception{
        List<CategoryDTO> list = service.findByNameOrEnabled(name, enabled).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

//    @GetMapping("/get/name/desc")
//    public ResponseEntity<List<CategoryDTO>> findByNameLike(@RequestParam("name") String name,
//                                                            @RequestParam("desc") String desc) throws Exception{
//        List<CategoryDTO> list = service.findNameDescription(name, desc).stream()
//                .map(this::convertToDTO).toList();
//        return ResponseEntity.ok(list);
//    }

    @GetMapping("/get/name/desc1")
    public ResponseEntity<List<CategoryDTO>> getNameDescription1(@RequestParam("name") String name,
                                                            @RequestParam("desc") String desc) throws Exception{
        List<CategoryDTO> list = service.getNameDescription1(name, desc).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/get/name/desc2")
    public ResponseEntity<List<CategoryDTO>> getNameDescription2(@RequestParam("name") String name,
                                                                 @RequestParam("desc") String desc) throws Exception{

        List<CategoryDTO> list = service.getNameDescription2(name, desc).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<CategoryDTO>> findPage(Pageable pageable) throws Exception{
        Page<CategoryDTO> page = service.findPage(pageable).map(this::convertToDTO);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/paginationcustomized")
    public ResponseEntity<Page<CategoryDTO>> findPage(
            @RequestParam(name = "p") int page,
            @RequestParam(name = "s") int size
    ) throws Exception{
        Page<CategoryDTO> pageResult = service.findPage(PageRequest.of(page, size)).map(this::convertToDTO);
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<CategoryDTO>> findSort(@RequestParam String param) throws Exception {
        List<CategoryDTO> list = service.findAllOrder(param).stream()
                .map(this::convertToDTO).toList();

        return ResponseEntity.ok(list);
    }

    private CategoryDTO convertToDTO(Category entity) {
        return modelMapper.map(entity, CategoryDTO.class);
    }

    private Category convertToEntity(CategoryDTO dto) {
        return modelMapper.map(dto, Category.class);
    }
}

