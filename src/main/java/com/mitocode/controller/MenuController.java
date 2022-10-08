package com.mitocode.controller;

import com.mitocode.dto.MenuDTO;
import com.mitocode.dto.MenuDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Menu;
import com.mitocode.model.Menu;
import com.mitocode.service.IMenuService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IMenuService service;

    /*
    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus() throws Exception{
        List<Menu> menus = service.findAll();
        List<MenuDTO> menusDTO = modelMapper.map(menus, new TypeToken<List<MenuDTO>>() {}.getType());
        return new ResponseEntity<>(menusDTO, HttpStatus.OK);
    }
    */

    @GetMapping("/user")
    public ResponseEntity<List<MenuDTO>> getMenusByUser() throws Exception{
        List<Menu> menus = service.getMenusByUsername();
        List<MenuDTO> menusDTO = mapper.map(menus, new TypeToken<List<MenuDTO>>() {}.getType());
        return new ResponseEntity<>(menusDTO, HttpStatus.OK);
    }
    
    @PostMapping("/user_front")
    public ResponseEntity<List<MenuDTO>> getMenusByUserFront(@RequestBody String username) throws Exception{
        List<Menu> menus = service.getMenusByUsernameFront(username);
        List<MenuDTO> menusDTO = mapper.map(menus, new TypeToken<List<MenuDTO>>() {}.getType());
        return new ResponseEntity<>(menusDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> findAll() {
        List<MenuDTO> list = service.findAll().stream().map(p -> mapper.map(p, MenuDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> findById(@PathVariable("id") Integer id) {
        MenuDTO dtoResponse;
        Menu obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, MenuDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MenuDTO dto) {
        Menu p = service.save(mapper.map(dto, Menu.class));
        //localhost:8080/menus/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdMenu()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Menu> update(@Valid @RequestBody MenuDTO dto) {
        Menu obj = service.findById(dto.getIdMenu());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdMenu());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Menu.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Menu obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MenuDTO> findByIdHateoas(@PathVariable("id") Integer id){
        MenuDTO dtoResponse;
        Menu obj = service.findById(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, MenuDTO.class);
        }

        //localhost:8080/menus/{id}
        EntityModel<MenuDTO> resource = EntityModel.of(dtoResponse);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
        resource.add(link1.withRel("menu-info1"));
        resource.add(link2.withRel("menu-full"));

        return resource;
    }
    
    @GetMapping("/pageable")
    public ResponseEntity<Page<MenuDTO>> listPage(Pageable pageable){
    	Page<MenuDTO> page = service.listPage(pageable).map(p -> mapper.map(p, MenuDTO.class));
    	
    	return new ResponseEntity<>(page, HttpStatus.OK) ;
    }

}
