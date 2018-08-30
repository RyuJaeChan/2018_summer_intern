package com.nts.connect.pjt3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.Product;
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

	public Product getProductByDisplayInfoId(int displayInfoid) {
		return productMapper.selectProductByDisplayInfotId(displayInfoid);
	}
}
