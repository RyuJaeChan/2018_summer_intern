function registerValidator(parameterObejct) {
    let checkValidation = (function() {
        switch (parameterObejct.inputType) {
            case "notEmpty":
                return function(value) {
                    return value.match(/.+/g);
                }
            case "tel":
                return function(value) {
                    return value.match(/^\d{3}-\d{3,4}-\d{4}$/);
                };
            case "email":
                return function(value) {
                    return value.match(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);
                };
        }
    })();

    parameterObejct.inputElement.onkeyup = function() {
        if (checkValidation(parameterObejct.inputElement.value)) {
            parameterObejct.correct();
        } else {
            parameterObejct.wrong();
        }
    };
}
