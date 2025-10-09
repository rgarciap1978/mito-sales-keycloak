package com.mitocode.service.impl;

import com.mitocode.model.Client;
import com.mitocode.repository.IClientRepo;
import com.mitocode.repository.IGenericRepo;
import com.mitocode.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl
        extends CRUDImpl<Client, Integer>
        implements IClientService {

    private final IClientRepo repo;


    @Override
    protected IGenericRepo<Client, Integer> getRepo() {
        return repo;
    }
}
