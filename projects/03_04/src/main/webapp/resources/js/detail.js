let detail = {
  imageArea: {
    maxCount: document.querySelector(".off span"),
    currIndex: document.querySelector(".num"),
    prevButton: document.querySelector(".btn_nxt"),
    nextButton: document.querySelector(".btn_prev"),
    itemTemplate: document.querySelector("#itemList"),
    slider: {
      parentUl: document.querySelector(".visual_img"),
      currDirection: "base",
      base: "translateX(0px)",
      left: "translateX(-414px)",
      moveLeft: function() {
        this.currDirection = "left"
        this.parentUl.style.transition = "1s";
        this.parentUl.style.transform = this.left;
      },
      moveRight: function() {
        this.currDirection = "right"

        this.parentUl.style.transition = "0s";
        this.parentUl.insertAdjacentElement("afterbegin", this.parentUl.lastElementChild);
        this.parentUl.style.transform = this.left;

        setTimeout(function(){
          this.parentUl.style.transition = "1s";
          this.parentUl.style.transform = this.base;
        }.bind(this), 0);

      },
      addEventListener: function() {
        this.parentUl.addEventListener("transitionend", this.sendToBack.bind(this));
      },
      sendToBack: function() {
        let parentUl = this.slider.parentUl;

        parentUl.style.transition = "0s";
        parentUl.style.transform = this.slider.base;

        if (this.slider.currDirection === "left") {
          parentUl.insertAdjacentElement("beforeend", parentUl.firstElementChild);
          this.setCurrIndexNum(1);
        } else if (this.slider.currDirection === "right") {
          this.setCurrIndexNum(-1);
        }
      }
    },
    setCurrIndexNum: function(num) {
      let res = parseInt(this.currIndex.innerText) + num;
      if (res < 1) {
        res = parseInt(this.maxCount.innerText);
      } else if (res > this.maxCount.innerText) {
        res = 1;
      }
      this.currIndex.innerText = res;
    },
    getProductId : function(){
      let urlString = window.location.href;
      let url = new URL(urlString);

      return url.searchParams.get("id");
    },
    loadImage: function() {
      let productId = this.getProductId();
      if(productId === null){
        return;
      }

      requestAjax("./api/productImages?type=et&id=" + productId, function(resultJson) {
        if (resultJson.size === 0) {
          this.prevButton.style.display = "none";
          this.nextButton.style.display = "none";
          return;
        }

        this.maxCount.innerText = resultJson.size + 1;

        let bindTemplate = Handlebars.compile(this.itemTemplate.innerText);
        this.slider.parentUl.innerHTML += bindTemplate(resultJson);
      }.bind(this));
    },
    setIndexNumber: function(number) {
      this.maxCount.innerHTML = number;
    },
    addEventListener: function() {
      this.slider.parentUl.addEventListener("transitionend", this.slider.sendToBack.bind(this));
      this.nextButton.addEventListener("click", this.slider.moveLeft.bind(this.slider));
      this.prevButton.addEventListener("click", this.slider.moveRight.bind(this.slider));
    }
  },
  descriptionArea: {
    descriptionText: document.querySelector(".store_details"),
    openButton: document.querySelectorAll(".bk_more")[0],
    closeButton: document.querySelectorAll(".bk_more")[1],
    openDescription: function() {
      this.descriptionText.classList.remove("close3");
      this.openButton.style.display = "none";
      this.closeButton.style.display = "block";
    },
    closeDescription: function() {
      this.descriptionText.classList.add("close3");
      this.openButton.style.display = "block";
      this.closeButton.style.display = "none";
    },
    addEventListener: function() {
      this.openButton.addEventListener("click", this.openDescription.bind(this));
      this.closeButton.addEventListener("click", this.closeDescription.bind(this));
    }
  },
  detailArea: {
    detailTab: document.querySelectorAll(".anchor")[0],
    pathTab: document.querySelectorAll(".anchor")[1],
    detailDiv: document.querySelector(".detail_area_wrap"),
    pathDiv: document.querySelector(".detail_location"),
    showDetail: function() {
      this.detailTab.classList.add("active");
      this.detailDiv.classList.remove("hide");
      this.pathTab.classList.remove("active");
      this.pathDiv.classList.add("hide");
    },
    showPath: function() {
      this.detailTab.classList.remove("active");
      this.detailDiv.classList.add("hide");
      this.pathTab.classList.add("active");
      this.pathDiv.classList.remove("hide");
    },
    addEventListener: function() {
      this.detailTab.addEventListener("click", this.showDetail.bind(this));
      this.pathTab.addEventListener("click", this.showPath.bind(this));
    }
  },
  initialize: function() {
    this.imageArea.addEventListener();
    this.imageArea.loadImage();

    this.descriptionArea.addEventListener();
    this.detailArea.addEventListener();
  }
};
