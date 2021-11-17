/**
 *
 */

//Создание нового User
$(document).ready(function () {
    restart();
    $('.aBtn').on('click', function () {

        let user = {
            id: $('#id2').val(),
            username: $('#username2').val(),
            surname: $('#surname2').val(),
            age: $('#age2').val(),
            email: $('#email2').val(),
            password: $('#password2').val(),
            roles: getRole("#selectRole2")
        }
        console.log(user);
        fetch("api/newUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => restart());
        $('input').val('');
    });
});

//Получение роли
function getRole(address) {
    let select = $(address).val();
    console.log(select)
    let roles = [];
    for (let i = 0; i < select.length; i++) {
        roles.push({
            id: select[i] === 'USER' ? 1 : 2,
            name: select[i],
        });
    }
    return roles;

}

function restart() {
    let UserTableBody = $("#dataTable")
    UserTableBody.empty();


    fetch("api/users")
        .then((res) => {
            console.log(res)
            res.json().then(dataTable => dataTable.forEach(function (user) {
                let TableRow = tableRow(user);
                UserTableBody.append(TableRow);

            }));
        }).catch(error => {
        console.log(error);
    });
}

//Заполнение таблицы User
function tableRow(u) {
    let userRole = '';
    u.roles.forEach(role => {
        userRole += ' ' + role.name;
    })

    return `<tr>
                 <td>${u.username}</td>
                 <td>${u.surname}</td>
                 <td>${u.id}</td>
                 <td>${u.password}</td>
                 <td>${u.age}</td>
                 <td>${u.email}</td>
                 <td>${userRole}</td>
                 <td> <a href="/api/${u.id}" class="btn btn-danger delBtn">Delete</a> </td>
                 <td> <a href="/api/${u.id}" class="btn btn-primary eBtn">Edit</a> </td>
             </tr>`;
}

document.onclick = function (event) {

    //Заполнение модульного окна Edit
    if ($(event.target).hasClass('eBtn')) {
        event.preventDefault();
        let href = $(event.target).attr("href");

        $.get(href, function (user) {
            $('.myForm #id').attr('readonly','readonly').val(user.id);
            $('.myForm #username').val(user.username);
            $('.myForm #surname').val(user.surname);
            $('.myForm #age').val(user.age);
            $('.myForm #email').val(user.email);
            $('.myForm #password').val(user.password);

        });

        $('.myForm #exampleModal').modal();

    };

    //Заполнение модульного окна Delete
    if ($(event.target).hasClass('delBtn')) {
        event.preventDefault();
        let href = $(event.target).attr("href");

        $.get(href, function (user) {
            $('.myFormDelete #id1').attr('readonly','readonly').val(user.id);
            $('.myFormDelete #username1').attr('readonly','readonly').val(user.username);
            $('.myFormDelete #surname1').attr('readonly','readonly').val(user.surname);
            $('.myFormDelete #age1').attr('readonly','readonly').val(user.age);
            $('.myFormDelete #email1').attr('readonly','readonly').val(user.email);
            $('.myFormDelete #password1').attr('readonly','readonly').val(user.password);

        });
        $('.myFormDelete #exampleModalDelete').modal();
    };

    //Кнопка Edit
    if ($(event.target).hasClass('eddBtn')) {
        event.preventDefault();

        let user = {
            id: $('#id').val(),
            username: $('#username').val(),
            surname: $('#surname').val(),
            age: $('#age').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            roles: getRole("#selectRole")
        }
        editBut(user)
        console.log(user);
    }

    //Кнопка Delete
    if ($(event.target).hasClass('delBtn')) {
        event.preventDefault();
        let user = {
            id: $('#id1').val(),
            username: $('#username1').val(),
            surname: $('#surname1').val(),
            age: $('#age1').val(),
            email: $('#email1').val(),
            password: $('#password1').val(),
            roles: getRole("#selectRole1")
        }
        delModalButton(user)
        console.log(user);
    }
}

//Функция Edit
function editBut(user) {
    fetch("api/edit", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.myForm #exampleModal').modal('hide');
        restart();
    })
}

//Функция Delete
function delModalButton(user) {
    console.log(user.id)
    fetch(`api/delete/${user.id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('.myFormDelete #exampleModalDelete').modal('hide');
        restart();
    })
}

//Текущий юсер
fetch("http://localhost:8080/api/userNav")
    .then(res => res.json())
    .then(date => {

        let rol = '';
        date.roles.forEach(role => {
            rol += ' ' + role.name;
        })
        document.getElementById('Role').innerText = rol;
        document.getElementById('Username3').innerText = date.username;
})