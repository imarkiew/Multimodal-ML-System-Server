const csrfToken = $("#csrfToken").val();
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();


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
