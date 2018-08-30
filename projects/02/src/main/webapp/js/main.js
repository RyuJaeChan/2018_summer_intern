/*
 * 버튼이 눌러지면 해당 노드를 옮기도록 요청한다.
 */
function moveNodeEvent(e) {
  var node = e.closest("li");
  var nextDiv = e.closest("div").nextElementSibling;

  requestTypeUpdate(node, nextDiv);
}

/*
 * TodoTypeServlet으로 데이터 업데이트 요청을 ajax로 보낸 후 성공하면 노드를 옮긴다.
 */
function requestTypeUpdate(node, nextDiv) {
  var oReq = new XMLHttpRequest();
  var id = node.dataset.id;
  var type = nextDiv.dataset.type;

  var data = {};
  data.type = type;

  oReq.addEventListener("load", function() {
    if (this.responseText === "success") {
      //Done이면 버튼을 삭제
      if (type == "DONE") {
        var moveButton = node.lastElementChild;
        node.removeChild(moveButton);
      }
      moveNode(node, nextDiv);
    } else {
      alert("Fail");
    }
  });

  oReq.open("PUT", "./type/" + id);
  oReq.setRequestHeader("Content-type", "application/json;charset=utf-8;");
  oReq.send(JSON.stringify(data));
}

/*
 * 해당 노드가 TODO면 DOING으로, DOING이면 DONE으로 이동한다.
 * 이동한 노드는 일자별 정렬 상태를 유지한다.
 */
function moveNode(node, nextDiv) {
  var ul = nextDiv.lastElementChild;
  var iterator = ul.firstElementChild;
  var date = node.dataset.date;

  iterator = getPosition(iterator, date);

  if(iterator == null){
    ul.appendChild(node);
    return;
  }

  iterator.insertAdjacentElement("beforebegin", node);
}

/*
 *  date를 비교하여 노드가 들어갈 위치를 찾는다.
 */
function getPosition(iterator, date){
  if(iterator == null){
    return null;
  }

  while(iterator){
    if(date < iterator.dataset.date){
      break;
    }
    iterator = iterator.nextElementSibling;
  }
  return iterator;
}
