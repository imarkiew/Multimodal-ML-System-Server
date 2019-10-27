const csrfToken = $("#csrfToken").val();
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();
const cockpitRoute = $("#cockpitRoute").val();
const skinLesionsFormRoute = $("#skinLesionsFormRoute").val();
const skinLesionsRoute = $("#skinLesionsRoute").val();
const breastCancerFormRoute = $("#breastCancerFormRoute").val();
const breastCancerRoute = $("#breastCancerRoute").val();


$("#content").load(loginRoute);

var selectedAction = null;

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

    selectedAction = $("#examinationsDropdown option:selected").text();

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
    } else if(selectedAction === "breast-cancer") {
        $.ajax({
            url: breastCancerFormRoute,
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

$(document).on("change", "#file", () => {
    if($("#file").val() === ''){
        $("#okButton").prop("disabled", true).trigger("change");
    }
    else{
        $("#okButton").prop("disabled", false).trigger("change");
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

performExamination = () => {

    $("#okButton").prop("disabled", true).trigger("change");
    $("#cancelButton").prop("disabled", true).trigger("change");
    $("#processingInfo").show();

    const title = $("#title").val();
    const date = new Date();
    const image = $("#file")[0].files[0];
    const fileName = image.name;
    const reader = new FileReader();
    reader.readAsDataURL(image);
    var urlRout = null;

    if(selectedAction === "skin-lesions"){
        urlRout = skinLesionsRoute;
    } else if (selectedAction === "breast-cancer") {
        urlRout = breastCancerRoute;
    }

    reader.onloadend = () => {

        const content = reader.result;

        $.ajaxSetup({
            beforeSend: xhr => {
                xhr.setRequestHeader('Csrf-Token', csrfToken);
            }
        });

        $.ajax({
            type: 'POST',
            url: urlRout,
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
