package com.example.sell.service.Impl;

import com.example.sell.dataobject.ProductInfo;
import com.example.sell.dto.CartDTO;
import com.example.sell.enums.ProductStatusEnum;
import com.example.sell.enums.ResultEnum;
import com.example.sell.exception.SellException;
import com.example.sell.repository.ProductInfoRepository;
import com.example.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        if (repository.findById(productId).isPresent()) {
            return repository.findById(productId).get();
        }
        return null;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            if (repository.findById(cartDTO.getProductId()).isPresent()) {
                ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
                Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
                productInfo.setProductStock(result);
                repository.save(productInfo);
            } else {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            if (repository.findById(cartDTO.getProductId()).isPresent()) {
                ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
                Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
                if (result < 0) {
                    throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
                }
                productInfo.setProductStock(result);
                repository.save(productInfo);
            } else {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        if (repository.findById(productId).isPresent()) {
            ProductInfo productInfo = repository.findById(productId).get();
            if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
                throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            return repository.save(productInfo);
        } else {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
    }

    @Override
    public ProductInfo offSale(String productId) {
        if (repository.findById(productId).isPresent()) {
            ProductInfo productInfo = repository.findById(productId).get();
            if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
                throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
            return repository.save(productInfo);
        } else {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
    }
}
