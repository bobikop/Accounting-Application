package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.Product;
import com.thegogetters.accounting.mapper.ProductMapper;
import com.thegogetters.accounting.repository.ProductRepository;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findProductById(id);
        return productMapper.convertToDto(product);
    }

    @Override
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(ProductDTO dto) {
    //TODO
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
