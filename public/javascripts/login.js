const csrfToken = $("#csrfToken").val();
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();


$("#content").load(loginRoute);


function login() {
    const username = $("#username").val();
    const password = $("#password").val();

    $.post(validateRoute,
        { username, password, csrfToken },
        data => {
        console.log(data);
  });
}

