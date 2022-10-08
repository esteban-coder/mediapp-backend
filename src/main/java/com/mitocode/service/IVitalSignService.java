package com.mitocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.VitalSign;

public interface IVitalSignService extends ICRUD<VitalSign, Integer>{

	Page<VitalSign> listPage(Pageable page);
}
