package com.mitocode.service.impl;

import com.mitocode.model.MediaFile;
import com.mitocode.repo.IMediaFileRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IMediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaFileServiceImpl extends CRUDImpl<MediaFile, Integer> implements IMediaFileService {

    @Autowired
    private IMediaFileRepo repo;

    @Override
    protected IGenericRepo<MediaFile, Integer> getRepo() {
        return repo;
    }
}
