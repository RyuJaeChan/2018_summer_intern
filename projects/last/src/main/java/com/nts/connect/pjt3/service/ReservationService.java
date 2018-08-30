package com.nts.connect.pjt3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.ReservationInfo;
import com.nts.connect.pjt3.dto.ReservationPrice;
import com.nts.connect.pjt3.enumeration.ReservationType;
import com.nts.connect.pjt3.mapper.ReservationInfoMapper;
import com.nts.connect.pjt3.mapper.ReservationPriceMapper;

/**
 * @author	유재찬
 * @date	2018. 8. 20.
 */
@Service
@Transactional(readOnly = true)
public class ReservationService {
	@Autowired
	private ReservationInfoMapper reservationInfoMapper;
	@Autowired
	private ReservationPriceMapper reservationPriceMapper;

	@Transactional
	public int insertReservation(ReservationInfo reservation) {
		reservationInfoMapper.insertReservation(reservation);

		for (ReservationPrice element : reservation.getReservationPriceList()) {
			element.setReservationInfoId(reservation.getId());
		}

		reservationPriceMapper.insertReservationPrices(reservation.getReservationPriceList());

		return reservation.getId();
	}

	public List<ReservationInfo> getReservationInfoByEmail(ReservationType type, String email) {
		return reservationInfoMapper.selectReservationInfoByEmail(type, email);
	}

	public ReservationInfo getReservationInfo(int reservationInfoId) {
		return reservationInfoMapper.selectReservationInfoById(reservationInfoId);
	}

	@Transactional
	public int cancelReservationInfo(int id) {
		return reservationInfoMapper.cancelReservationInfo(id);
	}
}
