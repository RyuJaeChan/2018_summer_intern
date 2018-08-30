/*
* ajax 요청을 보낸 후 응답이 오면 콜백함수 실행
*/
function requestAjax(requestUrl, callBackFunction) {
  var oReq = new XMLHttpRequest();

  oReq.addEventListener("load", function(){
    var resultJson = JSON.parse(this.response);
    callBackFunction(resultJson);
  });

  oReq.open("GET", requestUrl);

  oReq.send();
}
