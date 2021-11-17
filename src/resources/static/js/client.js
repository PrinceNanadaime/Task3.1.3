//Таблица для клиента
fetch("http://localhost:8080/currentClient")
    .then(res => res.json())
    .then(data => {
        document.getElementById('Id').innerText = data.id;
        document.getElementById('Username').innerText = data.username;

        document.getElementById('Surname').innerText = data.surname;
        document.getElementById('Age').innerText = data.age;
        document.getElementById('Email').innerText = data.email;
        document.getElementById('Password').innerText = data.password;

        let roles = '';
        data.roles.forEach(role => {
            roles += ' ' + role.name;
        })
        document.getElementById('Role').innerText = roles;
        document.getElementById('Role2').innerText = roles;
        document.getElementById('Username2').innerText = data.username;
    })