$(document).ready(function () {
    cargarEmpresas();

    function cargarEmpresas() {
        var usuarioId = $('#usuarioId').val();
        var usuarioNivel = $('#usuarioNivel').val();
        console.log("nivel de usuairi", usuarioNivel);

        $.ajax({
            url: '/kenpis/empresas/cargar-empresas',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({usuId: usuarioId}),
            success: function (response) {
                if (response.status === 'success') {
                    if (usuarioNivel === 'ADMINISTRADOR' && Array.isArray(response.data)) {
                        renderAdministradorView(response.data);
                    } else if (usuarioNivel === 'PROPIETARIO' && response.data) {
                        renderPropietarioView(response.data);
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

    // Renderizar la vista para el Administrador
    function renderAdministradorView(empresas) {
        var tableBody = $('#empresaBody');
        tableBody.empty();
        empresas.forEach(function (empresa) {
            var responsableTelefono = (empresa.empResponsable || 'Sin responsable') + ' - ' + (empresa.empTelefono || 'Sin teléfono');
            tableBody.append(
                '<tr id="empresa-row-' + empresa.empId + '" class="empresa-row" data-id="' + empresa.empId + '">' +
                '<td>' +
                '<img src="' + empresa.empImagenLogo + '" alt="Logo" style="width: 50px; height: 50px; vertical-align: middle;"> ' +
                '</td>' +
                '<td>' +
                '<span>' + empresa.empNombreComercial + '</span>' +
                '</td>' +
                '<td>' +
                '<span class="text">' + responsableTelefono + '</span>' +
                '</td>' +
                '<td>' +
                '<div class="contrato-fechas">' +
                '<div><i class="fas fa-calendar-alt"></i>&nbsp;<strong>Inicio:</strong> ' + formatDate(empresa.empFechaContratoInicio) + '</div>' +
                '<div><i class="fas fa-calendar-check"></i>&nbsp;<strong>Fin:</strong> ' + formatDate(empresa.empFechaContratoFin) + '</div>' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<label class="switch">' +
                '<input type="checkbox" class="estado-checkbox" data-id="' + empresa.empId + '" ' + (empresa.empIsActive ? 'checked' : '') + '>' +
                '<span class="slider"></span>' +
                '</label>' +
                '</td>' +
                '<td>' +
                '<button type="button" data-id="' + empresa.empId + '" class="btn btn-sm btn-warning editarEmpresa" data-toggle="tooltip" title="Editar Empresa">' +
                '<i class="fas fa-pencil-alt"></i>' +
                '</button>' +
                '<span> </span>' +
                '<button type="button" data-id="' + empresa.empId + '" class="btn btn-sm btn-danger eliminarEmpresa" data-toggle="tooltip" title="Eliminar Empresa">' +
                '<i class="fas fa-trash"></i>'+
                '</button>' +
                '<span> </span>' +
                '<button type="button" data-id="' + empresa.empId + '" class="btn btn-sm btn-info mostrarSucursales" data-toggle="tooltip" title="Mostrar Sucursales">' +
                '<i class="fas fa-building"></i>' +
                '</button>' +
                '<span> </span>' +
                '<button type="button" data-id="' + empresa.empId + '" class="btn btn-sm btn-danger mostrarMetodosPago" data-toggle="tooltip" title="Mostrar Métodos de Pago">' +
                '<i class="fas fa-credit-card"></i>' +
                '</button>' +
                '</td>' +
                '</tr>'
            );
        });

        // Asignación de eventos fuera del bucle forEach
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
            $('#btnAgregarMetodoPago').data('emp-id', empId);
            $('#guardarMetPago').data('emp-id', empId);
            cargarMetodoPago(empId);
        });
    }

    // Renderizar la vista para el Propietario (igual que en el código anterior)
// Renderizar la vista para el Propietario
    function renderPropietarioView(empresa) {
        if (empresa.empImagenLogo) {
            $('#logoEmpresa').attr('src', empresa.empImagenLogo).show();
            $('#initialsCanvas').hide();
        } else {
            var initials = getInitials(empresa.empNombreComercial);
            drawInitialsOnCanvas(initials);
        }
        // Establecer los valores en las etiquetas HTML
        $('#NombreComercial').text(empresa.empNombreComercial);
        $('#NumeroDocumento').text(empresa.empDocumentoNumero);
        // Establecer los valores en los campos de formulario
        $('#empresaNombre').val(empresa.empNombreComercial);
        $('#empresaRazonSocial').val(empresa.empRazonSocial);
        $('#empresaTipoDocumento').val(empresa.empDocumentoTipo);
        $('#empresaNumeroDocumento').val(empresa.empDocumentoNumero);
        $('#empresaTelefono').val(empresa.empTelefono);
        $('#empresaEmail').val(empresa.empEmail);

        // Verificar si hay sucursales y mostrarlas en la tabla
        if (empresa.listaSucursales && Array.isArray(empresa.listaSucursales)) {
            var tableBody = $('#sucursalesTableBody');
            tableBody.empty();

            empresa.listaSucursales.forEach(function (sucursal) {
                var rowHtml = `
                <tr>
                    <td>${sucursal.empNombreComercial || 'N/A'}</td>
                    <td>${sucursal.empTelefono || 'N/A'}</td>
                    <td>${sucursal.empEmail || 'N/A'}</td>
                    <td>
                        <input type="checkbox" ${sucursal.empIsActive ? 'checked' : ''} disabled>
                    </td>
                </tr>`;
                tableBody.append(rowHtml);
            });
        } else {
            var noDataHtml = `
            <tr>
                <td colspan="4" class="text-center">No se encontraron sucursales para esta empresa.</td>
            </tr>`;
            $('#sucursalesTableBody').append(noDataHtml);
        }
    }

    function getInitials(name) {
        return name.split(' ').map(word => word.charAt(0)).join('').toUpperCase();
    }

    function drawInitialsOnCanvas(initials) {
        var canvas = document.getElementById('initialsCanvas');
        var ctx = canvas.getContext('2d');

        ctx.fillStyle = '#007bff'; // Fondo
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.font = 'bold 40px Arial';
        ctx.fillStyle = '#ffffff';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText(initials, canvas.width / 2, canvas.height / 2);
    }
    // Formatear fechas
    function formatDate(date) {
        var options = {year: 'numeric', month: '2-digit', day: '2-digit'};
        return new Date(date).toLocaleDateString('es-ES', options);
    }


// Validación de correo electrónico
    function validarEmail() {
        const email = $('#empEmail').val().trim();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
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

    $('#empImageLogo').on('change', function () {
        var file = this.files[0];

        if (file) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#logoPreview').attr('src', e.target.result).css({width: '100px', height: '100px'}).show();
                $('#cargarLogo').show();
                $('#empImageBase64').val(e.target.result);
            };
            reader.readAsDataURL(file);
        } else {
            $('#logoPreview').hide();
            $('#cargarLogo').hide();
            $('#empImageBase64').val('');
        }
    });

    $('#cargarLogo').on('click', function () {
        $('#empImageLogo').trigger('click');
    });


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
                empNombreComercial: $('#empNombreComercial').val(),
                empFechaContratoInicio: $('#empFechaContratoInicio').val(),
                empFechaContratoFin: $('#empFechaContratoFin').val(),
                empFechaCreacion: $('#empFechaCreacion').val(),
                empQrYape: $('#empQrYape').val(),
                empQrPlin: $('#empQrPlin').val(),
                empQrPagos: $('#empQrPagos').val(),
                empEmail: $('#empEmail').val(),
                empImageLogo: $('#empImageBase64').val()

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
        $('#logoPreview').hide(); // Ocultar la vista previa del logo
        $('#cargarLogo').hide();  // Ocultar el botón de cargar nuevo logo
        $('#empImageBase64').val(''); // Limpiar el campo oculto
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
                if (empresa.empImagenLogo) {
                    $('#logoPreviewEdit').attr('src', empresa.empImagenLogo).css({width: '100px', height: '100px'}).show();
                } else {
                    $('#logoPreviewEdit').hide();
                }

                $('#editarEmpFechaContratoInicio').val(formatDate(empresa.empFechaContratoInicio));
                $('#editarEmpFechaContratoFin').val(formatDate(empresa.empFechaContratoFin));

                $('#editarEmpresaModal').modal('show');
            },
            error: function () {
                toastr.error('Error al obtener los detalles de la empresa.');
            }
        });
    }

    // Manejar la carga del logo de la empresa en el formulario de edición
    $('#editarEmpImageLogo').on('change', function () {
        var file = this.files[0];
        if (file) {
            if (file.type === 'image/png' || file.type === 'image/jpeg') {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#logoPreviewEdit').attr('src', e.target.result).css({width: '100px', height: '100px'}).show();
                };
                reader.readAsDataURL(file);
            } else {
                toastr.error('Solo se permiten imágenes en formato PNG o JPG.');
                $('#editarEmpImageLogo').val('');
                $('#logoPreviewEdit').hide();
            }
        } else {
            $('#logoPreviewEdit').hide();
        }
    });


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
                empImagenLogo: $('#logoPreviewEdit').attr('src'),
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

    $('#btnAgregarMetodoPago').on('click', function () {
        var empId = $(this).data('emp-id');
        console.log("Id de la empresa seleccionada", empId);
        $('#modalRegistrarMetodosPago').modal('show');
    });

    // Habilitar o deshabilitar campos adicionales al seleccionar un método de pago
    $('.metodo-pago-checkbox').on('change', function () {
        var $fila = $(this).closest('tr'); // Obtener la fila actual

        if ($(this).is(':checked')) {
            $fila.find('input[type="text"]').attr('disabled', false);
            $fila.find('input[type="file"]').attr('disabled', false);
        } else {
            $fila.find('input[type="text"]').attr('disabled', true).val('');
            $fila.find('input[type="file"]').val('');
            $fila.find('img').hide();
            $fila.find('#editQrYape, #editQrPlin').hide();
            $fila.find('#cargarQrYape, #cargarQrPlin').show();
            $fila.find('input[type="file"]').attr('disabled', true);
        }
    });

    // Manejar la carga del QR
    $('input[type="file"]').on('change', function () {
        var $fila = $(this).closest('tr');
        var file = this.files[0];

        if (file) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $fila.find('img').attr('src', e.target.result).css({width: '50px', height: '50px'}).show(); // Ajustar el tamaño y mostrar la imagen
                // Ocultar el botón de cargar QR
                $fila.find('#cargarQrYape, #cargarQrPlin').hide();
                // Mostrar el botón de editar
                $fila.find('#editQrYape, #editQrPlin').show();
            }
            reader.readAsDataURL(file);
        } else {
            $fila.find('img').hide();
            $fila.find('#editQrYape, #editQrPlin').hide();
            $fila.find('#cargarQrYape, #cargarQrPlin').show();
        }
    });

    // Funcionalidad para editar el QR (opcional, puedes agregar tu lógica aquí)
    $('#editarQrYape').on('click', function () {
        var $fila = $(this).closest('tr');
        $fila.find('input[type="file"]').trigger('click');
    });

    $('#editarQrPlin').on('click', function () {
        var $fila = $(this).closest('tr');
        $fila.find('input[type="file"]').trigger('click');
    });

    // Guardar método de pago seleccionado
    $('#guardarMetPago').on('click', function () {
        var empId = $(this).data('emp-id');
        console.log("valor del Id de la empresa " + empId);
        var metodosPago = [];
        var validInput = true;

        $('#tablaMetodosPago tbody tr').each(function () {
            var $fila = $(this);
            var metodoPagoCheckbox = $fila.find('.metodo-pago-checkbox');

            if (metodoPagoCheckbox.is(':checked')) {
                var metodoPago = {
                    metPagoTipo: metodoPagoCheckbox.data('tipo'),
                    metPagoCuentaNumero: $fila.find('input[type="text"][id^="metPagoCuenta"]').val(),
                    metPagoCuentaNombre: $fila.find('input[type="text"][id^="metPagoNombre"]').val(),
                    metPagoDetalle: $fila.find('input[type="text"][id^="metPagoDetalle"]').val(),
                    metPagoQr: $fila.find('img').attr('src') || ''
                };

                if (!metodoPago.metPagoCuentaNumero || !metodoPago.metPagoCuentaNombre || !metodoPago.metPagoDetalle) {
                    validInput = false;
                    return false; // Salir del loop
                }

                metodosPago.push(metodoPago);
            }
        });

        var metodoPagoData = {
            empId: empId,
            metodosPago: metodosPago
        };
        if (validInput) {
            $.ajax({
                url: `/kenpis/metodoPago/create`,
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(metodoPagoData),
                success: function (response) {
                    toastr.success("Métodos de pago guardados exitosamente");
                    $('#modalRegistrarMetodosPago').modal('hide');
                    cargarMetodoPago(empId);
                },
                error: function (error) {
                    toastr.error("Error al guardar métodos de pago", error);
                }
            });
        } else {
            toastr.info("Por favor, complete todos los campos requeridos para los métodos de pago seleccionados.");
        }
    });


    // Resetear el formulario al cerrar el modal
    $('#modalRegistrarMetodosPago').on('hidden.bs.modal', function () {
        $('#formRegistrarMetodosPago')[0].reset();
        $('.modal-backdrop').remove();
    });
    $('#btnAgregarMetodoPago').on('click', function () {
        $('#modalRegistrarMetodosPago').modal('show');
        $('#modalRegistrarMetodoPago').modal('hide');
    });
    $('#guardarMetPago').on('click', function () {
        $('#modalRegistrarMetodoPago').modal('show');
        $('#modalRegistrarMetodosPago').modal('hide');
    });

    // Cargar métodos de pago para una empresa
    function cargarMetodoPago(empId) {
        $.ajax({
            url: '/kenpis/metodoPago/empresa/' + empId,
            method: 'GET',
            success: function (response) {
                $('#tablaMetodoPago tbody').empty();
                $('.metodo-pago-checkbox').prop('checked', false);

                if (response.data && response.data.length > 0) {
                    var metodosExistentes = response.data.map(metodo => metodo.metPagoTipo);

                    response.data.forEach(function (metodo) {
                        var html = '<tr>' +
                            '<td>' + metodo.metPagoTipo + '</td>' +
                            '<td><img src="' + metodo.metPagoQr + '" alt="QR" style="width: 50px; height: 50px;"></td>' +
                            '<td>' + metodo.metPagoCuentaNombre + '</td>' +
                            '<td>' + metodo.metPagoCuentaNumero + '</td>' +
                            '<td>' + metodo.metPagoDetalle + '</td>' +
                            '<td>' +
                            '<button type="button" data-id="' + metodo.empId + '"   data-tipo="' + metodo.metPagoTipo + '"   class="btn btn-sm btn-danger eliminarMetodoPago" data-toggle="tooltip" data-placement="top" title="Eliminar Metodo de Pago">' +
                            '<i class="fas fa-trash"></i>' +
                            '</button>' +
                            '</td>' +
                            '</tr>';
                        $('#tablaMetodoPago tbody').append(html);
                    });

                    $('#tablaMetodosPago tbody tr').each(function () {
                        var tipo = $(this).find('.metodo-pago-checkbox').data('tipo');
                        if (metodosExistentes.includes(tipo)) {
                            $(this).hide();
                        } else {
                            $(this).show();
                        }
                    });

                } else {
                    var noMethodsHtml = '<tr><td colspan="7" class="text-center">No hay métodos de pago disponibles.</td></tr>';
                    $('#tablaMetodoPago tbody').html(noMethodsHtml);
                }

                // Asignar el evento de eliminación después de cargar la tabla
                $('.eliminarMetodoPago').off('click').on('click', function (event) {
                    event.preventDefault();
                    var empId = $(this).data('id');
                    var metTipoPago = $(this).data('tipo');
                    console.log("Id de la empresa: " + empId + ", Tipo de método de pago: " + metTipoPago);
                    eliminarMetodoPago(empId, metTipoPago);
                });

                $('#modalRegistrarMetodoPago').modal('show').on('show.bs.modal', function () {
                    $('#modalRegistrarMetodosPago').modal('hide');
                });
            },
            error: function (xhr, status, error) {
                toastr.error('Error al cargar los métodos de pago.');
            }
        });
    }


    function eliminarMetodoPago(empId, metTipoPago) {
        $('#deleteMetodoPagoId').val(metTipoPago);
        $('#deleteEmpId').val(empId);
        console.log("Id de la empresa: " + empId + ", Tipo de método de pago: " + metTipoPago);
        $('#confirmDeleteModalMetodoPago').modal('show');
    }

// Evento de clic en el botón de eliminar del modal
    $('#eliminarMetodoPago').off('click').on('click', function () {
        let metTipoPago = $('#deleteMetodoPagoId').val();
        let empId = $('#deleteEmpId').val();
        console.log("Eliminando método de pago con tipo: " + metTipoPago + " y empId: " + empId);

        $.ajax({
            url: `/kenpis/metodoPago/delete/${metTipoPago}/${empId}`,
            method: 'DELETE',
            success: function () {
                $('#confirmDeleteModalMetodoPago').modal('hide');
                toastr.success('Método de Pago eliminado correctamente.');
                cargarMetodoPago(empId);
            },
            error: function () {
                toastr.error('Error al eliminar el Método de Pago. Intente nuevamente.');
            }
        });
    });


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // EDITAR LA EMPRESA DE UN USUARIO CON ROL DE PROPIETARIO

    var formularioModificado = false;

// Detectar cambios en los campos del formulario
    $('#empresaForm input, #empresaForm select').on('input change', function () {
        formularioModificado = true;
        $('#botonActualizar').prop('disabled', false);
    });

    $('#botonActualizar').prop('disabled', true);

    $('#empresaForm').submit(function (event) {
        event.preventDefault();

        // Crear un objeto con los datos del formulario
        var empresaData = {
            empId: $('#empresaId').val(),
            empNombreComercial: $('#empresaNombre').val(),
            empRazonSocial: $('#empresaRazonSocial').val(),
            empDocumentoTipo: $('#empresaTipoDocumento').val(),
            empDocumentoNumero: $('#empresaNumeroDocumento').val(),
            empTelefono: $('#empresaTelefono').val(),
            empEmail: $('#empresaEmail').val()
        };


        if (!formularioModificado) {
            toastr.info('No se realizaron cambios en el formulario.');
            return;
        }
        $('#confirmModal').modal('show');

        $('#confirmUpdate').off('click').on('click', function () {
            $.ajax({
                url: '/kenpis/empresas/update-propietario',
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(empresaData),
                success: function (response) {
                        toastr.success('Empresa actualizada correctamente.');
                        formularioModificado = false;
                        $('#confirmModal').modal('hide');
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
