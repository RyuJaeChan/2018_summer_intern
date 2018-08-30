package com.nts.connect.pjt3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.ProductThumbnail;
import com.nts.connect.pjt3.mapper.ProductThumbnailMapper;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
@Service
@Transactional(readOnly = true)
public class ProductThumbnailService {
	@Autowired
	private ProductThumbnailMapper productThumbnailMapper;

	public List<ProductThumbnail> getProductThumbnailsByCategoryId(int categoryId, int start, int limit) {
		return productThumbnailMapper.getProductThumbnailsByCategoryId(categoryId, start, limit);
	}

	public long getProductThumbnailCountByCategoryId(int categoryId) {
		return productThumbnailMapper.getProductThumbnailCountByCategoryId(categoryId);
	}
}
