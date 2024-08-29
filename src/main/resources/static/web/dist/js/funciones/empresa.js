$(document).ready(function () {
    cargarEmpresas();

    function cargarEmpresas() {
        var usuarioId = $('#usuarioId').val();

        console.log("id" + usuarioId);

        $.ajax({
            url: '/kenpis/empresas/cargar-empresas',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({usuId: usuarioId}),
            success: function (response) {
                if (response.status === 'success') {
                    if (Array.isArray(response.data)) {
                        var tableBody = $('#empresaBody');
                        tableBody.empty();
                        response.data.forEach(function (empresa) {
                            tableBody.append(
                                '<tr id="empresa-row-' + empresa.empId + '">' +
                                '<td>' + empresa.empId + '</td>' +
                                '<td><img src="' + (empresa.empImageLogo || 'path_to_default_logo.png') + '" alt="Logo" style="width: 50px; height: 50px;"></td>' +
                                '<td>' + empresa.empRazonSocial + '</td>' +
                                '<td>' + formatDate(empresa.empFechaContratoInicio) + '</td>' +
                                '<td>' + formatDate(empresa.empFechaContratoFin) + '</td>' +
                                '<td>' + (empresa.empTelefono || '') + '</td>' +
                                '<td>' +
                                '<label class="switch">' +
                                '<input type="checkbox" class="estado-checkbox" data-id="' + empresa.empId + '" ' + (empresa.empIsActive ? 'checked' : '') + '>' +
                                '<span class="slider"></span>' +
                                '</label>' +
                                '</td>' +
                                '<td>' +
                                '<button type="button" data-id="' + (empresa.empId || '') + '" class="btn btn-sm btn-warning editarEmpresa" data-toggle="tooltip" data-placement="top" title="Editar Empresa">' +
                                '<i class="fas fa-pencil-alt"></i>' +
                                '</button>' +
                                '<button type="button" data-id="' + (empresa.empId || '') + '" class="btn btn-sm btn-danger eliminarEmpresa" data-toggle="tooltip" data-placement="top" title="Eliminar Empresa">' +
                                '<i class="fas fa-trash"></i>' +
                                '</button>' +
                                '</td>' +
                                '</tr>'
                            );
                        });

                        $('.eliminarEmpresa').click(function (event) {
                            event.preventDefault();
                            var empId = $(this).data('id');
                            eliminarEmpresa(empId);
                        });
                        $('.editarEmpresa').click(function (event) {
                            event.preventDefault();
                            var empId = $(this).data('id');
                            editarEmpresa(empId);
                        });

                    } else {
                        $('#empresaLogo').attr('src', response.data.empImageLogo || '/static/web/assets/images/0_logo/logo_kenpis_img.png');
                        $('#empresaNombre').val(response.data.empNombreComercial);
                        $('#empresaRazonSocial').val(response.data.empRazonSocial);
                        $('#empresaTipoDocumento').val(response.data.empDocumentoTipo);
                        $('#empresaNumeroDocumento').val(response.data.empDocumentoNumero);
                        $('#empresaTelefono').val(response.data.empTelefono);
                        $('#empresaEmail').val(response.data.empEmail);

                    }
                } else {
                    alert(response.message || 'Error al cargar los datos');
                }
            },
            error: function () {
                alert('Error en la solicitud');
            }
        });
    }

    //para crear una empresa
    $('#guardarEmpresa').on('click', function () {
        var empresaData = {
            empDocumentoTipo: $('#empDocumentoTipo').val(),
            empDocumentoNumero: $('#empDocumentoNumero').val(),
            empRazonSocial: $('#empRazonSocial').val(),
            empResponsable: $('#empResponsable').val(),
            empTelefono: $('#empTelefono').val(),
            empImageLogo: $('#empImageLogo').val(),
            empNombreComercial: $('#empNombreComercial').val(),
            empFechaContratoInicio: $('#empFechaContratoInicio').val(),
            empFechaContratoFin: $('#empFechaContratoFin').val(),
            empFechaCreacion: $('#empFechaCreacion').val(),
            empQrYape: $('#empQrYape').val(),
            empQrPlin: $('#empQrPlin').val(),
            empQrPagos: $('#empQrPagos').val(),
            empEmail: $('#empEmail').val()
        };

        $.ajax({
            url: '/kenpis/empresas/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(empresaData),
            success: function (response) {
                $('#empresaModal').modal('hide');
                toastr.success('Empresa registrada con exito.');
                cargarEmpresas();

            },
            error: function (xhr, status, error) {
                console.error('Error al guardar la empresa:', error);
                toastr.error('Hubo un error al guardar la empresa. Intenta de nuevo.');
            }
        });
    });

    // Función para cargar los datos de la empresa en el modal de edición
    function editarEmpresa(empId) {
        var imgContainer = $('#editImgContainer');
        $.ajax({
            url: `/kenpis/empresas/find-by-id/${empId}`,
            method: 'GET',
            success: function (empresa) {
                $('#editempresaId').val(empresa.empId);
                $('#editempDocumentoTipo').val(empresa.empDocumentoTipo);
                $('#editempDocumentoNumero').val(empresa.empDocumentoNumero);
                $('#editempRazonSocial').val(empresa.empRazonSocial);
                $('#editempResponsable').val(empresa.empResponsable);
                $('#editempTelefono').val(empresa.empTelefono);
                $('#editempEmail').val(empresa.empEmail);
                $('#editempNombreComercial').val(empresa.empNombreComercial);
                $('#editempFechaContratoInicio').val(empresa.empFechaContratoInicio);
                $('#editempFechaContratoFin').val(empresa.empFechaContratoFin);
                /*
                                $('#editImgContainer').html('');
                                if (empresa.empImageLogo) {
                                    var logoImg = '<img src="data:image/png;base64,' + empresa.empImageLogo + '" height="100" width="100" />';
                                    $('#editImgContainer').append('<div><strong>Logo:</strong></div>' + logoImg);
                                }
                                if (empresa.empQrYape) {
                                    var qrYapeImg = '<img src="data:image/png;base64,' + empresa.empQrYape + '" height="100" width="100" />';
                                    $('#editImgContainer').append('<div><strong>QR Yape:</strong></div>' + qrYapeImg);
                                }
                                if (empresa.empQrPlin) {
                                    var qrPlinImg = '<img src="data:image/png;base64,' + empresa.empQrPlin + '" height="100" width="100" />';
                                    $('#editImgContainer').append('<div><strong>QR Plin:</strong></div>' + qrPlinImg);
                                }
                                if (empresa.empQrPagos) {
                                    var qrPagosImg = '<img src="data:image/png;base64,' + empresa.empQrPagos + '" height="100" width="100" />';
                                    $('#editImgContainer').append('<div><strong>QR Pagos:</strong></div>' + qrPagosImg);
                                }

                 */

                $('#editempresaModal').modal('show');
            },
            error: function () {
                toastr.error('Error al obtener los detalles de la empresa.');
            }
        });
    }

    $('#editEmpresaFormulario').submit(function (event) {
        event.preventDefault();

        var empresaData = {
            empId: $('#editempresaId').val(),
            empDocumentoTipo: $('#editempDocumentoTipo').val(),
            empDocumentoNumero: $('#editempDocumentoNumero').val(),
            empRazonSocial: $('#editempRazonSocial').val(),
            empResponsable: $('#editempResponsable').val(),
            empTelefono: $('#editempTelefono').val(),
            empEmail: $('#editempEmail').val(),
            empNombreComercial: $('#editempNombreComercial').val(),
            empFechaContratoInicio: $('#editempFechaContratoInicio').val(),
            empFechaContratoFin: $('#editempFechaContratoFin').val(),
            empIsActive: true
        };

        $.ajax({
            url: `/kenpis/empresas/update`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(empresaData),
            success: function () {
                $('#editempresaModal').modal('hide');
                toastr.success('Empresa actualizada correctamente.');
                cargarEmpresas();
            },
            error: function () {
                toastr.error('Error al actualizar la empresa. Intente nuevamente.');
            }
        });

    });

    //EDITAR LA EMPRESA DE UN USUARIO CON ROL DE PROPIETARIO
    var formularioModificado = false;

    // Función para habilitar el botón de actualización cuando se detecten cambios
    $('#empresaForm input, #empresaForm select').on('input change', function () {
        formularioModificado = true;
        $('#botonActualizar').prop('disabled', false);
    });
    $('#submitButton').prop('disabled', true);

    $('#empresaForm').on('submit', function (event) {
        event.preventDefault();

        if (formularioModificado) {
            $('#confirmModal').modal('show');
        } else {
            toastr.info('No se realizaron cambios en el formulario.');
        }
    });

    $('#confirmUpdate').on('click', function () {
        $.ajax({
            url: '/kenpis/empresas/update-propietario',
            method: 'PUT',
            data: $('#empresaForm').serialize(),
            success: function (response) {
                if (response.status === 'success') {
                    toastr.success('Empresa actualizada correctamente.');
                    formularioModificado = false;
                    $('#confirmModal').modal('hide');
                } else {
                    toastr.error('Error al actualizar la empresa.');
                }
            },
            error: function () {
                toastr.error('Error en la solicitud.');
            }
        });
    });

    $('#cancelUpdate').on('click', function () {
        $('#confirmModal').modal('hide');
    });

    // Función para eliminar una empresa
    function eliminarEmpresa(empId) {
        if (confirm('¿Estás seguro de que deseas eliminar esta empresa?')) {
            $.ajax({
                url: `/kenpis/empresas/delete/${empId}`,
                method: 'DELETE',
                success: function () {
                    toastr.success('Empresa eliminada correctamente.');
                    cargarEmpresas();
                    // $(`#empresa-row-${empId}`).remove(); para no recargar la pagina.
                },
                error: function () {
                    toastr.error('Error al eliminar la empresa. Intente nuevamente.');
                }
            });
        }
    }


    $(document).on('change', '.estado-checkbox', function () {
        var checkbox = $(this);
        var empresaId = checkbox.data('id');
        var isActive = checkbox.is(':checked');
        $.ajax({
            url: '/kenpis/empresas/update/status',
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                empId: empresaId,
                empIsActive: isActive
            }),
            success: function () {
                toastr.success('Estado de la Empresa actualizado correctamente.');
            },
            error: function () {
                toastr.error('Error al actualizar el estado de la Empresa. Intente nuevamente.');
            }
        });
    });

    $('#tableFilter').on('keyup', function () {
        var term = $(this).val().toLowerCase();
        $('#empresaTable tbody tr').each(function () {
            var found = false;
            $(this).find('td').each(function () {
                if ($(this).text().toLowerCase().indexOf(term) !== -1) {
                    found = true;
                    return false;
                }
            });
            $(this).toggle(found);
        });
    });

});

function formatDate(date) {
    var options = {year: 'numeric', month: '2-digit', day: '2-digit'};
    return new Date(date).toLocaleDateString('es-ES', options);
}