package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {


    List<InvoiceDTO> findAllInvoicesBelongsToCompany(InvoiceType invoiceType);

    //-----------------------------------------------------------------//

    InvoiceDTO getNewInvoiceDTO(InvoiceType invoiceType);

    //-----------------------------------------------------------------//

    InvoiceDTO create(InvoiceType invoiceType, InvoiceDTO invoiceDTO);

    //-----------------------------------------------------------------//
    InvoiceDTO update(Long id, InvoiceDTO invoiceDTO);

    //-----------------------------------------------------------------//
    void deleteById(Long id);

    //-----------------------------------------------------------------//

    InvoiceDTO findInvoiceById(Long id);

    //-----------------------------------------------------------------//
    InvoiceDTO approveInvoice(Long invoiceId);
}
