package com.mitocode.service;

import com.mitocode.model.Menu;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMenuService extends ICRUD<Menu, Integer>{

    List<Menu> getMenusByUsername();
    List<Menu> getMenusByUsernameFront(String username);
    
    Page<Menu> listPage(Pageable page);

}
