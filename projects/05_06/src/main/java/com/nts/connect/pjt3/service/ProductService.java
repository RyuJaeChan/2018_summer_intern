package com.nts.connect.pjt3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.Product;
import com.nts.connect.pjt3.dto.ProductImage;
import com.nts.connect.pjt3.dto.ProductThumbnail;
import com.nts.connect.pjt3.mapper.ProductMapper;

/**
 * @author	유재찬
 * @date	2018. 8. 7.
 */
@Service
@Transactional(readOnly = true)
public class ProductService {
	@Autowired
	private ProductMapper productMapper;

	public Product getProductByDisplayInfoId(int displayInfoId) {
		return productMapper.selectProductWithCommentByDisplayInfotId(displayInfoId);
	}

	public Product getProductWithPriceByDisplayInfoId(int displayInfoId) {
		return productMapper.selectProductWithPriceByDisplayInfotId(displayInfoId);
	}

	public List<ProductThumbnail> getProductThumbnailsByCategoryId(int categoryId, int start, int limit) {
		return productMapper.selectProductThumbnailsByCategoryId(categoryId, start, limit);
	}

	public long getProductThumbnailCountByCategoryId(int categoryId) {
		return productMapper.selectProductThumbnailCountByCategoryId(categoryId);
	}

	public List<ProductImage> getProductImage(int displayInfoId, String type) {
		return productMapper.selectProductsImageByProductIdAndType(displayInfoId, type);
	}

}
