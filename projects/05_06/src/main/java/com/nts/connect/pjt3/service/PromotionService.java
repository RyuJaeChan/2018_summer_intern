package com.nts.connect.pjt3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.Promotion;
import com.nts.connect.pjt3.mapper.PromotionMapper;

/**
 * @author	유재찬
 * @date	2018. 8. 7.
 */
@Service
@Transactional(readOnly = true)
public class PromotionService {
	@Autowired
	private PromotionMapper promotionMapper;

	public List<Promotion> getPromotions() {
		return promotionMapper.selectPromotionsAll();
	}

}
