package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.dto.InvoiceProductDTO;
import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.service.ClientVendorService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.InvoiceService;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //GetMapping - list()

    @GetMapping("/list")
    public String listInvoices(Model model){

        List<InvoiceDTO> invoiceDTOList = invoiceService.findAllPurchaseInvoices();

        model.addAttribute("invoices", invoiceDTOList);

        return "/invoice/purchase-invoice-list";
    }




    //GetMapping - create()

    @GetMapping("/create")
    public String createInvoice(Model model){

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewPurchaseInvoiceDTO());

        //List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorType(ClientVendorType.VENDOR);

        List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorTypeBelongsToCompany(ClientVendorType.VENDOR);
        model.addAttribute("vendors",  clientVendorDtoList);

        return "invoice/purchase-invoice-create";
    }



    //PostMapping - create(InvoiceDto invoiceDto)

    @PostMapping("/create")
    public String createInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDTO invoiceDTO){

        InvoiceDTO invoiceDto = invoiceService.create(invoiceDTO); // we take the id from DB after save , create method returns invoice dto with id

        //invoiceProductService.save(invoiceDto.getId());

        //return "/invoice/purchase-invoice-update" + invoiceDTO.getId();
        //return "redirect:/purchaseInvoice/update/{id}(id=${invoiceDTO.getId()})" + invoiceDTO.getId();
        return "redirect:/purchaseInvoices/update/"+ invoiceDto.getId();
    }


    //GetMapping - update(Long invoiceId)
    @GetMapping("/update/{id}")
    public String editInvoice(@PathVariable("id") Long invoiceId,Model model){

        InvoiceDTO invoiceDTO = invoiceService.findInvoiceById(invoiceId);
        model.addAttribute("invoice", invoiceDTO);


        ClientVendorDto clientVendorDto = clientVendorService.findById(invoiceDTO.getClientVendor().getId());

        model.addAttribute("vendors", clientVendorDto);



        //model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        //InvoiceProductDTO invoiceProductDTO = invoiceProductService.getInvoiceProductByInvoiceId(invoiceDTO.getId());


        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());


        List<ProductDTO> productDTOList = productService.listAllProducts();


        model.addAttribute("products",productDTOList);


        //List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId(invoiceDTO.getId());

        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId_for_productList(invoiceDTO.getId());

        model.addAttribute("invoiceProducts", invoiceProductDTOList);



        return "/invoice/purchase-invoice-update";

    }

    //PostMapping - update(Long invoiceId, InvoiceDto invoice)

    @PostMapping("/update/{id}")
    public String updateInvoice(@PathVariable("id") Long invoiceId, @ModelAttribute("invoice") InvoiceDTO invoiceDTO){

        //invoiceProductService.update(id,invoiceProductDTO);no needed
        invoiceService.update(invoiceId,invoiceDTO);


        //return "redirect:/purchaseInvoices/update/{id}";
        //return "redirect:/purchaseInvoices/update/" + id;
        return "redirect:/purchaseInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String updateInvoice(@PathVariable("id") Long invoiceId, @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO){



        //save
        //invoiceProductService.update(invoiceId, invoiceProductDTO);


        InvoiceProductDTO savedInvoiceProductDTO = invoiceProductService.save(invoiceId, invoiceProductDTO);






        return "redirect:/purchaseInvoices/update/" + invoiceId;


    }



    //GetMapping - delete(Long invoiceId)

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long invoiceId){

        invoiceService.deleteById(invoiceId);

        return "redirect:/purchaseInvoices/list";


    }


    @GetMapping("/approve/{id}")
    public String approveInvoiceStatus(@PathVariable("id") Long invoiceId){

        invoiceService.approveInvoice(invoiceId);

        return "redirect:/purchaseInvoices/list";
    }


    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}") //soft delete
    public String removeInvoiceProduct(@PathVariable("invoiceId") Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId){

       // invoiceService.findInvoiceById(invoiceId);
        invoiceProductService.deleteById(invoiceProductId);



        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }



}
