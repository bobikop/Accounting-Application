package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.InvoiceProductDTO;
import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.InvoiceProduct;
import com.thegogetters.accounting.entity.Product;
import com.thegogetters.accounting.enums.InvoiceType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ProductRepository;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final MapperUtil mapperUtil;
    private final ProductRepository productRepository;
    private final CompanyService companyService;
    private final InvoiceProductService invoiceProductService;

    public ProductServiceImpl(MapperUtil mapperUtil, ProductRepository productRepository, CompanyService companyService,
                              InvoiceProductService invoiceProductService) {
        this.mapperUtil = mapperUtil;
        this.productRepository = productRepository;
        this.companyService = companyService;
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll(Sort.by("category_id", "name").ascending())
                .stream()
                .map(product -> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) throws AccountingAppException {
        Product product = productRepository.findProductById(id).orElseThrow(() -> new AccountingAppException("Product not found"));
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = mapperUtil.convert(productDTO, new Product());
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        product.setCompany(company);
        productRepository.save(product);
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public void update(ProductDTO productDTO) throws AccountingAppException {
        Product product = productRepository.findProductById(productDTO.getId()).orElseThrow(() -> new AccountingAppException("Product not found"));
        ProductDTO convertedProduct = mapperUtil.convert(product, new ProductDTO());
        convertedProduct.setId(product.getId());
        convertedProduct.setCategory(productDTO.getCategory());
        convertedProduct.setName(productDTO.getName());
        convertedProduct.setProductUnit(productDTO.getProductUnit());
        convertedProduct.setLowLimitAlert(productDTO.getLowLimitAlert());
        Integer quantityInStock = productDTO.getQuantityInStock() == null ? product.getQuantityInStock() :  productDTO.getQuantityInStock();
        convertedProduct.setQuantityInStock(quantityInStock);
        CompanyDto company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new CompanyDto());
        convertedProduct.setCompany(company);
        productRepository.save(mapperUtil.convert(convertedProduct, new Product()));
    }

    @Override
    public void deleteById(Long id) throws AccountingAppException {
        Product product = productRepository.findProductById(id).orElseThrow(() -> new AccountingAppException("Product not found"));

        //check if quantity of product is more than 0, user cannot delete that product.
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public boolean checkAnyProductExist(Long id) {
        List<Product> products = productRepository.findAllByCategoryId(id);
        return products.size() > 0;
    }

    @Override
    public boolean isInStockEnough(InvoiceProductDTO invoiceProductDTO) {
        int remainingStock = productRepository.findProductByName(invoiceProductDTO.getProduct().getName()).getQuantityInStock();
        return remainingStock > invoiceProductDTO.getQuantity();
    }

    @Override
    public List<ProductDTO> getAllProductsByCompany() {
     Sort sort =  Sort.by("company_id","category_id", "name").ascending();
        List<Product> products = productRepository.findAllByCompanyId(companyService.getCompanyOfLoggedInUser().getId(), sort);
        return products
                .stream()
                .map(product -> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkAnyInvoiceExist(Long id) {
        return invoiceProductService.findInvoiceProductsByProductID(id).size() > 0;
    }

    @Override
    public boolean isNameExist(String name, Long id) {
        Product product=productRepository.findProductByName(name);
        if (product == null) {
           return false;
        }
        return !Objects.equals(product.getId(), id);
    }

    @Override
    public void updateProductQuantity(InvoiceType invoiceType, InvoiceProduct invoiceProduct) {
        Product product = invoiceProduct.getProduct();
        if (invoiceProduct.getInvoice().equals(InvoiceType.SALES)) {
            product.setQuantityInStock(invoiceProduct.getProduct().getQuantityInStock() - invoiceProduct.getQuantity());
        } else {
            product.setQuantityInStock(invoiceProduct.getProduct().getQuantityInStock() + invoiceProduct.getQuantity());
        }
        productRepository.save(mapperUtil.convert(product, new Product()));
    }
}
