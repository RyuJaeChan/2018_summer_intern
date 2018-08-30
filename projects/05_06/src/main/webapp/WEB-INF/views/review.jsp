<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

    <head>
        <meta charset="utf-8">
        <meta name="description" content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
        <title>네이버 예약</title>
        <link href="./css/style.css" rel="stylesheet">
    </head>

    <body>
        <div id="container">
            <div class="ct main">
                <div>
                    <div class="top_title">
                        <a href="./detail?id=${productId}" class="btn_back" title="이전 화면으로 이동">
                            <i class="fn fn-backward1"></i>
                        </a>
                        <h2>
                            <span class="title"></span></h2>
                    </div>
                    <div class="section_review_list">
                        <div class="review_box">
                            <h3 class="title_h3">예매자 한줄평</h3>
                            <div class="short_review_area">
                                <div class="grade_area">
                                    <!-- [D] 별점 graph_value는 퍼센트 환산하여 width 값을 넣어줌 -->
                                    <span class="graph_mask">
                                        <em class="graph_value" style="width: <fmt:formatNumber value="${score*10}" />%;"></em>
                                    </span>
                                    <strong class="text_value">
                                        <span><fmt:formatNumber value="${score}" pattern="0.0"/></span>
                                        <em class="total">5.0</em>
                                    </strong>
                                    <span class="join_count">
                                        <em class="green">${count}건</em>
                                        등록</span>
                                </div>
                                <c:if test="${commentList.size() eq 0}">
                                    <h3 style="height: 70px; line-height:70px;">등록된 리뷰가 없습니다.</h3>
                                </c:if>
                                <ul class="list_short_review">
                                    <c:forEach var="item" items="${commentList}">
                                        <li class="list_item">
                                            <div>
                                                <div class="review_area">
                                                    <div class="thumb_area">
														<c:if test="${item.getUserCommentImageList().isEmpty() eq false}">
															<a href="./api/comments/${item.getId()}/image" class="thumb" title="이미지 크게 보기">
																<img width="90" height="90" class="img_vertical_top" src="./api/comments/${item.getId()}/image" alt="리뷰이미지">
															</a>
															<span class="img_count" style="display:none;">1</span-->
														</c:if>
                                                    </div>
                                                    <h4 class="resoc_name"></h4>
                                                    <p class="review">${item.getComment()}</p>
                                                </div>
                                                <div class="info_area">
                                                    <div class="review_info">
                                                        <span class="grade"><fmt:formatNumber value="${item.getScore()}" pattern="0.0"/></span>
                                                        <span class="name">${fn:substring(item.getReservationEmail(), 0, 4)}****</span>
                                                        <span class="date">${localDateTimeFormat.format(item.getReservationDate())} 방문</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <p class="guide">
                                <i class="spr_book2 ico_bell"></i>
                                <span>네이버 예약을 통해 실제 방문한 이용자가 남긴 평가입니다.</span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
