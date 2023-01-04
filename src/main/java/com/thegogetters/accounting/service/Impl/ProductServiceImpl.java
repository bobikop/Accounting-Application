package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.Product;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CompanyRepository;
import com.thegogetters.accounting.repository.InvoiceProductRepository;
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

    public ProductServiceImpl(MapperUtil mapperUtil, ProductRepository productRepository, CompanyRepository companyRepository, CompanyService companyService, InvoiceProductRepository invoiceProductRepository, InvoiceProductService invoiceProductService) {
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
    public ProductDTO getProductById(Long id) {
       Product product = productRepository.findProductById(id).orElseThrow();
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
    public void update(ProductDTO productDTO) {
        Product product = productRepository.findProductById(productDTO.getId()).orElseThrow();
        ProductDTO convertedProduct = mapperUtil.convert(product, new ProductDTO());
        convertedProduct.setId(product.getId());
        convertedProduct.setCategory(productDTO.getCategory());
        convertedProduct.setName(productDTO.getName());
        convertedProduct.setProductUnit(productDTO.getProductUnit());
        convertedProduct.setLowLimitAlert(productDTO.getLowLimitAlert());
        convertedProduct.setQuantityInStock(productDTO.getQuantityInStock());
        productRepository.save(mapperUtil.convert(convertedProduct, new Product()));
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findProductById(id).orElseThrow();

        //check if quantity of product is more than 0, user cannot delete that product.
            product.setIsDeleted(true);
            productRepository.save(product);
    }
    @Override
    public boolean checkAnyProductExist(Long id) {
        List<Product> products = productRepository.findAllByCategoryId(id);
        return products.size() > 0;
    }
//    @Override
//    public boolean isInStock(Long id) {
//        return productRepository.getQuantityInStock(id) > 0;
//    }
    @Override
    public List<ProductDTO> getAllProductsByCompany() {
        List<Product> products = productRepository.findAllByCompanyId(companyService.getCompanyOfLoggedInUser().getId());
        return products
                .stream()
                .map(product-> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }
    @Override
    public boolean checkAnyInvoiceExist(Long id) {
        return invoiceProductService.findInvoiceProductsByProductID(id).size() > 0;
    }

    @Override
    public boolean isNameExist(String name, Long id) {
        Product product=productRepository.findProductByName(name).orElse(null);
        if (product == null) {
            return false;
        }
        return !Objects.equals(product.getId(), id);
    }
}