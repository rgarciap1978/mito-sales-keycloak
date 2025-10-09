package com.mitocode.service.impl;

import com.mitocode.model.Ingress;
import com.mitocode.repository.IIngressRepo;
import com.mitocode.repository.IGenericRepo;
import com.mitocode.service.IIngressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngressServiceImpl
        extends CRUDImpl<Ingress, Integer>
        implements IIngressService {

    private final IIngressRepo repo;


    @Override
    protected IGenericRepo<Ingress, Integer> getRepo() {
        return repo;
    }
}
