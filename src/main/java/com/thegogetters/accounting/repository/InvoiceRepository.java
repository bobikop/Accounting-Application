package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.Invoice;
import com.thegogetters.accounting.entity.InvoiceProduct;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {



    //List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);

    List<Invoice> findAllByInvoiceTypeAndCompany_Id(InvoiceType invoiceType, Long id);


    List<Invoice> findAllByCompany_Id(Long companyId);

    List<Invoice> findAllByClientVendor_ClientVendorTypeAndInvoiceTypeAndCompany_Id(ClientVendorType clientVendorType, InvoiceType invoiceType, Long id);

    List<Invoice> findAllByCompanyAndInvoiceType(Company company , InvoiceType invoiceType);
}
