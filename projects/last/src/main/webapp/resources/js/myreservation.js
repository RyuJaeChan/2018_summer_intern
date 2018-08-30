myreservationPage = {};

myreservationPage.confirmedArea = {
    confirmedList: document.querySelector(".card.confirmed").querySelectorAll(".card_item"),

    init(){
        this.registerEvents();
    },
    registerEvents(){
        this.confirmedList.forEach(element=>{
            element.querySelector(".booking_cancel .btn")
                .addEventListener("click", this.cancel.bind(element));
        });
    },
    cancel(){
        requestAjax({
            url: "./api/reserve",
            method: "PUT",
            contentType: "application/json;charset=utf-8",
            data: {
                "id": this.querySelector(".booking_number").innerText.replace(/[^0-9]/g,"")
            },
            success: function(response){
                alert("취소되었습니다.");
                window.location.href = "./myreservation";
            }
        });
    }
}

myreservationPage.initialize = function() {
    this.confirmedArea.init();
};
