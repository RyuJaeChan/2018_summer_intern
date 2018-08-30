package com.nts.connect.pjt3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.ReservationPrice;

/**
 * @author	유재찬
 * @date	2018. 8. 20.
 */
public interface ReservationPriceMapper {
	int selectReservationPrices(@Param("reservationInfoId") int reservationInfoId);

	long selectTotalPrice(@Param("reservationInfoId") int reservationInfoId);

	int insertReservationPrices(@Param("reservationPriceList") List<ReservationPrice> reservationPriceList);
}
