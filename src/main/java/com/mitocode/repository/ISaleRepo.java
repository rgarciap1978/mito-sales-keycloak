package com.mitocode.repository;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface ISaleRepo extends IGenericRepo<Sale,Integer> {

    @Query(value = "select * from fn_sales()", nativeQuery = true)
    List<Object[]> callFunction1();
    @Query(value = "select * from fn_sales()", nativeQuery = true)
    List<IProcedureDTO> callFunction2();
    @Query(name = "Sale.fn_sales", nativeQuery = true)
    List<ProcedureDTO> callFunction3();

    @Procedure(procedureName = "pr_sales" )
    void callProcedure1();
}
