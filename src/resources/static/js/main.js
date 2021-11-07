/**
 *
 */

$(document).ready(function (){

    $('.table .eBtn').on('click', function (event){
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (user) {
            $('.myForm #id').attr('readonly','readonly').val(user.id);
            $('.myForm #name').val(user.name);
            $('.myForm #surname').val(user.surname);
            $('.myForm #age').val(user.age);
            $('.myForm #username').val(user.username);
            $('.myForm #password').val(user.password);

        });

        $('.myForm #exampleModal').modal();

    });

    $(' .table .dBtn').on('click', function (event){
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (user) {
            $('.myFormDelete #id1').attr('readonly','readonly').val(user.id);
            $('.myFormDelete #name1').attr('readonly','readonly').val(user.name);
            $('.myFormDelete #surname1').attr('readonly','readonly').val(user.surname);
            $('.myFormDelete #age1').attr('readonly','readonly').val(user.age);
            $('.myFormDelete #username1').attr('readonly','readonly').val(user.username);
            $('.myFormDelete #password1').attr('readonly','readonly').val(user.password);

        });

        $('.deleteForm #exampleModal').modal();

    });
});
