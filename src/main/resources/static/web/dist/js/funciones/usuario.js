$(document).ready(function () {

    var usuarioId = $("#usuarioId").val();
    var usuarioNivel = $("#usuarioNivel").val();

    if (!usuarioId) {
        alert("No se ha definido el ID del usuario.");
        return;
    } else {
        // Cargar usuarios según el nivel del usuario
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
                            '<td>' + (usuario.usuTipoDocumento || '') + ' - ' + (usuario.usuNumeroDocumento || '') + '</td>' +
                            '<td>' + (usuario.usuNombre || '') + ' ' + (usuario.usuApePaterno || '') + ' ' + (usuario.usuApeMaterno || '') + '</td>' +
                            '<td>' + (usuario.authRoles || '') + '</td>' +
                            '<td>' + (usuario.authUsername || '') + '</td>' +
                            '<td>' + (usuario.empNombreComercial || '') + '</td>' +
                            '<td>' +
                            '<button class="btn btn-primary btn-sm">Acción</button>' +
                            '</td>' +
                            '</tr>';
                    });

                    $('#usuariosBody').html(tableBody);

                    if (usuarioNivel === "ADMINISTRADOR" && response.empresasList) {
                        const empresas = response.empresasList;
                        let options = '';

                        empresas.forEach(function (empresa) {
                            options += '<option value="' + empresa.empId + '">' + empresa.empRazonSocial + '</option>';
                        });

                        $('#usuario_empresa').html(options);
                    }
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


    // para poder registrar un usuario desde Administrador

    $('#registrarUsuario').click(function (event) {
        event.preventDefault();

        $.ajax({
            url: '/kenpis/usuario/create',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(usuarioData),
            success: function (response) {
                if (response.status === "success") {
                    alert("Usuario creado exitosamente.");

                    $('#createUserForm')[0].reset();
                } else {
                    alert("Error: " + response.message);
                }
            },
            error: function (error) {
                console.error("Error al crear el usuario:", error);
                alert("Ocurrió un error al crear el usuario.");
            }
        });
    });


});
