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
                            var responsableTelefono = (empresa.empResponsable || 'Sin responsable') + ' - ' + (empresa.empTelefono || 'Sin teléfono');


                            tableBody.append(
                                '<tr id="empresa-row-' + empresa.empId + '" class="empresa-row" data-id="' + empresa.empId + '">' +
                                '<td>' +
                                '<img src="' + (empresa.empImageLogo || 'path_to_default_logo.png') + '" alt="Logo" style="width: 50px; height: 50px; vertical-align: middle;"> ' +
                                '<span>' + empresa.empNombreComercial + '</span>' +
                                '</td>' +
                                '<td>' +
                                '<span class="text">' + responsableTelefono + '</span>' +
                                '</td>' +
                                '<td>' +
                                '<div class="contrato-fechas">' +
                                '<div><i class="fas fa-calendar-alt"></i> <strong>Inicio:</strong> ' + formatDate(empresa.empFechaContratoInicio) + '</div>' +
                                '<div><i class="fas fa-calendar-check"></i> <strong>Fin:</strong> ' + formatDate(empresa.empFechaContratoFin) + '</div>' +
                                '</div>' +
                                '</td>' +
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
                                '<span> </span>' +
                                '<button type="button" data-id="' + (empresa.empId || '') + '" class="btn btn-sm btn-danger mostrarMetodosPago" data-toggle="tooltip" data-placement="top" title="Mostrar Metodos de pago">' +
                                '<i class="fas fa-credit-card"></i>' +
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

                        $('.mostrarMetodosPago').off('click').on('click', function () {
                            var empId = $(this).data('id');
                            cargarMetodoPago(empId);
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

// Validación de correo electrónico
    function validarEmail() {
        const email = $('#empEmail').val().trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Expresión regular simple para validar el email
        if (!emailRegex.test(email)) {
            $('#empEmail').addClass('is-invalid');
            $('#emailError').remove();
            $('#empEmail').after('<div id="emailError" class="text-danger">El correo electrónico no es válido.</div>');
            return false;
        } else {
            $('#empEmail').removeClass('is-invalid');
            $('#emailError').remove();
            return true;
        }
    }

// Validación de teléfono
    function validarTelefono() {
        const phone = $('#empTelefono').val();
        if (phone.length !== 9 || isNaN(phone)) {
            $('#empTelefono').addClass('is-invalid');
            $('#telefonoError').remove();
            $('#empTelefono').after('<div id="telefonoError" class="text-danger">El teléfono debe tener 9 dígitos y ser numérico.</div>');
            return false;
        } else {
            $('#empTelefono').removeClass('is-invalid');
            $('#telefonoError').remove();
            return true;
        }
    }

// Validación de documento
    function validarDocumento() {
        const tipoDocumento = $('#empDocumentoTipo').val();
        const documentoNumero = $('#empDocumentoNumero').val().trim();

        if (tipoDocumento === 'RUC' && (documentoNumero.length !== 11 || isNaN(documentoNumero))) {
            $('#empDocumentoNumero').addClass('is-invalid');
            $('#documentoError').remove();
            $('#empDocumentoNumero').after('<div id="documentoError" class="text-danger">El RUC debe tener 11 dígitos y ser numérico.</div>');
            return false;
        } else if (tipoDocumento === 'DNI' && (documentoNumero.length !== 8 || isNaN(documentoNumero))) {
            $('#empDocumentoNumero').addClass('is-invalid');
            $('#documentoError').remove();
            $('#empDocumentoNumero').after('<div id="documentoError" class="text-danger">El DNI debe tener 8 dígitos y ser numérico.</div>');
            return false;
        } else {
            $('#empDocumentoNumero').removeClass('is-invalid');
            $('#documentoError').remove();
            return true;
        }
    }

// Validación de fechas
    function validarFechas() {
        const fechaInicio = $('#empFechaContratoInicio').val();
        const fechaFin = $('#empFechaContratoFin').val();

        // Verificar si las fechas están vacías
        if (!fechaInicio || !fechaFin) {
            $('#fechaError').remove();
            $('#empFechaContratoFin').after('<div id="fechaError" class="text-danger">Ambas fechas deben estar completadas.</div>');
            return false;
        }

        const fechaInicioDate = new Date(fechaInicio);
        const fechaFinDate = new Date(fechaFin);
        const fechaActual = new Date();

        if (fechaInicioDate < fechaActual) {
            $('#fechaInicioError').remove();
            $('#empFechaContratoInicio').after('<div id="fechaInicioError" class="text-danger">La fecha de inicio debe ser mayor o igual a la fecha actual.</div>');
            return false;
        }

        if (fechaInicioDate >= fechaFinDate) {
            $('#fechaFinError').remove();
            $('#empFechaContratoFin').after('<div id="fechaFinError" class="text-danger">La fecha de fin debe ser mayor que la fecha de inicio.</div>');
            return false;
        } else {
            $('#fechaFinError').remove();
            $('#fechaInicioError').remove();
            $('#fechaError').remove();
            return true;
        }
    }

    $('#guardarEmpresa').on('click', function (event) {
        event.preventDefault();
        const emailValido = validarEmail();
        const telefonoValido = validarTelefono();
        const documentoValido = validarDocumento();
        const fechasValidas = validarFechas();

        if (emailValido && telefonoValido && documentoValido && fechasValidas) {
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
                    toastr.success('Empresa registrada con éxito.');
                    cargarEmpresas();
                },
                error: function (xhr, status, error) {
                    console.error('Error al guardar la empresa:', error);
                    toastr.error('Hubo un error al guardar la empresa. Intenta de nuevo.');
                }
            });
        }
    });
    $('#empresaModal').on('hidden.bs.modal', function () {
        $('#empresaFormulario')[0].reset();
        $('.modal-backdrop').remove();
    });


// Validación de campos vacíos
    function validarCampoEditar(selector, mensajeError) {
        const valor = $(selector).val().trim();
        if (valor.length === 0) {
            $(selector).addClass('is-invalid');
            $(selector).next('.text-danger').remove();
            $(selector).after(`<div class="text-danger">${mensajeError}</div>`);
            return false;
        } else {
            $(selector).removeClass('is-invalid');
            $(selector).next('.text-danger').remove();
            return true;
        }
    }

// Validación de correo electrónico
    function validarEmailEditar() {
        const email = $('#editarEmpEmail').val().trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Expresión regular para validar el email
        if (!emailRegex.test(email)) {
            $('#editarEmpEmail').addClass('is-invalid');
            $('#emailErrorEdit').remove();
            $('#editarEmpEmail').after('<div id="emailErrorEdit" class="text-danger">El correo electrónico no es válido.</div>');
            return false;
        } else {
            $('#editarEmpEmail').removeClass('is-invalid');
            $('#emailErrorEdit').remove();
            return true;
        }
    }

// Validación de teléfono
    function validarTelefonoEditar() {
        const phone = $('#editarEmpTelefono').val();
        if (phone.length !== 9 || isNaN(phone)) {
            $('#editarEmpTelefono').addClass('is-invalid');
            $('#telefonoErrorEdit').remove();
            $('#editarEmpTelefono').after('<div id="telefonoErrorEdit" class="text-danger">El teléfono debe tener 9 dígitos y ser numérico.</div>');
            return false;
        } else {
            $('#editarEmpTelefono').removeClass('is-invalid');
            $('#telefonoErrorEdit').remove();
            return true;
        }
    }

// Validación de RUC y DNI
    function validarDocumentoEditar() {
        const tipoDocumento = $('#editarEmpDocumentoTipo').val();
        const documentoNumero = $('#editarEmpDocumentoNumero').val().trim();

        if (tipoDocumento === 'RUC' && (documentoNumero.length !== 11 || isNaN(documentoNumero))) {
            $('#editarEmpDocumentoNumero').addClass('is-invalid');
            $('#documentoErrorEdit').remove();
            $('#editarEmpDocumentoNumero').after('<div id="documentoErrorEdit" class="text-danger">El RUC debe tener 11 dígitos y ser numérico.</div>');
            return false;
        } else if (tipoDocumento === 'DNI' && (documentoNumero.length !== 8 || isNaN(documentoNumero))) {
            $('#editarEmpDocumentoNumero').addClass('is-invalid');
            $('#documentoErrorEdit').remove();
            $('#editarEmpDocumentoNumero').after('<div id="documentoErrorEdit" class="text-danger">El DNI debe tener 8 dígitos y ser numérico.</div>');
            return false;
        } else {
            $('#editarEmpDocumentoNumero').removeClass('is-invalid');
            $('#documentoErrorEdit').remove();
            return true;
        }
    }

// Validación de fechas
    function validarFechasEditar() {
        const fechaInicio = new Date($('#editarEmpFechaContratoInicio').val());
        const fechaFin = new Date($('#editarEmpFechaContratoFin').val());

        if (fechaInicio >= fechaFin) {
            $('#fechaFinErrorEdit').remove();
            $('#editarEmpFechaContratoFin').addClass('is-invalid');
            $('#editarEmpFechaContratoFin').after('<div id="fechaFinErrorEdit" class="text-danger">La fecha de fin debe ser mayor que la fecha de inicio.</div>');
            return false;
        } else {
            $('#editarEmpFechaContratoFin').removeClass('is-invalid');
            $('#fechaFinErrorEdit').remove();
            return true;
        }
    }

// Función para editar empresa findbyId
    function editarEmpresa(empId) {
        $.ajax({
            url: `/kenpis/empresas/find-by-id/${empId}`,
            method: 'GET',
            success: function (empresa) {
                $('#editarEmpresaId').val(empresa.empId);
                $('#editarEmpDocumentoTipo').val(empresa.empDocumentoTipo);
                $('#editarEmpDocumentoNumero').val(empresa.empDocumentoNumero);
                $('#editarEmpRazonSocial').val(empresa.empRazonSocial);
                $('#editarEmpResponsable').val(empresa.empResponsable);
                $('#editarEmpTelefono').val(empresa.empTelefono);
                $('#editarEmpEmail').val(empresa.empEmail);
                $('#editarEmpNombreComercial').val(empresa.empNombreComercial);

                const formatDate = (milisegundos) => {
                    const date = new Date(milisegundos);
                    return date.toISOString().split('T')[0]; // Formato YYYY-MM-DD
                };

                $('#editarEmpFechaContratoInicio').val(formatDate(empresa.empFechaContratoInicio));
                $('#editarEmpFechaContratoFin').val(formatDate(empresa.empFechaContratoFin));

                $('#editarEmpresaModal').modal('show');
            },
            error: function () {
                toastr.error('Error al obtener los detalles de la empresa.');
            }
        });
    }

// editar empresa update
    $('#editarEmpresaFormulario').submit(function (event) {
        event.preventDefault();
        const nombreValido = validarCampoEditar('#editarEmpRazonSocial', 'El nombre no puede estar vacío.');
        const emailValido = validarEmailEditar();
        const telefonoValido = validarTelefonoEditar();
        const documentoValido = validarDocumentoEditar();
        const fechasValidas = validarFechasEditar();

        if (nombreValido && emailValido && telefonoValido && documentoValido && fechasValidas) {
            var empresaData = {
                empId: $('#editarEmpresaId').val(),
                empDocumentoTipo: $('#editarEmpDocumentoTipo').val(),
                empDocumentoNumero: $('#editarEmpDocumentoNumero').val(),
                empRazonSocial: $('#editarEmpRazonSocial').val(),
                empResponsable: $('#editarEmpResponsable').val(),
                empTelefono: $('#editarEmpTelefono').val(),
                empEmail: $('#editarEmpEmail').val(),
                empNombreComercial: $('#editarEmpNombreComercial').val(),
                empFechaContratoInicio: $('#editarEmpFechaContratoInicio').val(),
                empFechaContratoFin: $('#editarEmpFechaContratoFin').val(),
                empIsActive: true
            };

            $.ajax({
                url: `/kenpis/empresas/update`,
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(empresaData),
                success: function () {
                    $('#editarEmpresaModal').modal('hide');
                    toastr.success('Empresa actualizada correctamente.');
                    cargarEmpresas();
                },
                error: function () {
                    toastr.error('Error al actualizar la empresa. Intente nuevamente.');
                }
            });
        }
    });


    //////////////////////////////////////////////////////////////7
    function cargarSucursales(empId) {
        $('#btnAgregarSucursal').data('emp-id', empId);

        var empresaNombreComercial = $('#empresa-row-' + empId + ' td span:first').text().trim();
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

    /* REGISTRAR Y EDITAR PARA UNA SUCURSAL */

    function validarSucursal() {
        const nombreComercial = $('#sucNombre').val().trim();
        const telefono = $('#sucTelefono').val().trim();
        let isValid = true;

        // Validar nombre comercial
        if (!nombreComercial) {
            $('#sucNombre').addClass('is-invalid');
            $('#nombreError').remove();
            $('#sucNombre').after('<div id="nombreError" class="text-danger">El nombre comercial es obligatorio.</div>');
            isValid = false;
        } else {
            $('#sucNombre').removeClass('is-invalid');
            $('#nombreError').remove();
        }

        // Validar número de teléfono
        if (telefono.length !== 9 || isNaN(telefono)) {
            $('#sucTelefono').addClass('is-invalid');
            $('#telefonoError').remove();
            $('#sucTelefono').after('<div id="telefonoError" class="text-danger">El teléfono debe tener 9 dígitos y ser numérico.</div>');
            isValid = false;
        } else {
            $('#sucTelefono').removeClass('is-invalid');
            $('#telefonoError').remove();
        }

        return isValid;
    }

    $('#btnAgregarSucursal').on('click', function () {
        $('#sucNombre').val('').removeClass('is-invalid');
        $('#sucTelefono').val('').removeClass('is-invalid');
        $('#nombreError').remove();
        $('#telefonoError').remove();
        $('#sucNombre').val('');
        $('#sucTelefono').val('');
        $('#sucursalModal').modal('show');
    });

    $('#guardarSucursal').on('click', function () {
        var empId = $('#btnAgregarSucursal').data('emp-id');
        var sucursalData = {
            empNombreComercial: $('#sucNombre').val(),
            empTelefono: $('#sucTelefono').val(),
            empPadreId: parseInt(empId),
        };
        if (!validarSucursal()) {
            return;
        }

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

    function validarSucursalEditar() {
        const nombreComercial = $('#editSucursalNombreComercial').val().trim();
        const telefono = $('#editSucursalTelefono').val().trim();
        let isValid = true;

        // Validar nombre comercial
        if (!nombreComercial) {
            $('#editSucursalNombreComercial').addClass('is-invalid');
            $('#nombreError').remove();
            $('#editSucursalNombreComercial').after('<div id="nombreError" class="text-danger">El nombre comercial es obligatorio.</div>');
            isValid = false;
        } else {
            $('#editSucursalNombreComercial').removeClass('is-invalid');
            $('#nombreError').remove();
        }

        // Validar número de teléfono
        if (telefono.length !== 9 || isNaN(telefono)) {
            $('#editSucursalTelefono').addClass('is-invalid');
            $('#telefonoError').remove();
            $('#editSucursalTelefono').after('<div id="telefonoError" class="text-danger">El teléfono debe tener 9 dígitos y ser numérico.</div>');
            isValid = false;
        } else {
            $('#editSucursalTelefono').removeClass('is-invalid');
            $('#telefonoError').remove();
        }

        return isValid;
    }

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
        if (!validarSucursalEditar()) {
            return;
        }

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

    function eliminarSucursal(usuId) {
        $('#deleteSucursalId').val(usuId);
        $('#sucursalesModal').modal('hide');
        $('#confirmDeleteModalSucursal').modal('show');
    }

    $('#eliminarSucursal').click(function () {
        let empId = $('#deleteSucursalId').val();
        $.ajax({
            url: `/kenpis/empresas/sucursal-delete/${empId}`,
            method: 'DELETE',
            success: function () {
                toastr.success('Sucursal eliminada correctamente.');
                $('#confirmDeleteModalSucursal').modal('hide');
                cargarEmpresas();
            },
            error: function () {
                toastr.error('Error al eliminar la sucursal. Intente nuevamente.');
            }
        });
    });


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    $('#cargarMetodoPago').on('click', function (event) {
        event.preventDefault();
        $('#modalRegistrarMetodoPago').modal('show');
        var empId = $('#guardarMetodoPago').data('emp-id');
        var metodoPagoData = {
            empId: empId,
            metPagoTipo: $('#metodoPagoTipo').val(),
            metPagoLogo: $('#metodoPagoLogo').val(),
            metPagoQr: $('#metodoPagoQr').val(),
            metPagoCuentaNombre: $('#metodoPagoCuentaNombre').val(),
            metPagoCuentaNumero: $('#metodoPagoCuentaNumero').val(),
            metPagoDetalle: $('#metodoPagoDetalle').val()
        };

        $.ajax({
            url: '/kenpis/metodoPago/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(metodoPagoData),
            success: function (response) {
                $('#modalRegistrarMetodoPago').modal('hide');
                toastr.success('Metodo de pago registrado con éxito.');
                cargarEmpresas();
            },
            error: function (xhr, status, error) {
                console.error('Error al guardar el metodo de pago:', error);
                toastr.error('Hubo un error al guardar el metodo de pago. Intenta de nuevo.');
            }
        });
    });
    $('#modalRegistrarMetodoPago').on('hidden.bs.modal', function () {
        $('#formRegistrarMetodoPago')[0].reset();
        $('.modal-backdrop').remove();
    });

    function cargarMetodoPago(empId) {
        $.ajax({
            url: '/kenpis/metodoPago/empresa/' + empId,
            method: 'GET',
            success: function (response) {
                var html = ''
                if (response.data && response.data.length > 0) {
                    response.data.forEach(function (metodo) {
                        html += '<tr>' +
                            '<td>' + metodo.metPagoTipo + '</td>' +
                            '<td><img src="' + metodo.metPagoLogo + '" alt="Logo" style="width: 50px; height: 50px;"></td>' +
                            '<td><img src="' + metodo.metPagoQr + '" alt="QR" style="width: 50px; height: 50px;"></td>' +
                            '<td>' + metodo.metPagoCuentaNombre + '</td>' +
                            '<td>' + metodo.metPagoCuentaNumero + '</td>' +
                            '<td>' + metodo.metPagoDetalle + '</td>' +
                            '</tr>';
                    });
                } else  {
                    html = '<tr><td colspan="6"> response.message;</td></tr>';

                }

                $('#tablaMetodosPago tbody').html(html);
                $('#modalRegistrarMetodoPago').modal('show');
            },
            error: function (xhr, status, error) {
                console.error('Error al cargar los métodos de pago:', error);
                toastr.error('Error al cargar los métodos de pago.');
            }
        });
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //EDITAR LA EMPRESA DE UN USUARIO CON ROL DE PROPIETARIO

    var formularioModificado = false;

// Detectar cambios en los campos del formulario
    $('#empresaForm input, #empresaForm select').on('input change', function () {
        formularioModificado = true;
        $('#botonActualizar').prop('disabled', false);
    });

    $('#botonActualizar').prop('disabled', true);

// Manejar la actualización del formulario
    $('#empresaForm').on('submit', function (event) {
        event.preventDefault();
        if (formularioModificado) {
            $('#confirmModal').modal('show');
        } else {
            toastr.info('No se realizaron cambios en el formulario.');
        }
    });

// Confirmar la actualización desde el modal
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

// Cancelar la actualización desde el modal
    $('#cancelUpdate').on('click', function () {
        $('#confirmModal').modal('hide');
    });


    // Función para eliminar una empresa
    function eliminarEmpresa(empId) {
        $('#deleteEmpresaId').val(empId);
        $('#confirmDeleteModalEmpresa').modal('show');
    }

    $('#eliminarEmpresa').click(function () {
        let empId = $('#deleteEmpresaId').val();
        $.ajax({
            url: `/kenpis/empresas/delete/${empId}`,
            method: 'DELETE',
            success: function () {
                $('#confirmDeleteModalEmpresa').modal('hide');
                toastr.success('Empresa eliminada correctamente.');
                cargarEmpresas();
            },
            error: function () {
                toastr.error('Error al eliminar la empresa. Intente nuevamente.');
            }
        });
    });


//////////////////////////////////////////////////////////////////////////////////////

    // actualizar estado para empresa
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
                toastr.success('Estado  actualizado correctamente.');
            },
            error: function () {
                toastr.error('Error al actualizar el estado. Intente nuevamente.');
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
