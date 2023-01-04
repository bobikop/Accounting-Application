package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.ClientVendor;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.Invoice;
import com.thegogetters.accounting.entity.InvoiceProduct;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {


    //********************************* Dashboard

    List<Invoice> findAllByCompanyAndInvoiceStatusAndInvoiceType(Company company ,InvoiceStatus invoiceStatus, InvoiceType invoiceType);

    //*********************************



    //List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);

    List<Invoice> findAllByInvoiceTypeAndCompany_Id(InvoiceType invoiceType, Long id);

   List<Invoice> findAllByCompanyAndClientVendor_IdAndInvoiceStatus(Company company, Long clientVendorId, InvoiceStatus invoiceStatus);

    List<Invoice> findAllByClientVendor_ClientVendorTypeAndInvoiceTypeAndCompany_Id(ClientVendorType clientVendorType, InvoiceType invoiceType, Long id);

    List<Invoice> findAllByCompanyAndInvoiceType(Company company , InvoiceType invoiceType);

    //*******************************************PROFIT LOSS


    @Query(value = "select sum(quantity) from invoices\n" +
            "join invoice_products ip on invoices.id = ip.invoice_id\n" +
            "where invoice_type = 'PURCHASE' and company_id = ?1 and product_id = ?2",nativeQuery = true)
    Integer retrieveTotalQuantityForPurchaseInvoices(Long companyId, Long productId);

    @Query(value = "select sum( (price * quantity ) + (price * quantity * tax) / 100) from invoices\n" +
            "join invoice_products ip on invoices.id = ip.invoice_id\n" +
            "where invoice_type = 'PURCHASE' and company_id = ?1 and product_id = ?2",nativeQuery = true)
    Integer retrieveTotalPriceForPurchaseInvoices(Long companyId,Long productId);



    //**************************PROFIT/LOSS REPORT*************************//

    @Query(value = "select date from invoices i join invoice_products ip on i.id = ip.invoice_id\n" +
            "where  invoice_type = 'SALES' and company_id = 2\n" +
            "group by date, profit_loss",nativeQuery = true)
    List<LocalDate> retrieveLocalDatesBelongsToSalesInvoicesAndCompany();


    @Query(value = "select profit_loss from invoices i join invoice_products ip on i.id = ip.invoice_id\n" +
            "where  invoice_type = 'SALES' and company_id = 2\n" +
            "group by date, profit_loss",nativeQuery = true)
    List<BigDecimal> retrieveProfitLossBelongsToSalesInvoicesAndCompany();



}
