const GET_PRODUCTS = "./api/productThumbnails?start={1}&categoryId={2}";
const GET_PROMOTIONS = "./api/promotions";
const GET_CATEGORIES = "./api/categories";


/*
* 초기화 실행
*/
function initialize(){
  loadPromotions();

  loadCategories();

  loadProducts();

  addEventListeners();
}


/*
* 필요한 이벤트 리스너를 추가
*/
function addEventListeners(){
  var categoryUl = document.querySelector(".event_tab_lst");
  categoryUl.addEventListener("click", categoryTabClickedEvent);

  var moreButton = document.querySelector(".btn");
  moreButton.addEventListener("click", loadMoreProducts);

  var promotionUl = document.querySelector(".visual_img");
  promotionUl.addEventListener("transitionend", sendToBack);

}


/*
* ajax 요청을 보낸 후 응답이 오면 콜백함수 실행
*/
function requestAjax(requestUrl, callBackFunction) {
  var oReq = new XMLHttpRequest();

  oReq.addEventListener("load", callBackFunction);

  oReq.open("GET", requestUrl);

  oReq.send();
}


/*
* 카테고리 목록을 불러와 화면에 표시
*/
function loadCategories(){
  requestAjax(GET_CATEGORIES, function(){
    var resultJson = JSON.parse(this.response);

    var categoryUl = document.querySelector(".event_tab_lst");
    var itemTemplate = document.querySelector("#categoryItem").innerText;

    if(resultJson.result !== "success"){
        alert("요청이 실패하였습니다. (result : " + resultJson.result + ")");
        return;
    }
    resultJson.body.items.forEach(data =>{
      categoryUl.innerHTML += formatTemplate(itemTemplate, data);
    });

  });

}



/*
* 서버에서 promotion 리스트를 가져와 화면에 표시하고 움직이도록 설정
*/
function loadPromotions() {
  requestAjax(formatUrl(GET_PROMOTIONS, 0, 0), function() {
    var resultJson = JSON.parse(this.response);

    if(resultJson.result !== "success"){
        alert("요청이 실패하였습니다. (result : " + resultJson.result + ")");
        return;
    }

    addPromotionsList(resultJson.body.items, resultJson.body.size);

    if(resultJson.size > 1){
      requestAnimationFrame(slidePromotion);
    }

  });

}


/*
* 화면에 promotion 추가
*/
function addPromotionsList(list, size) {
  var promotionUl = document.querySelector(".visual_img");
  var itemTemplate = document.querySelector("#promotionItem").innerText;

  var maxWidth = size * 100;
  promotionUl.style.width = maxWidth + "%";

  list.forEach(data => {
    promotionUl.innerHTML += formatTemplate(itemTemplate, data);
  });

}


/*
* 화면에 표시된 promotion 슬라이드가 움직이게 한다.
*/
var start = null;
function slidePromotion(timestamp) {
  if (!start) start = timestamp;
  var progress = timestamp - start;
  if (progress > 4000) {
    movePromotionImage();
    start = null;
  }

  requestAnimationFrame(slidePromotion);
}


/*
* promotion 이미지 이동
*/
function movePromotionImage() {
  var promotionUl = document.querySelector(".visual_img");

  promotionUl.classList.add("moving");
  promotionUl.style.transform = "translateX(-414px)";
}


/*
* promotion의 이동이 완료되면 맨 뒤로 보내고 위치를 조정
*/
function sendToBack(evt){
  var promotionUl = evt.target;

  if(promotionUl.classList.contains("moving")) {
    promotionUl.classList.remove("moving");

    promotionUl.style.transform = "translateX(0)";

    promotionUl.insertAdjacentElement("beforeend", promotionUl.firstElementChild);
  }

}


/*
* 처음 메인 페이지 로드 시 서버에서 product 리스트를 가져와 하면에 표시
*/
function loadProducts() {
  var categoryId = document.querySelector(".active").dataset.category;

  requestAjax(formatUrl(GET_PRODUCTS, 0, categoryId), function() {
    var resultJson = JSON.parse(this.response);

    if(resultJson.result !== "success"){
        alert("요청이 실패하였습니다. (result : " + resultJson.result + ")");
        return;
    }

    setCategoryCount(resultJson.body.size);

    addProductsList(resultJson.body.items, resultJson.body.size);

  });

}

/*
* 화면에 전체 개수를 표시
* ex) 바로 예매 가능한 행사가 00개 있습니다.
*/
function setCategoryCount(countValue) {
  var countSpan = document.querySelector(".pink");
  countSpan.innerText = countValue + "개";
}


/*
* 화면에 product 리스트를 표시
* 모든 product를 화면에 표시했으면 더 보기 버튼 비활성화
*/
function addProductsList(list, totalCount) {
  var firstUl = document.querySelector(".lst_event_box");
  var secondUl = firstUl.nextElementSibling;
  var itemTemplate = document.querySelector("#itemList").innerText;

  list.forEach((data, index) => {
    if (index % 2 == 0) {
      firstUl.innerHTML += formatTemplate(itemTemplate, data);
    } else {
      secondUl.innerHTML += formatTemplate(itemTemplate, data);
    }
  });

  if (getDisplayedProductsCount() != totalCount) {
    enableMoreButton(true);
  } else {
    enableMoreButton(false);
  }

}


/*
* 더 보기 버튼을 활성화/비활성화
*/
function enableMoreButton(flag){
  var moreBtn = document.querySelector(".btn");

  if (flag == true) {
    moreBtn.style.display = "inline-block"
  }
  else {
    moreBtn.style.display = "none";
  }
}


/*
* 더보기 버튼을 누르면 서버에서 product를 불러와 화면에 표시
*/
function loadMoreProducts() {
  var productsCount = getDisplayedProductsCount();
  var categoryId = document.querySelector(".active").dataset.category;

  requestAjax(formatUrl(GET_PRODUCTS, productsCount, categoryId), function() {
    var resultJson = JSON.parse(this.response);

    if(resultJson.result !== "success"){
        alert("요청이 실패하였습니다. (result : " + resultJson.result + ")");
        return;
    }

    addProductsList(resultJson.body.items, resultJson.body.size);
  });

}


/*
* 화면에 표시된 product들의 개수를 반환
*/
function getDisplayedProductsCount(){
  var firstUl = document.querySelector(".lst_event_box");
  var secondUl = firstUl.nextElementSibling;
  return firstUl.childElementCount + secondUl.childElementCount;
}


/*
* 클릭한 아이템을 강조하고 해당 카테고리의 product 목록을 화면에 표시
*/
function categoryTabClickedEvent(evt){
    if (evt.target.nodeName == "SPAN") {
      evt.stopPropagation();

      var clickedSpan = evt.target;
      highlightClickedCategory(clickedSpan);

      removeProductsList();

      loadProducts();
    }

}


/*
* 화면에 표시되었던 모든 product를 지운다.
*/
function removeProductsList(){
  var firstUl = document.querySelector(".lst_event_box");
  var secondUl = firstUl.nextElementSibling;

  firstUl.innerText = "";
  secondUl.innerText = "";
}


/*
* 클릭한 카테고리 아이템을 녹색으로 강조
*/
function highlightClickedCategory(clickedSpan){
  var prevActive = document.querySelector(".active");
  prevActive.classList.remove("active");

  var currAnchor = clickedSpan.closest("a");
  currAnchor.classList.add("active");
}


/*
* 입력 문자열 str에 이후 입력 인자를 포맷팅한다. 입력 문자열에 변환할 값은 {num} 형식으로 지정한다.
* 입력 문자열 양식 : "./api/products?start={1}"  : 매칭 시킬 숫자는 1부터 시작
*/
function formatUrl(str) {
  var idx = arguments.length - 1;
  while(0 < idx){
    str = str.replace("{" + idx +"}", arguments[idx]);
    idx--;
  }
  return str;
}


/*
* 입력 str에 포함된 data의 키 값을 해당하는 값으로 변환
*/
function formatTemplate(str, data){
  Object.keys(data).forEach((key) =>{
    str = replaceAll(str, "{" + key + "}", data[key]);
  });
  return str;
}


/*
* 입력 str에 포함된 부분 문자열 searchStr을 모두 찾아 replaceStr으로 변환
*/
function replaceAll(str, searchStr, replaceStr) {
  return str.split(searchStr).join(replaceStr);
}
