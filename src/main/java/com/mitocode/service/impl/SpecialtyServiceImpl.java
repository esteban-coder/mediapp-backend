package com.mitocode.service.impl;

import com.mitocode.model.Specialty;
import com.mitocode.repo.ISpecialtyRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.ISpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceImpl extends CRUDImpl<Specialty, Integer> implements ISpecialtyService {

    @Autowired
    private ISpecialtyRepo repo;

    @Override
    protected IGenericRepo<Specialty, Integer> getRepo() {
        return repo;
    }
}
