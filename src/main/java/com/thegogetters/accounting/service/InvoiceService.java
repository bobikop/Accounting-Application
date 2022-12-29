package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {

    List<InvoiceDTO> findAllPurchaseInvoices();
    List<InvoiceDTO> findAllSalesInvoices();

    //-----------------------------------------------------------------//

    InvoiceDTO getNewPurchaseInvoiceDTO();
    InvoiceDTO getNewSalesInvoiceDTO();

    //-----------------------------------------------------------------//


    InvoiceDTO create(InvoiceDTO invoiceDTO);
    InvoiceDTO update(Long id, InvoiceDTO invoiceDTO);
    void deleteById(Long id);


    InvoiceDTO findInvoiceById(Long id);

    InvoiceDTO approveInvoice(Long invoiceId);
}
