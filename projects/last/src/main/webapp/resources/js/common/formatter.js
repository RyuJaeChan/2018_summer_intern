Number.prototype.addCommas = function() {
    return this.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

Number.prototype.removeCommas = function() {
    return this.toString().replace(/[^\d]+/g, "");
}

String.prototype.addCommas = function() {
    return this.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

String.prototype.removeCommas = function() {
    return this.replace(/[^\d]+/g, "");
}
