<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>할 일 등록</title>
  <link rel="stylesheet" href="./css/style.css">
</head>

<body>
  <div class="wrap">
    <header class="input_header">
      <h1>할일 등록</h1>
    </header>
    <section class="input_con">
      <form class="input_form" action="./add" method="post">
        <div class="input_area">
          <ul>
            <li>
              <label>어떤 일인가요?</label>
            </li>
            <li>
              <input id="todo" type="text" maxlength="24" size="30px" name="title" placeholder="할 일을 작성해주세요." required>
            </li>
            <li>
              <label>누가 할 일인가요?</label>
            </li>
            <li>
              <input type="text" maxlength="32" name="name" placeholder="홍길동" required>
            </li>
            <li>
              <label>우선순위를 선택하세요.</span>
            </li>
            <li>
              <ul class="checkbox_ul">
                <li><input type="radio" name="sequence" value="1" required>1순위</input></li>
                <li><input type="radio" name="sequence" value="2">2순위</input></li>
                <li><input type="radio" name="sequence" value="3">3순위</input></li>
              </ul>
            </li>
          </ul>
        </div>
        <div class="button_area">
          <button id="back_button">< 이전</button>
          <button type="submit" name="submit_button">제출</button>
          <button type="reset" name="reset_button">내용 지우기</button>
        </div>
      </form>
    </section>
  </div>
</body>

<script>
document.addEventListener("DOMContentLoaded", function(){
  var backButton = document.querySelector("#back_button");
  backButton.addEventListener('click', function(){
    history.back();
  });
});
</script>

</html>
