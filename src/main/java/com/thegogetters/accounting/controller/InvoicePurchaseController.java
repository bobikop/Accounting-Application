package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/purchaseInvoices")
public class InvoicePurchaseController {


    private final InvoiceService invoiceService;

    private final ClientVendorService clientVendorService;

    private final ProductService productService;


    private final InvoiceProductService invoiceProductService;

    public InvoicePurchaseController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
    }



    //GetMapping - list()----------------------------------------------------------------1
    @GetMapping("/list")
    public String listPurchaseInvoices(Model model){

        List<InvoiceDTO> invoiceDTOList = invoiceService.findAllInvoicesBelongsToCompany(InvoiceType.PURCHASE);

        model.addAttribute("invoices", invoiceDTOList);

        return "/invoice/purchase-invoice-list";
    }




    //GetMapping - create()----------------------------------------------------------------2

    @GetMapping("/create")
    public String createInvoice(Model model){

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoiceDTO(InvoiceType.PURCHASE));

        //List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorType(ClientVendorType.VENDOR);

        List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorTypeBelongsToCompany(ClientVendorType.VENDOR);
        model.addAttribute("vendors",  clientVendorDtoList);

        return "invoice/purchase-invoice-create";
    }



    //PostMapping - create(InvoiceDto invoiceDto)----------------------------------------------------------------3

    @PostMapping("/create")
    public String createInvoice(@Valid @ModelAttribute("newPurchaseInvoice") InvoiceDTO invoiceDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorTypeBelongsToCompany(ClientVendorType.VENDOR);
            model.addAttribute("vendors",  clientVendorDtoList);
            return "invoice/purchase-invoice-create";
        }

        InvoiceDTO invoiceDto = invoiceService.create(InvoiceType.PURCHASE,invoiceDTO); // we take the id from DB after save , create method returns invoice dto with id

        return "redirect:/purchaseInvoices/update/"+ invoiceDto.getId();
    }


    //GetMapping - update(Long invoiceId)----------------------------------------------------------------4

    @GetMapping("/update/{id}")
    public String editInvoice(@PathVariable("id") Long invoiceId,Model model) throws AccountingAppException {

        InvoiceDTO invoiceDTO = invoiceService.findInvoiceById(invoiceId);
        model.addAttribute("invoice", invoiceDTO);


        ClientVendorDto clientVendorDto = clientVendorService.findById(invoiceDTO.getClientVendor().getId());

        model.addAttribute("vendors", clientVendorDto);



        //model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        //InvoiceProductDTO invoiceProductDTO = invoiceProductService.getInvoiceProductByInvoiceId(invoiceDTO.getId());


        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());


        List<ProductDTO> productDTOList = productService.getAllProductsByCompany();


        model.addAttribute("products",productDTOList);


        //List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId(invoiceDTO.getId());

        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId_for_productList(invoiceDTO.getId());

        model.addAttribute("invoiceProducts", invoiceProductDTOList);



        return "/invoice/purchase-invoice-update";

    }

    //PostMapping - update(Long invoiceId, InvoiceDto invoice)----------------------------------------------------------------5

    @PostMapping("/update/{id}")
    public String updateInvoice(@PathVariable("id") Long invoiceId, @ModelAttribute("invoice") InvoiceDTO invoiceDTO){

        //invoiceProductService.update(id,invoiceProductDTO);no needed
        invoiceService.update(invoiceId,invoiceDTO);


        //return "redirect:/purchaseInvoices/update/{id}";
        //return "redirect:/purchaseInvoices/update/" + id;
        return "redirect:/purchaseInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String updateInvoice(@PathVariable("id") Long invoiceId,  @Valid @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO,BindingResult bindingResult,Model model) throws AccountingAppException {


        if(bindingResult.hasErrors()){
            InvoiceDTO invoiceDTO = invoiceService.findInvoiceById(invoiceId);
            model.addAttribute("invoice", invoiceDTO);

            ClientVendorDto clientVendorDto = clientVendorService.findById(invoiceDTO.getClientVendor().getId());
            model.addAttribute("vendors", clientVendorDto);

            List<ProductDTO> productDTOList = productService.getAllProductsByCompany();
            model.addAttribute("products",productDTOList);

            List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId_for_productList(invoiceDTO.getId());
            model.addAttribute("invoiceProducts", invoiceProductDTOList);

            return "/invoice/purchase-invoice-update";
        }

        InvoiceProductDTO savedInvoiceProductDTO = invoiceProductService.save(invoiceId, invoiceProductDTO);

        return "redirect:/purchaseInvoices/update/" + invoiceId;


    }


    //GetMapping - delete(Long invoiceId)----------------------------------------------------------------6

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long invoiceId){

        invoiceService.deleteById(invoiceId);

        return "redirect:/purchaseInvoices/list";


    }


    //GetMapping - approveInvoiceStatus(Long invoiceId)----------------------------------------------------------------7
    @GetMapping("/approve/{id}")
    public String approveInvoiceStatus(@PathVariable("id") Long invoiceId){

        invoiceService.approveInvoice(invoiceId);

        return "redirect:/purchaseInvoices/list";
    }


    //GetMapping - removeInvoiceProduct(Long invoiceId)----------------------------------------------------------------8

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}") //soft delete
    public String removeInvoiceProduct(@PathVariable("invoiceId") Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId) throws AccountingAppException {

       // invoiceService.findInvoiceById(invoiceId);
        invoiceProductService.deleteById(invoiceProductId);



        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }

    //GetMapping - printInvoice----------------------------------------------------------------9

    @GetMapping("/print/{id}")
    public String printInvoice(@PathVariable("id") Long invoiceId,Model model){

        InvoiceDTO invoice = invoiceService.findInvoiceById(invoiceId);

        model.addAttribute("company", invoice.getCompany() );

        model.addAttribute("invoice", invoice);

        model.addAttribute(invoice.getPrice());
        model.addAttribute(invoice.getTax());
        model.addAttribute(invoice.getTotal());

        model.addAttribute(invoice.getClientVendor().getClientVendorName());
        model.addAttribute(invoice.getClientVendor().getAddress().getZipCode());
        model.addAttribute(invoice.getClientVendor().getAddress().getState());
        model.addAttribute(invoice.getClientVendor().getWebsite());


        List<InvoiceProductDTO> invoiceProductDTOS = invoiceProductService.findInvoiceProductByInvoiceId_for_productList(invoiceId);

        model.addAttribute("invoiceProducts", invoiceProductDTOS);


        return "/invoice/invoice_print";
    }





}
