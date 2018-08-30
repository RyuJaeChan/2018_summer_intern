/*
 * ajax 요청을 보낸 후 응답이 오면 콜백함수 실행
 */
function requestAjax(parameterObejct) {
    let params = parameterObejct;

    let oReq = new XMLHttpRequest();

    oReq.open(params.method, params.url);

    if(params.contentType){
        oReq.setRequestHeader("Content-Type", params.contentType);
        params.data = JSON.stringify(params.data);
    }

    oReq.send(params.data);

    oReq.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            params.success(this.response);
        }
    }
}
