package com.nts.connect.pjt3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.ProductImage;
import com.nts.connect.pjt3.mapper.ProductImageMapper;

/**
 * @author	유재찬
 * @date	2018. 8. 9.
 */
@Service
@Transactional(readOnly = true)
public class ProductImageService {

	@Autowired
	ProductImageMapper productImageMapper;

	public List<ProductImage> getProductImage(int displayInfoId, String type) {
		return productImageMapper.selectProductsImageByProductIdAndType(displayInfoId, type);
	}

	public long getProductImageCount(int displayInfoId, String type) {
		return productImageMapper.selectProductImageCount(displayInfoId, type);
	}
}
