package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {

    List<InvoiceProduct> findAll();
    InvoiceProduct findByInvoice_Id(Long id);
    List<InvoiceProduct> findAllByInvoice_Id(Long id);
    List<InvoiceProduct> findAllByProductId(Long Id);
}
