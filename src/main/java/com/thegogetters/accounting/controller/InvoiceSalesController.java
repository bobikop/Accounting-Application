package com.thegogetters.accounting.controller;


import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.dto.InvoiceProductDTO;
import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.enums.InvoiceType;
import com.thegogetters.accounting.service.ClientVendorService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.InvoiceService;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salesInvoices")
public class InvoiceSalesController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;

    private final ProductService productService;


    private final InvoiceProductService invoiceProductService;

    public InvoiceSalesController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
    }


    //GetMapping - list()----------------------------------------------------------------1

    @GetMapping("/list")
    public String listSalesInvoices(Model model) {

        List<InvoiceDTO> invoiceDTOList = invoiceService.findAllInvoicesBelongsToCompany(InvoiceType.SALES);

        model.addAttribute("invoices", invoiceDTOList);

        return "/invoice/sales-invoice-list";
    }




    //GetMapping - create()----------------------------------------------------------------2

    @GetMapping("/create")
    public String createInvoice(Model model) {

        model.addAttribute("newSalesInvoice", invoiceService.getNewSalesInvoiceDTO());

        //List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorType(ClientVendorType.VENDOR);
        List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorTypeBelongsToCompany(ClientVendorType.CLIENT);

        model.addAttribute("clients", clientVendorDtoList);


        return "invoice/sales-invoice-create";

    }



    //PostMapping - create(InvoiceDto invoiceDto)----------------------------------------------------------------3

    @PostMapping("/create")
    public String createInvoice(@ModelAttribute("newSalesInvoice") InvoiceDTO invoiceDTO){

        InvoiceDTO invoiceDto = invoiceService.create(invoiceDTO); // we take the id from DB after save , create method returns invoice dto with id

        //invoiceProductService.save(invoiceDto.getId());

        //return "/invoice/purchase-invoice-update" + invoiceDTO.getId();
        //return "redirect:/purchaseInvoice/update/{id}(id=${invoiceDTO.getId()})" + invoiceDTO.getId();
        return "redirect:/salesInvoices/update/"+ invoiceDto.getId();
    }


    //GetMapping - update(Long invoiceId)----------------------------------------------------------------4
    @GetMapping("/update/{id}")
    public String editInvoice(@PathVariable("id") Long invoiceId, Model model){

        InvoiceDTO invoiceDTO = invoiceService.findInvoiceById(invoiceId);
        model.addAttribute("invoice", invoiceDTO);


        ClientVendorDto clientVendorDto = clientVendorService.findById(invoiceDTO.getClientVendor().getId());

        model.addAttribute("clients", clientVendorDto);



        //model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        //InvoiceProductDTO invoiceProductDTO = invoiceProductService.getInvoiceProductByInvoiceId(invoiceDTO.getId());


        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());


        List<ProductDTO> productDTOList = productService.listAllProducts();


        model.addAttribute("products",productDTOList);


        //List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId(invoiceDTO.getId());

        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId_for_productList(invoiceDTO.getId());

        model.addAttribute("invoiceProducts", invoiceProductDTOList);



        return "/invoice/sales-invoice-update";

    }

    //PostMapping - update(Long invoiceId, InvoiceDto invoice)----------------------------------------------------------------5

    @PostMapping("/update/{id}")
    public String updateInvoice(@PathVariable("id") Long invoiceId, @ModelAttribute("invoice") InvoiceDTO invoiceDTO){

        invoiceService.update(invoiceId,invoiceDTO);


        //return "redirect:/purchaseInvoices/update/{id}";
        //return "redirect:/purchaseInvoices/update/" + id;
        return "redirect:/salesInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String updateInvoice(@PathVariable("id") Long invoiceId, @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO){

        //save
        //invoiceProductService.update(invoiceId, invoiceProductDTO);

        InvoiceProductDTO savedInvoiceProductDTO = invoiceProductService.save(invoiceId, invoiceProductDTO);


        return "redirect:/salesInvoices/update/" + invoiceId;


    }



    //GetMapping - delete(Long invoiceId)----------------------------------------------------------------6

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long invoiceId){

        invoiceService.deleteById(invoiceId);

        return "redirect:/salesInvoices/list";


    }

    //GetMapping - approveInvoiceStatus(Long invoiceId)----------------------------------------------------------------7


    @GetMapping("/approve/{id}")
    public String approveInvoiceStatus(@PathVariable("id") Long invoiceId){

        invoiceService.approveInvoice(invoiceId);

        return "redirect:/salesInvoices/list";
    }


    //GetMapping - removeInvoiceProduct(Long invoiceId)----------------------------------------------------------------8

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}") //soft delete
    public String removeInvoiceProduct(@PathVariable("invoiceId") Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId){

        // invoiceService.findInvoiceById(invoiceId);
        invoiceProductService.deleteById(invoiceProductId);



        return "redirect:/salesInvoices/update/" + invoiceId;
    }



}

