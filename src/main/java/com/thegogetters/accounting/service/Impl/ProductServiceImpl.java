package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.Product;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ProductRepository;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void save(ProductDTO dto) {
        Product product = mapperUtil.convert(dto, new Product());
        productRepository.save(product);
    }
    @Override
    public void update(ProductDTO dto) {
        //TODO
    }
    @Override
    public void deleteById(Long id) {
        //TODO
    }
}
