package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.VitalSignDTO;
import com.mitocode.dto.VitalSignDTO;
import com.mitocode.dto.VitalSignDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.VitalSign;
import com.mitocode.model.VitalSign;
import com.mitocode.service.IVitalSignService;

@RestController
@RequestMapping("/vitalsigns")
public class VitalSignController {

	@Autowired
	private IVitalSignService service;
	
	@Autowired
	private ModelMapper mapper;
	
    @GetMapping
    public ResponseEntity<List<VitalSignDTO>> findAll() {
        List<VitalSignDTO> list = service.findAll().stream().map(p -> mapper.map(p, VitalSignDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VitalSignDTO> findById(@PathVariable("id") Integer id) {
        VitalSignDTO dtoResponse;
        VitalSign obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, VitalSignDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody VitalSignDTO dto) {
        VitalSign p = service.save(mapper.map(dto, VitalSign.class));
        //localhost:8080/vitalsigns/3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdVitalSign()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<VitalSign> update(@Valid @RequestBody VitalSignDTO dto) {
        VitalSign obj = service.findById(dto.getIdVitalSign());
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getIdVitalSign());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, VitalSign.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        VitalSign obj = service.findById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/hateoas/{id}")
    public EntityModel<VitalSignDTO> findByIdHateoas(@PathVariable("id") Integer id){
        VitalSignDTO dtoResponse;
        VitalSign obj = service.findById(id);

        if(obj == null){
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(obj, VitalSignDTO.class);
        }

        //localhost:8080/vitalsigns/{id}
        EntityModel<VitalSignDTO> resource = EntityModel.of(dtoResponse);
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
        resource.add(link1.withRel("vitalsign-info1"));
        resource.add(link2.withRel("vitalsign-full"));

        return resource;
    }
    
    @GetMapping("/pageable")
    public ResponseEntity<Page<VitalSignDTO>> listPage(Pageable pageable){
    	Page<VitalSignDTO> page = service.listPage(pageable).map(p -> mapper.map(p, VitalSignDTO.class));
    	
    	return new ResponseEntity<>(page, HttpStatus.OK) ;
    }
	
}
