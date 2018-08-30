package com.nts.connect.pjt3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.FileInfo;

public interface FileInfoMapper {
	int insertFileInfoList(@Param("list") List<FileInfo> fileInfoList);
}
