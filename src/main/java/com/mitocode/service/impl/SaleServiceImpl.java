package com.mitocode.service.impl;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;
import com.mitocode.model.SaleDetail;
import com.mitocode.repository.ISaleRepo;
import com.mitocode.repository.IGenericRepo;
import com.mitocode.service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl
        extends CRUDImpl<Sale, Integer>
        implements ISaleService {

    private final ISaleRepo repo;

    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

    @Override
    public Sale getSaleMostExpensive() {
        return repo.findAll()
                .stream()
                .max(Comparator.comparing(Sale::getTotal))
                .orElse(new Sale());
    }

    @Override
    public String getBestSellerUsername() {
        Map<String, Double> byUser = repo.findAll()
                .stream()
                .collect(groupingBy(s -> s.getUser().getUsername(),
                        summingDouble(Sale::getTotal)));

        return Collections.max(byUser.entrySet(), Comparator.comparing(Map.Entry::getValue)).getKey();
    }

    @Override
    public Map<String, Long> getSalesCountBySeller() {
        return repo.findAll().stream()
                .collect(groupingBy(s -> s.getUser().getUsername(),
                        counting()));
    }

    @Override
    public Map<String, Double> getMostSoldProduct() {
        Stream<Sale> saleStream = repo.findAll().stream();
        Stream<List<SaleDetail>> lstStream = saleStream.map(Sale::getDetails);
        Stream<SaleDetail> streamDetail = lstStream.flatMap(Collection::stream);
        Map<String, Double> byProduct = streamDetail
                .collect(groupingBy(d -> d.getProduct().getName(),
                        summingDouble(SaleDetail::getQuantity)));

        return byProduct.entrySet()
                .stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));
    }

    @Override
    public List<ProcedureDTO> callFunction1() {
        List<ProcedureDTO> list = new ArrayList<>();
        repo.callFunction1()
                .forEach(e-> {
                    ProcedureDTO dto = new ProcedureDTO();
                    dto.setQuantityfn(Integer.valueOf(String.valueOf(e[0])));
                    dto.setDatetimefn(String.valueOf(e[1]));
                    list.add(dto);
                });

        return list;
    }

    @Override
    public List<IProcedureDTO> callFunction2() {
        return repo.callFunction2();
    }

    @Override
    public List<ProcedureDTO> callFunction3() {
        return repo.callFunction3();
    }

    @Override
    public void callProcedure1() {
        repo.callProcedure1();
    }
}
