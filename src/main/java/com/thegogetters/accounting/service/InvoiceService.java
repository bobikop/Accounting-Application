package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.enums.InvoiceType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InvoiceService {




    //Dashboard
    List<InvoiceDTO> findAllApprovedInvoicesBelongsToCompany(InvoiceStatus invoiceStatus, InvoiceType invoiceType);

    Map<String, Double> calculateCostSummary();

    List<InvoiceDTO> lastThreeTransactions();

    //-----------------------------------------------------------------//

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

    List<InvoiceDTO> findAllByClientVendorId(Long id);

}
