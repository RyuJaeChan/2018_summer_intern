<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="utf-8"/>
  <title>나의 할 일</title>
  <link rel="stylesheet" href="./css/style.css?ver=1"/>
</head>

<body>
  <div class="wrap">
    <header>
        <p>나의 해야 할 일들</p>
    </header>
    <section class="main_con">
      <a href="./form">새로운 TODO 등록</a>
      <div class="table">
        <div data-type="TODO">
          <h1>TODO</h1>
          <ul>
            <c:forEach items="${todoList}" var="item">
              <li data-id="${item.getId()}" data-date="${item.getRegdate()}">
                <h3>${item.getTitle()}</h3>
                <p>등록날짜 : ${fn:substring(item.getRegdate(),0,10)}</p><span>${item.getName()} 우선순위 ${item.getSequence()}</span>
                <button class="move_button">▶</button>
              </li>
            </c:forEach>
          </ul>
        </div>
        <div data-type="DOING">
          <h1>DOING</h1>
          <ul>
            <c:forEach items="${doingList}" var="item">
              <li data-id="${item.getId()}" data-date="${item.getRegdate()}">
                <h3>${item.getTitle()}</h3>
                <p>등록날짜 : ${fn:substring(item.getRegdate(),0,10)}</p><span>${item.getName()} 우선순위 ${item.getSequence()}</span>
                <button class="move_button">▶</button>
              </li>
            </c:forEach>
          </ul>
        </div>
        <div data-type="DONE">
          <h1>DONE</h1>
          <ul>
            <c:forEach items="${doneList}" var="item">
              <li data-id="${item.getId()}" data-date="${item.getRegdate()}">
                <h3>${item.getTitle()}</h3>
                <p>등록날짜 : ${fn:substring(item.getRegdate(),0,10)}</p><span>${item.getName()} 우선순위 ${item.getSequence()}</span>
              </li>
            </c:forEach>
          </ul>
        </div>
      </div>
    </section>
  </div>
</body>

<script>
document.addEventListener("DOMContentLoaded", function() {
  var table = document.querySelector(".table");

  table.addEventListener("click", function(evt) {
    if (evt.target.nodeName == "BUTTON") {
      moveNodeEvent(evt.target);
      evt.stopPropagation();
    }
  });
});
</script>
<script src="./js/main.js?ver=1"></script>

</html>
