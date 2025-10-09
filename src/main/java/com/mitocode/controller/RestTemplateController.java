package com.mitocode.controller;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.security.JwtRequest;
import com.mitocode.security.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RestTemplateController {

    private final RestTemplate restTemplate;

    @GetMapping("/pokemon/{name}")
    public ResponseEntity<String> getPokemon(@PathVariable String name) {
        String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + name;
        String response = restTemplate.getForEntity(pokemonUrl, String.class).getBody();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test1")
    public ResponseEntity<List<CategoryDTO>> test1() {
        String url = "http://localhost:8080/v1/categories";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ParameterizedTypeReference<List<CategoryDTO>> typeRef = new ParameterizedTypeReference<>(){};

        return restTemplate.exchange(url, HttpMethod.GET, entity, typeRef);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2(
            @RequestParam(name = "page") int page, @RequestParam(name = "size") int size
    ) {

        String url = "http://localhost:8080/v1/categories/pagination?p=" + page + "&s=" + size;
        String response = restTemplate.getForEntity(url, String.class).getBody();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test3")
    public ResponseEntity<Map> test3(
            @RequestParam(name = "page") int page, @RequestParam(name = "size") int size
    ) {

        String url = "http://localhost:8080/v1/categories/pagination?p={page}&s={size}";

        Map<String, Integer> uriVariable = new HashMap<>();
        uriVariable.put("page", page);
        uriVariable.put("size", size);

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, entity, Map.class, uriVariable)    ;
    }

    @PostMapping("/test4")
    public ResponseEntity<CategoryDTO> test4(@RequestBody CategoryDTO dto){
        String url = "http://localhost:8080/v1/categories";

        HttpEntity<CategoryDTO> entity = new HttpEntity<>(dto);
        CategoryDTO response = restTemplate.postForObject(url, entity, CategoryDTO.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/test5/{id}")
    public ResponseEntity<CategoryDTO> test5(@PathVariable Integer id, @RequestBody CategoryDTO dto){
        String url = "http://localhost:8080/v1/categories/" + id;

        HttpEntity<CategoryDTO> entity = new HttpEntity<>(dto);

        return restTemplate.exchange(url, HttpMethod.PUT, entity, CategoryDTO.class);
    }

    @DeleteMapping("/test6/{id}")
    public ResponseEntity<Void> test6(@PathVariable Integer id) {
        String url = "http://localhost:8080/v1/categories/"+ id;

        restTemplate.delete(url);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test7/{id}")
    public ResponseEntity<Void> test7(@PathVariable Integer id) {
        String url = "http://localhost:8080/v1/categories/{idCategory}";
        Map<String, Integer> uriVariable = new HashMap<>();
        uriVariable.put("idCategory", id);

        restTemplate.delete(url, uriVariable);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test8/{id}")
    public ResponseEntity<Void> test8(@PathVariable Integer id, @RequestBody JwtRequest request) {
        String token = generateToken(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8080/v1/categories/{idCategory}";
        Map<String, Integer> uriVariable = new HashMap<>();
        uriVariable.put("idCategory", id);

        restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class, uriVariable);

        return ResponseEntity.noContent().build();
    }

    private String generateToken(JwtRequest request) {
        final String AUTHENTICATION_URL = "http://localhost:8080/login";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JwtRequest> authEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<JwtResponse> response = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authEntity, JwtResponse.class);

        if(response.getBody() != null){
            return response.getBody().accessToken();
        }

        return "INVALID_TOKEN";
    }
}
