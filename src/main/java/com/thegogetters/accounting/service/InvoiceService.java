package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {


    List<InvoiceDTO> findAllInvoicesBelongsToCompany(InvoiceType invoiceType);

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
