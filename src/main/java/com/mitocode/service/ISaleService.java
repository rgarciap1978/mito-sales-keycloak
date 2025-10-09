package com.mitocode.service;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;

import java.util.List;
import java.util.Map;

public interface ISaleService extends ICRUD<Sale, Integer> {

    Sale getSaleMostExpensive();
    String getBestSellerUsername();
    Map<String, Long> getSalesCountBySeller();
    Map<String, Double> getMostSoldProduct();


    List<ProcedureDTO> callFunction1();
    List<IProcedureDTO> callFunction2();
    List<ProcedureDTO> callFunction3();
    void callProcedure1();

}