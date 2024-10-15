$(document).ready(function () {
    function obtenerIniciales(nombre, apellido) {
        const primeraLetraNombre = nombre.charAt(0).toUpperCase();
        const primeraLetraApellido = apellido.charAt(0).toUpperCase();
        return primeraLetraNombre + primeraLetraApellido;
    }

    function generarImagenIniciales(nombre, apellido) {
        const canvas = document.getElementById('initialsCanvas');
        const ctx = canvas.getContext('2d');

        const scale = 2;

        canvas.width = 150 * scale;
        canvas.height = 150 * scale;

        canvas.style.width = '150px';
        canvas.style.height = '150px';

        ctx.scale(scale, scale);

        ctx.clearRect(0, 0, canvas.width, canvas.height);


        ctx.fillStyle = '#007bff';
        ctx.beginPath();
        ctx.arc(75, 75, 75, 0, Math.PI * 2, true);
        ctx.closePath();
        ctx.fill();


        ctx.fillStyle = '#ffffff';
        ctx.font = 'bold 50px Arial';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';


        const iniciales = obtenerIniciales(nombre, apellido);
        ctx.fillText(iniciales, 75, 75);
    }

    window.onload = function () {
        const nombre = $('#nombre').val();
        const apellido = $('#apellidoPaterno').val();
        generarImagenIniciales(nombre, apellido);
    };

//PARA ACTIVAR EL BOTON DE ACTUALIZAR ANTE UN CAMBIO EN LOS INPUTS

    $('#actualizarPerfil').prop('disabled', true);

    function validateFields() {
        let isValid = true;

        $('#nombre, #apellidoPaterno, #apellidoMaterno').on('input', function () {
            $(this).val($(this).val().toUpperCase());
        });

        // Verificar campos vacíos
        $('#nombre, #apellidoPaterno, #apellidoMaterno, #telefono, #correoUsuario').each(function () {
            if ($(this).val().trim() === '') {
                $(this).addClass('is-invalid');
                isValid = false;
            } else {
                $(this).removeClass('is-invalid');
            }
        });


        const email = $('#correoUsuario').val();
        if (!/\S+@\S+\.\S+/.test(email)) {
            $('#correoUsuario').addClass('is-invalid');
            isValid = false;
        } else {
            $('#correoUsuario').removeClass('is-invalid');
        }

        const phone = $('#telefono').val();
        if (phone.length !== 9) {
            $('#telefono').addClass('is-invalid');
            isValid = false;
        } else {
            $('#telefono').removeClass('is-invalid');
        }

        return isValid;
    }

    $('#nombre, #apellidoPaterno, #apellidoMaterno').on('input change', function () {
        const nombre = $('#nombre').val();
        const apellido = $('#apellidoPaterno').val();

        generarImagenIniciales(nombre, apellido);
        // Validar campos
        if (validateFields()) {
            $('#actualizarPerfil').prop('disabled', false);
        } else {
            $('#actualizarPerfil').prop('disabled', true);
        }
    });

    $('#ActualizarPerfilForm input').on('input change', function () {

        if (validateFields()) {
            $('#actualizarPerfil').prop('disabled', false);
        } else {
            $('#actualizarPerfil').prop('disabled', true);
        }
    });


    $('#actualizarPerfil').click(function (event) {
        event.preventDefault();
        var miPerfilData = {
            usuId: $('#usuId').val(),
            empId: $('#empId').val(),
            usuNombre: $('#nombre').val(),
            usuApePaterno: $('#apellidoPaterno').val(),
            usuApeMaterno: $('#apellidoMaterno').val(),
            usuTelefono: $('#telefono').val(),
            usuCorreo: $('#correoUsuario').val(),
            usuTipoDocumento: $('#tipoDocumento').val(),
            usuNumeroDocumento: $('#numeroDocumento').val()
        };

        $.ajax({
            url: `/kenpis/usuario/actualizar-perfil`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(miPerfilData),
            success: function (response) {
                toastr.success("Perfil actualizado correctamente.");
                location.reload();
            },
            error: function (xhr, status, error) {
                console.log(xhr.responseText);
                toastr.error("Ocurrió un error al actualizar el perfil.");
            }
        });
    });


});