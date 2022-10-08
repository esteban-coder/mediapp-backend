package com.mitocode.service;

import com.mitocode.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService extends ICRUD<Role, Integer>{

	Page<Role> listPage(Pageable page);

}
