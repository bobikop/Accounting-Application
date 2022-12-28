package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.Product;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ProductRepository;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final MapperUtil mapperUtil;
    private final ProductRepository productRepository;

    public ProductServiceImpl(MapperUtil mapperUtil, ProductRepository productRepository) {
        this.mapperUtil = mapperUtil;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findProductById(id);
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public void save(ProductDTO productDTO) {
        Product product = mapperUtil.convert(productDTO, new Product());
        productRepository.save(product);
    }

    @Override
    public void update(ProductDTO productDTO) {
        Product product = productRepository.findProductById(productDTO.getId());
        ProductDTO convertedProduct = mapperUtil.convert(product, new ProductDTO());
        convertedProduct.setId(product.getId());
        convertedProduct.setCategory(mapperUtil.convert(product.getCategory(), new CategoryDto()));
        convertedProduct.setName(product.getName());
        convertedProduct.setProductUnit(product.getProductUnit());
        convertedProduct.setLowLimitAlert(product.getLowLimitAlert());
        convertedProduct.setQuantityInStock(product.getQuantityInStock());
        productRepository.save(mapperUtil.convert(convertedProduct, new Product()));
    }

    @Override
    public void deleteById(Long id) {
    Product product = productRepository.findProductById(id);
    product.setIsDeleted(true);
    productRepository.save(product);
    }

     @Override
    public boolean checkAnyProductExist(Long id) {
        List <Product> products = productRepository.findAllByCategoryId(id);
        if (products.size() > 0) return true;
        return false;
    }

  }
