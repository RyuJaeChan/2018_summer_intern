let reservePage = {};

reservePage.ticketArea = {
    ticketElementList: [],
    init: function() {
        this.setPriceElementList();
    },
    setPriceElementList: function() {
        let currentElement = document.querySelector(".ticket_body");
        let index = 0;
        let size = currentElement.childElementCount;
        for (let i = 0; i < size; i++) {
            let element = new TicketElement(currentElement.children[i]);
            this.ticketElementList.push(element);
        }
    },
    makeTicketData: function() {
        return reservePage.ticketArea.ticketElementList.map((element) => {
            return {
                "productPriceId": element.priceId,
                "count": element.ticketCount.value
            };
        });
    }
} //end of reservePage.ticketArea

reservePage.reserveButton = {
    reserveButtonWrap: document.querySelector(".bk_btn_wrap"),
    reserveButton: document.querySelector(".bk_btn"),
    formValidation: false,
    agreementValidtion: false,

    init: function() {
        this.registerEvents();
    },
    registerEvents: function() {
        this.reserveButton.addEventListener("click", this.reserve.bind(this));
    },
    updateFormValidation: function(flag) {
        this.formValidation = flag;

        this.updateButtonState();
    },
    updateAgreementValidation: function(flag) {
        this.agreementValidtion = flag;

        this.updateButtonState();
    },
    updateButtonState: function() {
        if (this.formValidation === true && this.agreementValidtion === true) {
            this.enableButton(true);
        } else {
            this.enableButton(false);
        }
    },
    enableButton: function(flag) {
        if (flag === true) {
            this.reserveButtonWrap.classList.remove("disable");
            this.reserveButton.disabled = false;
        } else {
            this.reserveButtonWrap.classList.add("disable");
            this.reserveButton.disabled = true;
        }
    },
    reserve: function() {
        requestAjax({
            url: "./api/reserve",
            method: "POST",
            contentType: "application/json;charset=utf-8",
            data: {
                "productId": document.querySelector("#productId").value,
                "reservationName": document.querySelector("#name").value,
                "reservationEmail": document.querySelector("#email").value,
                "reservationTel": document.querySelector("#tel").value,
                "reservationDate": document.querySelector("#reservateDate").innerText,
                "reservationPriceList": reservePage.ticketArea.makeTicketData()
            },
            success: function(response) {
                alert("예약이 완료되었습니다.");
                window.location.href = "./myreservation";
            }
        });
    }
}

reservePage.formArea = {
    nameValid: false,
    telValid: false,
    emailValid: false,

    init: function() {
        registerValidator({
            inputElement: document.querySelector(".text"),
            inputType: "notEmpty",
            correct: function() {
                let warningText = document.querySelector(".warning_msg.name");
                warningText.style.visibility = "hidden";
                warningText.style.position = "absolute";

                reservePage.formArea.nameValid = true;
                reservePage.reserveButton.updateFormValidation(reservePage.formArea.isFormValid());
            },
            wrong: function() {
                let warningText = document.querySelector(".warning_msg.name");
                warningText.style.visibility = "visible";
                warningText.style.position = "relative";

                reservePage.formArea.nameValid = false;
                reservePage.reserveButton.updateFormValidation(reservePage.formArea.isFormValid());
            }
        });

        registerValidator({
            inputElement: document.querySelector(".tel"),
            inputType: "tel",
            correct: function() {
                let warningText = document.querySelector(".warning_msg.tel");
                warningText.style.visibility = "hidden";
                warningText.style.position = "absolute";

                reservePage.formArea.telValid = true;
                reservePage.reserveButton.updateFormValidation(reservePage.formArea.isFormValid());
            },
            wrong: function() {
                let warningText = document.querySelector(".warning_msg.tel");
                warningText.style.visibility = "visible";
                warningText.style.position = "relative";

                reservePage.formArea.telValid = false;
                reservePage.reserveButton.updateFormValidation(reservePage.formArea.isFormValid());
            }
        });

        registerValidator({
            inputElement: document.querySelector(".email"),
            inputType: "email",
            correct: function() {
                let warningText = document.querySelector(".warning_msg.email");
                warningText.style.visibility = "hidden";
                warningText.style.position = "absolute";

                reservePage.formArea.emailValid = true;
                reservePage.reserveButton.updateFormValidation(reservePage.formArea.isFormValid());
            },
            wrong: function() {
                let warningText = document.querySelector(".warning_msg.email");
                warningText.style.visibility = "visible";
                warningText.style.position = "relative";

                reservePage.formArea.emailValid = false;
                reservePage.reserveButton.updateFormValidation(reservePage.formArea.isFormValid());
            }
        });
    },
    enableWarningMessage: function(element, flag) {
        if (flage === true) {
            warningText.style.visibility = "hidden";
            warningText.style.position = "absolute";
        } else {
            warningText.style.visibility = "visible";
            warningText.style.position = "relative";
        }
    },
    isFormValid: function() {
        return this.nameValid && this.telValid && this.emailValid;
    }

} //end of reservePage.formArea

reservePage.agreementArea = {
    agreementCheckBox: document.querySelector("#chk3"),
    reserveButtonWrap: document.querySelector(".bk_btn_wrap"),
    reserveButton: document.querySelector(".bk_btn"),
    firstShowButton: document.querySelectorAll(".btn_agreement")[0],
    secondShowButton: document.querySelectorAll(".btn_agreement")[1],

    init: function() {
        this.agreementCheckBox.checked = false;
        this.reserveButton.disabled = true;

        this.registerEvents();
    },
    registerEvents: function() {
        this.agreementCheckBox.addEventListener("click", this.checkBoxClickEvent.bind(this));
        this.firstShowButton.addEventListener("click", this.showAgreementContent.bind(this));
        this.secondShowButton.addEventListener("click", this.showAgreementContent.bind(this));
    },
    checkBoxClickEvent: function(evt) {
        if (evt.target.checked === true) {
            reservePage.reserveButton.updateAgreementValidation(true);
        } else {
            reservePage.reserveButton.updateAgreementValidation(false);
        }
    },
    showAgreementContent: function(evt) {
        let parentDiv = evt.target.closest(".agreement");

        parentDiv.classList.toggle("open");
        parentDiv.querySelector(".fn").classList.toggle("fn-up2");
        parentDiv.querySelector(".fn").classList.toggle("fn-down2");
    }
} //end of reservePage.agreementArea

reservePage.initialize = function() {
    this.ticketArea.init();
    this.reserveButton.init();
    this.formArea.init();
    this.agreementArea.init();
}
