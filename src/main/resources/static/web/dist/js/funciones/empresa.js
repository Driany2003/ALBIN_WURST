$(document).ready(function () {
        cargarEmpresas();

        function cargarEmpresas() {
            var usuarioId = $('#usuarioId').val();

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
                                    '<tr id="empresa-row-' + empresa.empId + '" class="empresa-row" data-id="' + empresa.empId + '">' +
                                    '<td>' +
                                    '<img src="' + (empresa.empImageLogo || 'path_to_default_logo.png') + '" alt="Logo" style="width: 50px; height: 50px; vertical-align: middle;"> ' +
                                    '<span>' + empresa.empNombreComercial + '</span>' +
                                    '</td>' +
                                    '<td>' +
                                    '<span class="text-primary">' + (empresa.empResponsable || 'Sin responsable') + '</span>' +
                                    '</td>' +
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
                                    '<span> </span>' +
                                    '<button type="button" data-id="' + (empresa.empId || '') + '" class="btn btn-sm btn-danger eliminarEmpresa" data-toggle="tooltip" data-placement="top" title="Eliminar Empresa">' +
                                    '<i class="fas fa-trash"></i>' +
                                    '</button>' +
                                    '<span> </span>' +
                                    '<button type="button" data-id="' + (empresa.empId || '') + '" class="btn btn-sm btn-info mostrarSucursales" data-toggle="tooltip" data-placement="top" title="Mostrar Sucursales">' +
                                    '<i class="fas fa-building"></i>' +
                                    '</button>' +
                                    '</td>' +
                                    '</tr>'
                                );
                            });

                            // Mover la asignación de eventos fuera del bucle forEach
                            $('.eliminarEmpresa').off('click').on('click', function (event) {
                                event.preventDefault();
                                var empId = $(this).data('id');
                                eliminarEmpresa(empId);
                            });

                            $('.editarEmpresa').off('click').on('click', function (event) {
                                event.preventDefault();
                                var empId = $(this).data('id');
                                editarEmpresa(empId);
                            });

                            $('.mostrarSucursales').off('click').on('click', function () {
                                var sucursalId = $(this).data('id');
                                cargarSucursales(sucursalId);
                            });
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


        function cargarSucursales(empId) {
            $('#btnAgregarSucursal').data('emp-id', empId);

            var empresaNombreComercial = $('#empresa-row-' + empId + ' td span').text();
            $('#sucursalesModalLabel').text('Sucursales de ' + empresaNombreComercial);
            $('#sucursalesBody').empty();
            $('#mensajeSinSucursales').hide();
            $('#sucursalesModal').modal('show');

            $.ajax({
                url: '/kenpis/empresas/find-sucursales/' + empId,
                type: 'GET',
                success: function (response) {
                    if (response.status === 'success') {
                        if (Array.isArray(response.data) && response.data.length > 0) {
                            response.data.forEach(function (sucursal) {
                                $('#sucursalesBody').append(
                                    '<tr>' +
                                    '<td>' +
                                    '<span style="font-weight: bold;">' + sucursal.empNombreComercial + '</span><br>' +
                                    '</td>' +
                                    '<td>' + (sucursal.empTelefono || 'N/A') + '</td>' +
                                    '<td>' +
                                    '<label class="switch">' +
                                    '<input type="checkbox" class="estado-checkbox" data-id="' + sucursal.empId + '" ' + (sucursal.empIsActive ? 'checked' : '') + '>' +
                                    '<span class="slider"></span>' +
                                    '</label>' +
                                    '</td>' +
                                    '<td>' +
                                    '<button type="button" class="btn btn-sm btn-warning editarSucursal" data-id="' + sucursal.empId + '">' +
                                    '<i class="fas fa-pencil-alt"></i>' +
                                    '</button> ' +
                                    '<span> </span>' +
                                    '<button type="button" class="btn btn-sm btn-danger eliminarSucursal" data-id="' + sucursal.empId + '">' +
                                    '<i class="fas fa-trash"></i>' +
                                    '</button>' +
                                    '</td>' +
                                    '</tr>'
                                );
                            });
                            $('.editarSucursal').click(function () {
                                var sucursalId = $(this).data('id');
                                editarSucursal(sucursalId);
                            });

                            $('.eliminarSucursal').click(function () {
                                var sucursalId = $(this).data('id');
                                eliminarSucursal(sucursalId);
                            });

                            $('#btnAgregarSucursal').show();
                        } else {
                            $('#mensajeSinSucursales').show();
                            $('#btnAgregarSucursal').show();
                        }
                    } else {
                        alert('Error al cargar las sucursales');
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
                empEmail: $('#empEmail').val(),
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
                    $('#editempFechaContratoInicio').data(empresa.empFechaContratoInicio);
                    $('#editempFechaContratoFin').data(empresa.empFechaContratoFin);

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

        //////////////////////////////////////////////////////////////7


        /* REGISTRAR Y EDITAR PARA UNA SUCURSAL */

        $('#btnAgregarSucursal').on('click', function () {
            $('#sucursalModal').modal('show');
        });

        $('#guardarSucursal').on('click', function () {
            var empId = $('#btnAgregarSucursal').data('emp-id');
            var sucursalData = {
                empNombreComercial: $('#sucNombre').val(),
                empTelefono: $('#sucTelefono').val(),
                empPadreId: parseInt(empId),

            };


            $.ajax({
                url: '/kenpis/empresas/sucursales-create',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(sucursalData),
                success: function (response) {
                    $('#sucursalModal').modal('hide');
                    toastr.success('Sucursal registrada con éxito.');
                    cargarSucursales(empId);
                },
                error: function (xhr, status, error) {
                    console.error('Error al guardar la sucursal:', error);
                    toastr.error('Hubo un error al guardar la sucursal. Intenta de nuevo.');
                }
            });
        });

        ////////////////////////////////////////////////////////////////////////////////////////


        function editarSucursal(sucursalId) {
            $.ajax({
                url: `/kenpis/empresas/find-by-id/${sucursalId}`,
                method: 'GET',
                success: function (sucursal) {
                    $('#editSucursalId').val(sucursal.empId);
                    $('#editSucursalNombreComercial').val(sucursal.empNombreComercial);
                    $('#editSucursalTelefono').val(sucursal.empTelefono);

                    $('#editsucursalModal').modal('show');
                },
                error: function () {
                    toastr.error('Error al obtener los detalles de la sucursal.');
                }
            });
        }


        $('#editFormularioSucursal').submit(function (event) {
            event.preventDefault();

            var sucursalData = {
                empId: $('#editSucursalId').val(),
                empNombreComercial: $('#editSucursalNombreComercial').val(),
                empTelefono: $('#editSucursalTelefono').val(),
            };

            $.ajax({
                url: `/kenpis/empresas/sucursal-update`,
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(sucursalData),
                success: function () {
                    cargarSucursales(sucursalData.empId);
                    $('#editsucursalModal').modal('hide');
                    $('#sucursalesModal').modal('hide');
                    toastr.success('Sucursal actualizada correctamente.');
                },
                error: function () {
                    toastr.error('Error al actualizar la sucursal. Intente nuevamente.');
                }
            });
        });

        function eliminarSucursal(empId) {
            if (confirm('¿Estás seguro de que deseas eliminar esta sucursal?')) {
                $.ajax({
                    url: `/kenpis/empresas/sucrusal-delete/${empId}`,
                    method: 'DELETE',
                    success: function () {
                        toastr.success('Sucursal eliminada correctamente.');
                        $('#sucursalesModal').modal('hide');
                        cargarEmpresas();
                    },
                    error: function () {
                        toastr.error('Error al eliminar la empresa. Intente nuevamente.');
                    }
                });
            }
        }

        /////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //EDITAR LA EMPRESA DE UN USUARIO CON ROL DE PROPIETARIO
        var formularioModificado = false;
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
    }
);

function formatDate(date) {
    var options = {year: 'numeric', month: '2-digit', day: '2-digit'};
    return new Date(date).toLocaleDateString('es-ES', options);
}