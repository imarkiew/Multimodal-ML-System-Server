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

// Adapted example from https://www.w3schools.com/howto/howto_js_dropdown.asp
// When the user clicks on the button, toggle between hiding and showing the dropdown content
examinationsDropdownFunction = () => {
    document.getElementById("examinationsDropdown").classList.toggle("show");
};

// Close the dropdown menu if the user clicks outside of it
window.onclick = event => {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
};
