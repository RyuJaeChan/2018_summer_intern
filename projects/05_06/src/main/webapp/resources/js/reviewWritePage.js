let ReviewWritePage = {};

ReviewWritePage.textArea = {
    reviewInfo: document.querySelector(".review_write_info"),
    reviewTextArea: document.querySelector(".review_textarea"),
    currentTextCount: document.querySelector(".guide_review span"),
    registerButton: document.querySelector(".bk_btn"),

    init() {
        this.reviewTextArea.mimLength = 5;
        this.reviewTextArea.maxLength = 400;

        this.registerEvents();
    },
    registerEvents() {
        this.reviewInfo.addEventListener("click", this.hideReviewInfo.bind(this));
        this.reviewTextArea.onblur = function() {
            if (this.reviewTextArea.textLength === 0) {
                this.showReviewInfo();
            }
        }.bind(this);
        this.reviewTextArea.onkeyup = function() {
            this.currentTextCount.innerText = this.reviewTextArea.textLength;
        }.bind(this);
    },
    hideReviewInfo() {
        this.reviewInfo.style.display = "none";
        this.reviewTextArea.focus();
    },
    showReviewInfo() {
        this.reviewInfo.style.display = "block";
        this.reviewTextArea.focus();
    }
};//end of ReviewWritePage.textArea

ReviewWritePage.ratingArea = {
    starArea: document.querySelector(".review_rating .rating"),
    ratingStars: document.querySelectorAll(".rating .rating_rdo"),
    ratingCountSpan: document.querySelector(".rating .star_rank"),

    init(){
        this.registerEvents();
    },
    registerEvents(){
        this.starArea.addEventListener("click", function(evt){
            if(evt.target.type === "checkbox"){
                this.fillStarColor(evt.target.value);
                this.ratingCountSpan.innerText = evt.target.value;
                this.ratingCountSpan.classList.remove("gray_star");
            }
        }.bind(this));
    },
    fillStarColor(rate){
        for(let i = 0; i < rate; i++){
            this.ratingStars[i].checked = true;
        }
        for(let i = rate; i < 5; i++){
            this.ratingStars[i].checked = false;
        }
    }
};//end of ReviewWritePage.ratingArea

ReviewWritePage.imageFileArea = {
    imageInput: document.querySelector(".review_write_footer #reviewImageFileOpenInput"),
    imageThumbList: document.querySelector(".lst_thumb"),

    init() {
        this.registerEvents();
    },
    registerEvents() {
        this.imageThumbList.addEventListener("click", this.removeImageThumbnail.bind(this));
        this.imageInput.addEventListener("change", this.addImageThumbnail.bind(this));
    },
    removeImageThumbnail(evt){
        if(evt.target.className == "spr_book ico_del"){
            evt.target.parentElement.parentElement.remove();
            this.imageInput.value = "";
            evt.stopPropagation();
        }
    },
    addImageThumbnail(){
        this.imageThumbList.innerHTML = "";
        var fileList = this.imageInput.files;
        if (fileList.length === 0) {
            return;
        }

        if(!this.valideImageType(fileList[0])){
            alert("올바르지 않은 파일 타입입니다.(JPG, JPGE, PNG만 가능)");
            return;
        }

        let reader = new FileReader();
        reader.readAsDataURL(fileList[0]);
        reader.onload = function() {
            let item = this.getThumbnailElemnt();
            item.querySelector(".item_thumb").src = reader.result;
            this.imageThumbList.innerHTML += item.innerHTML;
        }.bind(this);
    },
    getThumbnailElemnt(){
        let item = ReviewWritePage.bindTemplate();

        let element = document.createElement("li");
        element.innerHTML += item;

        return element;
    },
    valideImageType(image) {
            const result = (["image/jpeg", "image/png", "image/jpg"].indexOf(image.type) > -1);
            return result
    }
};//end of ReviewWritePage.fileImageArea

ReviewWritePage.registerReview = {
    registerButton: document.querySelector(".box_bk_btn .bk_btn"),
    formElement: document.querySelector(".review_write_footer #reviewImageFileOpenInput"),
    reviewTextArea: document.querySelector(".review_textarea"),
    ratingCountSpan: document.querySelector(".rating .star_rank"),

    init(){
        this.registerEvents();
    },
    registerEvents(){
        this.registerButton.addEventListener("click", this.register.bind(this));
    },
    register(){
        if(this.isValid() === false){
            alert("입력이 올바르지 않습니다.(글자 수, 별점을 확인해 주세요)");
            return;
        }

        let formData = this.makeFormData();

        requestAjax({
            url: "./api/comments",
            method: "POST",
            data: formData,
            success: function(response) {
                alert("등록이 완료되었습니다.");
                window.history.back();
            }
        });
    },
    makeFormData(){
        let formData = new FormData();
        formData.append("imageFile", this.formElement.files[0]);
        formData.append("productId", document.querySelector("#productId").value);
        formData.append("reservationInfoId", document.querySelector("#reservationInfoId").value);
        formData.append("score", document.querySelector(".rating .star_rank").innerText);
        formData.append("comment", document.querySelector(".review_textarea").value);
        return formData;
    },
    isValid(){
        return !(this.reviewTextArea.textLength < 5 || this.ratingCountSpan.classList.contains("gray_star"));
    }
};//end of ReviewWritePage.registerReview

ReviewWritePage.bindTemplate = Handlebars.compile(document.querySelector("#thumbnail-template").innerText);

ReviewWritePage.initialize = function() {
    this.textArea.init();
    this.ratingArea.init();
    this.imageFileArea.init();
    this.registerReview.init();
}
