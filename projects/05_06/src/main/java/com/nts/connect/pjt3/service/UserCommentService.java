package com.nts.connect.pjt3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nts.connect.pjt3.dto.FileInfo;
import com.nts.connect.pjt3.dto.UserComment;
import com.nts.connect.pjt3.mapper.FileInfoMapper;
import com.nts.connect.pjt3.mapper.UserCommentMapper;
import com.nts.connect.pjt3.util.FileManagement;

/**
 * @author	유재찬
 * @date	2018. 8. 14.
 */
@Service
@Transactional(readOnly = true)
public class UserCommentService {
	@Autowired
	private UserCommentMapper userCommentMapper;
	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Resource(name = "FileManagement")
	private FileManagement fileManagement;

	public List<UserComment> getUserCommentList(int productId) {
		return userCommentMapper.selectUserCommentAll(productId);
	}

	public BigDecimal getAverageScore(int productId) {
		return userCommentMapper.selectAverageScore(productId);
	}

	public long getCommentCount(int productId) {
		return userCommentMapper.selectCommentCount(productId);
	}

	public UserComment getUserComment(int id) {
		return userCommentMapper.selectUserComment(id);
	}

	@Transactional
	public void insertComment(UserComment userComment, List<MultipartFile> imageFiles) throws IOException {

		List<FileInfo> fileInfoList = new ArrayList<>();

		fileManagement.saveFiles(imageFiles, (fileInfo) -> {
			fileInfoList.add(fileInfo);
		});

		userCommentMapper.insertComment(userComment);

		if (!fileInfoList.isEmpty()) {
			fileInfoMapper.insertFileInfoList(fileInfoList);
			userCommentMapper.insertCommentImages(userComment.getReservationInfoId(), userComment.getId(),
				fileInfoList);
		}

	}

}
