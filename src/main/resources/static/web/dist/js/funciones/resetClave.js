$(document).ready(function () {
    $('#validarClave').click(function () {
        var claveActual = $('#claveActual').val();
        var usuId = $('#usuId').val();

        if (claveActual.trim() === '') {
            toastr.error("Debe ingresar su clave actual.");
            return;
        }

        $.ajax({
            url: `/kenpis/usuario/validar-clave`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ usuId: usuId, claveActual: claveActual }),
            success: function (response) {
                if (response.valida === true) {
                    toastr.success("Clave actual válida. Ahora ingrese su nueva clave.");
                    $('#nuevaClaveSection').addClass('show').css('display', 'block');
                    //validar clave
                    $('#claveActual').prop('disabled', true);
                    $('#validarClave').prop('disabled', true);
                    //nueva clave

                    $('#nuevaClave').prop('disabled', false);
                    $('#guardarNuevaClave').prop('disabled', false);
                } else {
                    toastr.error("La clave actual es incorrecta.");
                    $('#claveActual').val('');
                }
            },
            error: function (xhr, status, error) {
                toastr.error("Ocurrió un error al validar la clave.");
            }
        });
    });

    $('#guardarNuevaClave').click(function () {
        var nuevaClave = $('#nuevaClave').val();
        var usuId = $('#usuId').val();

        if (nuevaClave.trim() === '') {
            toastr.error("Debe ingresar una nueva clave.");
            return;
        }

        $.ajax({
            url: `/kenpis/usuario/actualizar-clave`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({ usuId: usuId, nuevaClave: nuevaClave }),
            success: function (response) {
                toastr.success("Clave actualizada correctamente.");
                    $('#resetClaveModal').modal('hide');
                    resetForm();
            },
            error: function (xhr, status, error) {
                toastr.error("Ocurrió un error al actualizar la clave.");

            }
        });
    });
    function resetForm() {
        $('#resetClaveForm')[0].reset();
        $('#nuevaClaveSection').removeClass('show').css('display', 'none');
        $('#nuevaClave').prop('disabled', true);
        $('#guardarNuevaClave').prop('disabled', true);
        $('#claveActual').prop('disabled', false);
        $('#validarClave').prop('disabled', false);
        $('#nuevaClave').removeClass('is-invalid');
    }

    $('#resetClaveModal').on('hidden.bs.modal', function () {
        resetForm();
        $('.modal-backdrop').remove();
    });


});
