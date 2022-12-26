package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate date;
    private CompanyDto company;
    private ClientVendorDto clientVendor;


    private BigDecimal price;
    private Integer tax;
    private BigDecimal total;


    private List<InvoiceProduct> invoiceProducts;

}
