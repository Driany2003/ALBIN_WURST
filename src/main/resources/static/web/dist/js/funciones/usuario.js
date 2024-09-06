$(document).ready(function () {

    var usuarioId = $("#usuarioId").val();
    var usuarioNivel = $("#usuarioNivel").val();
    var usuarioEmpresaId = $("#empresaId").val();

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
                            '<button type="button" data-id="' + (usuario.usuId || '') + '" class="btn btn-sm btn-warning editarUsuario" data-toggle="tooltip" data-placement="top" title="Editar Usuario">' +
                            '<i class="fas fa-pencil-alt"></i>' +
                            '</button>' +
                            '<button type="button" data-id="' + (usuario.usuId || '') + '" class="btn btn-sm btn-danger eliminarUsuario" data-toggle="tooltip" data-placement="top" title="Eliminar Usuario">' +
                            '<i class="fas fa-trash"></i>' +
                            '</button>' +
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
                alert("Ocurrió un error al cargar los usuarios.");
            }
        });
    }


    // para poder registrar un usuario desde Administrador
    $('#registrarUsuario').on('click', function () {
        var empresaId = (usuarioNivel === "ADMINISTRADOR") ? $('#usuario_empresa').val() : usuarioEmpresaId;
        var generoSeleccionado = $('input[name="usuario_genero"]:checked').val();
        var usuarioData = {
            impId: empresaId,
            usuNombre: $('#usuario_nombre').val(),
            usuApePaterno: $('#usuario_apellido_paterno').val(),
            usuApeMaterno: $('#usuario_apellido_materno').val(),
            usuTelefono: $('#usuario_telefono').val(),
            usuGenero: generoSeleccionado,
            usuNumeroDocumento: $('#usuario_numero_documento').val(),
            usuTipoDocumento: $('#usuario_tipo_documento').val(),
            authRoles: $('#usuario_cargo').val(),
            authUsername: $('#username_usuario').val(),
            authPassword: $('#usuario_clave_1').val(),
        };

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
