package com.mitocode.controller;

import com.mitocode.dto.RoleDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Role;
import com.mitocode.service.IRoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private IRoleService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() {
        List<RoleDTO> list = service.findAll().stream().map(p -> mapper.map(p, RoleDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable("id") Integer id) {
        RoleDTO dtoResponse;
        Role obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, RoleDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody RoleDTO dto) {
        Role p = service.save(mapper.map(dto, Role.class));
        //localhost:8080/roles/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdRole()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Role> update(@Valid @RequestBody RoleDTO dto) {
        Role obj = service.findById(dto.getIdRole());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdRole());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Role.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Role obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<RoleDTO> findByIdHateoas(@PathVariable("id") Integer id){
        RoleDTO dtoResponse;
        Role obj = service.findById(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, RoleDTO.class);
        }

        //localhost:8080/roles/{id}
        EntityModel<RoleDTO> resource = EntityModel.of(dtoResponse);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
        resource.add(link1.withRel("role-info1"));
        resource.add(link2.withRel("role-full"));

        return resource;
    }
    
    @GetMapping("/pageable")
    public ResponseEntity<Page<RoleDTO>> listPage(Pageable pageable){
    	Page<RoleDTO> page = service.listPage(pageable).map(p -> mapper.map(p, RoleDTO.class));
    	
    	return new ResponseEntity<>(page, HttpStatus.OK) ;
    }
}
