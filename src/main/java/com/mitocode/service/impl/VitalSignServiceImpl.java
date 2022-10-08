package com.mitocode.service.impl;

import com.mitocode.model.VitalSign;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IVitalSignRepo;
import com.mitocode.service.IVitalSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VitalSignServiceImpl extends CRUDImpl<VitalSign, Integer> implements IVitalSignService {

    @Autowired
    private IVitalSignRepo repo;

    @Override
    protected IGenericRepo<VitalSign, Integer> getRepo() {
        return repo;
    }

	@Override
	public Page<VitalSign> listPage(Pageable page) {
		return repo.findAll(page);
	}
}
