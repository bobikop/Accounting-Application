package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {


    //************************STOCK REPORT++++++++++++++

    @Query(value = "select * from invoice_products ip\n" +
            "join invoices i on i.id = ip.invoice_id\n" +
            "where company_id = ?1",nativeQuery = true)
    List<InvoiceProduct> retrieveAllInvoiceProductsOfCompany(Long companyId);


    //************************STOCK REPORT++++++++++++++


    List<InvoiceProduct> findAll();
    InvoiceProduct findByInvoice_Id(Long id);
    List<InvoiceProduct> findAllByInvoice_Id(Long id);
    List<InvoiceProduct> findAllByProductId(Long Id);
}
