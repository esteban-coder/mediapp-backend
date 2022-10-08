package com.mitocode.service;

import com.mitocode.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatientService extends ICRUD<Patient, Integer>{

	Page<Patient> listPage(Pageable page);

}
