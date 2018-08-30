function TicketElement(divElement) {
    this.parentDiv = divElement;
    this.priceId = this.parentDiv.querySelector(".priceId").innerText;
    this.price = parseInt(this.parentDiv.querySelector(".price").innerText.removeCommas());
    this.ticketCount = this.parentDiv.querySelector(".count_control_input");
    this.priceSum = this.parentDiv.querySelector(".total_price");
    this.minusButtonElemnt = this.parentDiv.querySelector(".btn_plus_minus.ico_minus3");
    this.plusButtonElemnt = this.parentDiv.querySelector(".btn_plus_minus.ico_plus3");
    this.priceTextSpan = this.parentDiv.querySelector(".individual_price");
    this.ticketTotalCount = document.querySelector("#totalCount");

    if (this.ticketCount.value === "0") {
        this.updateButtonState(false);
    }

    this.registerEvents();
}

TicketElement.prototype = {
    registerEvents: function() {
        this.minusButtonElemnt.addEventListener("click", this.minusButtonEvent.bind(this));
        this.plusButtonElemnt.addEventListener("click", this.plusButtonEvent.bind(this));
    },
    minusButtonEvent: function() {
        this.ticketCount.value--;
        this.priceSum.innerText = (this.price * this.ticketCount.value).addCommas();
        if (this.ticketCount.value === "0") {
            this.updateButtonState(false);
        }
        this.ticketTotalCount.innerText--;
    },
    plusButtonEvent: function() {
        this.ticketCount.value++;
        this.priceSum.innerText = (this.price * this.ticketCount.value).addCommas();
        this.updateButtonState(true);

        this.ticketTotalCount.innerText++;
    },
    updateButtonState: function(flag) {
        if (flag === false) {
            this.minusButtonElemnt.classList.add("disabled");
            this.minusButtonElemnt.style.pointerEvents = "none";
            this.ticketCount.classList.add("disabled");
            this.priceTextSpan.classList.remove("on_color");
        } else {
            this.minusButtonElemnt.classList.remove("disabled");
            this.minusButtonElemnt.style.pointerEvents = "";
            this.ticketCount.classList.remove("disabled");
            this.priceTextSpan.classList.add("on_color");
        }
    }
} // end of TicketElement.prototype
