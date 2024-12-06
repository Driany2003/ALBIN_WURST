$(document).ready(function () {
    var usuarioEmpresaId = $("#empresaIdModal").val();
    var usuarioNivel = $("#usuarioNivel").val();
    cargarUsuarios();

    function cargarUsuarios() {
        var usuarioId = $("#usuarioId").val();

        if (!usuarioId) {
            alert("No se ha definido el ID del usuario.");
            return;
        } else {
            $.ajax({
                url: '/kenpis/usuario/cargar-usuarios',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({usuId: usuarioId}),
                success: function (response) {
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

                    $('.eliminarUsuario').click(function (event) {
                        event.preventDefault();
                        var usuId = $(this).data('id');
                        console.log("ID DEL USUARIO A ELIMINAR SELECCIONADO", usuId);
                        eliminarUsuario(usuId);
                    });
                    $('.editarUsuario').click(function (event) {
                        event.preventDefault();
                        var usuId = $(this).data('id');
                        console.log("ID DEL USUARIO SELECCIONADO", usuId);
                        editarUsuario(usuId);
                    });
                    if (usuarioNivel === "ADMINISTRADOR" && response.empresasList) {
                        listarEmpresasAdmin(response.empresasList, 'usuario_empresa');
                    }
                },
                error: function (error) {
                    alert("Ocurrió un error al cargar los usuarios.");
                }
            });
        }

    }

    function listarEmpresasAdmin(empresas, selectElementId) {
        let options = '';
        empresas.forEach(function (empresa) {
            options += '<option value="' + empresa.empId + '">' + empresa.empNombreComercial + '</option>';
        });
        $('#' + selectElementId).html(options);
    }


    // para poder registrar un usuario desde Administrador
    $('#registrarUsuario').on('click', function () {
        var empresaId = (usuarioNivel === "ADMINISTRADOR") ? $('#usuario_empresa').val() : usuarioEmpresaId;
        var generoSeleccionado = $('input[name="usuario_genero"]:checked').val();
        var usuarioData = {
            empresaId: empresaId,
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
                alert("Usuario creado exitosamente.");
                $('#crearUsuarioModal').modal('hide');
                cargarUsuarios();
            },
            error: function (error) {
                console.error("Error al crear el usuario:", error);
                alert("Ocurrió un error al crear el usuario.");
            }
        });
    });


    //Funcion para editar los Usuarios
    function editarUsuario(usuId) {
        $.ajax({
            url: `/kenpis/usuario/find-by-id/${usuId}`,
            method: 'GET',
            success: function (response) {
                let usuario = response.usuario;
                console.log("Datos del usuario recuperados:", usuario);

                $("#usuarioId").val(usuario.usuId);
                $('#edit_usuario_nombre').val(usuario.usuNombre);
                $('#edit_usuario_apellido_paterno').val(usuario.usuApePaterno);
                $('#edit_usuario_apellido_materno').val(usuario.usuApeMaterno);
                $('#edit_usuario_telefono').val(usuario.usuTelefono);
                $('input[name="edit_usuario_genero"][value="' + usuario.usuGenero + '"]').prop('checked', true);
                $('#edit_usuario_tipo_documento').val(usuario.usuTipoDocumento);
                $('#edit_usuario_numero_documento').val(usuario.usuNumeroDocumento);
                $('#edit_usuario_cargo').val(usuario.authRoles);
                $('#edit_username_usuario').val(usuario.authUsername);
                $('#edit_usuario_clave_1').val(usuario.authPassword || '');

                if (usuarioNivel === "ADMINISTRADOR" && response.empresasList) {
                    listarEmpresasAdmin(response.empresasList, 'edit_usuario_empresa');
                    $('#edit_usuario_empresa').val(usuario.empresaId);
                } else if (usuarioNivel === "PROPIETARIO")
                    $('#edit_usuario_empresa').val(usuario.empresaId);


                $('#editUsuarioModal').modal('show');
            },
            error: function () {
                toastr.error('Error al obtener los detalles del Usuario.');
            }
        });
    }


    $('#editarUsuarioForm').submit(function (event) {
        event.preventDefault();
        var usuarioData = {
            usuId: $('#usuarioId').val(),
            empresaId: $('#edit_usuario_empresa').val(),
            usuNombre: $('#edit_usuario_nombre').val(),
            usuApePaterno: $('#edit_usuario_apellido_paterno').val(),
            usuApeMaterno: $('#edit_usuario_apellido_materno').val(),
            usuTelefono: $('#edit_usuario_telefono').val(),
            usuGenero: $('input[name="edit_usuario_genero"]:checked').val(),
            usuTipoDocumento: $('#edit_usuario_tipo_documento').val(),
            usuNumeroDocumento: $('#edit_usuario_numero_documento').val(),
            authRoles: $('#edit_usuario_cargo').val(),
            authUsername: $('#edit_username_usuario').val(),
            authPassword: $('#edit_usuario_clave_1').val()
        };

        $.ajax({
            url: `/kenpis/usuario/update`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(usuarioData),
            success: function () {
                $('#editUsuarioModal').modal('hide');
                toastr.success('Usuario actualizado correctamente.');
                cargarUsuarios();

            },
            error: function () {
                toastr.error('Error al actualizar el Usuario. Intente nuevamente.');
            }
        });
    });

    function eliminarUsuario(usuId) {
        if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
            $.ajax({
                url: `/kenpis/usuario/delete/${usuId}`,
                method: 'DELETE',
                success: function () {
                    toastr.success('Usuario eliminada correctamente.');
                    cargarUsuarios();
                },
                error: function () {
                    toastr.error('Error al eliminar el Usuario. Intente nuevamente.');
                }
            });
        }
    }

});
