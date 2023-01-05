package com.thegogetters.accounting.service;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.dto.InvoiceProductDTO;
import com.thegogetters.accounting.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceProductService {

    //************************STOCK REPORT++++++++++++++

    List<InvoiceProductDTO> findAllInvoiceProductsOfCompany();



    //************************STOCK REPORT++++++++++++++



    List<InvoiceProduct> FindAllInvoiceProducts();


    //-----------------------------------------------------------------//

    //InvoiceProductDTO findInvoiceProductByInvoiceId(Long id);
    List<InvoiceProductDTO> findInvoiceProductByInvoiceId(Long id); // for purchase invoice list page

    List<InvoiceProductDTO> findInvoiceProductByInvoiceId_for_productList(Long id);

    //InvoiceProductDTO getInvoiceProductByInvoiceId(Long id);

    //InvoiceProductDTO save(Long id);


    //InvoiceProductDTO update(Long invoiceId, InvoiceProductDTO invoiceProductDTO);

    InvoiceProductDTO save(Long invoiceId, InvoiceProductDTO invoiceProductDTO) throws AccountingAppException;

    void deleteByInvoiceId(Long invoiceId);

    void deleteById(Long invoiceProductId) throws AccountingAppException;




    boolean check_productQuantity_if_it_is_enough_to_sell(InvoiceProductDTO salesInvoiceProduct);


    List<InvoiceProductDTO> findInvoiceProductsByProductID(Long Id);//Added by Evgenia. Need for Product delete.
}
