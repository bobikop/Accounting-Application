package com.thegogetters.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice_products")
public class InvoiceProduct extends BaseEntity{

    private Integer quantity;
    private BigDecimal price;
    private Integer tax;
    private BigDecimal profitLoss;
    private Integer remainingQty;


    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


    /*
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


     */


}
