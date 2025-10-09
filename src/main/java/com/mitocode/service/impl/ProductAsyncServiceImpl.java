package com.mitocode.service.impl;

import com.mitocode.model.Product;
import com.mitocode.repository.IProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductAsyncServiceImpl {
    private final IProductRepo repo;

    @Async("virtualThreadExecutor2")
    public CompletableFuture<List<Product>> getProducts1()
            throws InterruptedException {

        Thread.sleep(1000);
        List<Product> products = repo.findAll();

        log.info(Thread.currentThread().getName());

        return CompletableFuture.completedFuture(products);
    }

    @Async("virtualThreadExecutor1")
    public CompletableFuture<List<Product>> getProducts2()
            throws InterruptedException {

        Thread.sleep(3000);
        List<Product> products = repo.findAll();

        log.info(Thread.currentThread().getName());

        return CompletableFuture.completedFuture(products);
    }

    @Async("virtualThreadExecutor2")
    public CompletableFuture<List<Product>> getProducts3()
            throws InterruptedException {

        Thread.sleep(2000);
        List<Product> products = repo.findAll();

        log.info(Thread.currentThread().getName());

        return CompletableFuture.completedFuture(products);
    }
}
