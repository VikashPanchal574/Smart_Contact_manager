document.querySelector("#image_flie_input")
    .addEventListener("change", function (event) {
        var file = event.target.files[0];
        var reader = new FileReader();
        reader.onload = function () {
            document
                .querySelector("#image_preview")
                .setAttribute("src", reader.result);
        };

        reader.readAsDataURL(file);

    });

