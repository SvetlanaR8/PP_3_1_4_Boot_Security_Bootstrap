$('document').ready(function () {
    $('.table .btn').on('click',function (event) {
        event.preventDefault();

        var href=$(this).attr('href');

        $.get(href,function(user){
            $('#id').val(user.id);
            $('#firstName').val(user.firstName);
            $('#lastName').val(user.lastName);
            $('#username').val(user.username);
            $('#password').val(user.password);
            $('#roles').val(user.roles);
        });

        $('#editModal').modal();
    });
});