package com.thegogetters.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDTO {

    private Long id;
    private Integer quantity;       // If this is a purchase invoice, you can keep track of how many product you bought
    private BigDecimal price;       // and how much you spend for that... Price is also here..
    private Integer tax;            // and tax
    private BigDecimal total;       // Total Price with tax included
    private BigDecimal profitLoss;  // You need to calculate profit/loss once invoice approved
    private Integer remainingQty;   // Once you approve a purchase invoice, this invoiceProduct remaining_quantity
                                    // will help you to calculate how many item you left from that invoice_product
                                    // Use this value to keep track of your products FIFO logic..
    private InvoiceDTO invoiceDto;
    private ProductDTO product;

}


