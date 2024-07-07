console.log('productSale.js Join Success');

// 가격 입력 input 태그의 값이 비어있는 것을 허용하지 않기
document.getElementById('createProductValue').addEventListener('input', function() {
    let inputValue = this.value;
    if(inputValue.trim() === ''){
        document.getElementById('createProduct').disabled = true;
        document.getElementById('priceSpan').hidden = false;
    }else{
        document.getElementById('createProduct').disabled = false;
        document.getElementById('priceSpan').hidden = true;
    }
});

document.addEventListener('DOMContentLoaded', function() {
    let categoryRadios = document.querySelectorAll('input[type="radio"][name="category"]');

    categoryRadios.forEach(function(radio) {
        radio.addEventListener('change', function() {
            let isFreeSelected = document.getElementById('category7').checked;
            document.getElementById('createProductValue').value = 0;
            document.getElementById('createProductValue').disabled = isFreeSelected;
            document.getElementById('priceSpan').hidden = true;
        });
    });

    const fileInput = document.getElementById('files1');
    const submitButton = document.getElementById('createProduct');

    function checkFileInput() {
        if (fileInput.files.length > 0) {
            submitButton.disabled = false;
        } else {
            submitButton.disabled = true;
        };
    };

    fileInput.addEventListener('change', checkFileInput);
});