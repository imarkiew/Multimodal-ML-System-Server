const csrfToken = $("#csrfToken").val();
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();
const cockpitRoute = $("#cockpitRoute").val();
const skinLesionsFormRoute = $("#skinLesionsFormRoute").val();
const skinLesionsRoute = $("#skinLesionsRoute").val();


$("#content").load(loginRoute);


login = () => {

    const username = $("#username").val();
    const password = $("#password").val();

    $.ajaxSetup({
        beforeSend: xhr => {
            xhr.setRequestHeader('Csrf-Token', csrfToken);
        }
    });

    $.ajax({
        url: validateRoute,
        type: 'POST',
        data: JSON.stringify({ username, password }),
        contentType: 'application/json; charset=utf-8',
        dataType: 'html',
        async: true,
        success: data => {
            $("#content").html(data);
        }
    })
};

$(document).on("change", "#examinationsDropdown", () => {

    const selectedAction = $("#examinationsDropdown option:selected").text();

    if(selectedAction === "skin-lesions") {
        $.ajax({
            url: skinLesionsFormRoute,
            type: 'GET',
            dataType: 'html',
            async: true,
            success: data => {
                $("#content").html(data);
            }
        })
    } else {
        console.log("Not implemented !");
    }
});

$(document).on("change", "#skinLesionFile", function(){
    if($("#skinLesionFile").val() === ''){
        $("#ok-button").prop("disabled", true).trigger("change");
    }
    else{
        $("#ok-button").prop("disabled", false).trigger("change");
    }
});

returnToCockpit = () => {
    $.ajax({
        url: cockpitRoute,
        type: 'GET',
        dataType: 'html',
        async: true,
        success: data => {
            $("#content").html(data);
        }
    })
};

performSkinLesions = () => {

    const title = $("#skinLesionTitle").val();
    const date = new Date();
    const image = $("#skinLesionFile")[0].files[0];
    const fileName = image.name;
    const reader = new FileReader();
    reader.readAsDataURL(image);

    reader.onloadend = function() {

        const content = reader.result;

        $.ajaxSetup({
            beforeSend: xhr => {
                xhr.setRequestHeader('Csrf-Token', csrfToken);
            }
        });

        $.ajax({
            type: 'POST',
            url: skinLesionsRoute,
            data: JSON.stringify({title, date, fileName, content}),
            contentType: 'application/json; charset=utf-8',
            dataType: 'html',
            async: true,
            success: (data) => {
                $("#content").html(data);
            }
        })
    };
};
