package com.nts.connect.pjt3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.ReservationInfo;
import com.nts.connect.pjt3.enumeration.ReservationType;

/**
 * @author	유재찬
 * @date	2018. 8. 20.
 */
public interface ReservationInfoMapper {
	int insertReservation(ReservationInfo reservation);

	ReservationInfo selectReservationInfoById(@Param("id") int reservationInfoId);

	List<ReservationInfo> selectReservationInfoByEmail(@Param("type") ReservationType type,
		@Param("email") String email);

	int cancelReservationInfo(@Param("id") int id);
}
