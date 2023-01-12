package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.enums.InvoiceType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDTO {

    private Long id;
    private String invoiceNo;

    private InvoiceStatus invoiceStatus;

    private InvoiceType invoiceType;
    private LocalDate date;

    private CompanyDto company;

    @NotNull
    private ClientVendorDto clientVendor;


    private BigDecimal price;
    private Integer tax;
    private BigDecimal total;


    private List<InvoiceProductDTO> invoiceProducts;

    public InvoiceDTO(String invoiceNo, LocalDate date, ClientVendorDto clientVendor, BigDecimal price, Integer tax, BigDecimal total) {
       this.invoiceNo = invoiceNo;
        this.date = date;
        this.clientVendor = clientVendor;
        this.price = price;
        this.tax = tax;
        this.total = total;
    }
}
