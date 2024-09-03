$(document).ready(function () {
    var usuarioId = $("#usuarioId").val();
    if (!usuarioId) {
        alert("No se ha definido el ID del usuario.");
        return;
    } else {
            console.log("id del usuario en sesion " + usuarioId);
        $.ajax({
            url: '/kenpis/usuario/cargar-usuarios',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({usuId: usuarioId}),
            success: function (response) {
                if (response.status === "success") {
                    const usuarios = response.data;
                    let tableBody = '';

                    usuarios.forEach(function (usuario, index) {
                        tableBody += '<tr>' +
                            '<th scope="row">' + (index + 1) + '</th>' +
                            '<td>' + (usuario.usuNumeroDocumento || '') + '</td>' +
                            '<td>' + (usuario.usuNombre || '') + ' ' + (usuario.usuApePaterno || '') + ' ' + (usuario.usuApeMaterno || '') + '</td>' +
                            '<td>' + (usuario.authRoles || '') + '</td>' +
                            '<td>' + (usuario.authUsername || '') + '</td>' +
                            '<td>' + (usuario.empresaNombre || '') + '</td>' +
                            '<td>' +
                            '<button class="btn btn-primary btn-sm">Acción</button>' +
                            '</td>' +
                            '</tr>';
                    });

                    $('#productoBody').html(tableBody);
                } else {
                    alert(response.message);
                }
            },
            error: function (error) {
                console.error("Error al cargar los usuarios:", error);
                alert("Ocurrió un error al cargar los usuarios.");
            }
        });
    }
});
