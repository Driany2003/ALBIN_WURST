$(document).ready(function () {
    let sucursalesAsignadas = new Set();
    let empresaId = $('#empresaId').val();
    let cajaIdToClose = null;
    let botonCerrarCaja;
    const usuariosMap = {};

    cargarNombresUsuarios().then(inicializarCaja);

    function cargarNombresUsuarios() {
        return $.ajax({
            url: '/kenpis/usuario/nombreCompleto',
            method: 'GET',
            success: function (usuarios) {
                usuarios.forEach(usuario => {
                    usuariosMap[usuario.usuId] = usuario.nombreCompleto;
                });
            },
            error: function () {
                toastr.error('Error al cargar los nombres de usuarios.');
            }
        });
    }

    // Evento para abrir el modal de apertura de caja
    $('#btnAperturarCaja').on('click', verificarDisponibilidadParaRegistrar);

    // Función para inicializar y cargar el estado de las cajas
    function inicializarCaja() {
        verificarDisponibilidadParaRegistrar();
        cargarCajasPorEmpresa();
    }

    // Verificar disponibilidad de sucursales y abrir modal si hay disponibilidad
    function verificarDisponibilidadParaRegistrar() {
        $.ajax({
            url: `/kenpis/caja/listar-disponibles/${empresaId}`,
            method: 'GET',
            success: function (response) {
                if (response.status === "success" && response.sucursales.length > 0) {
                    mostrarSucursalesDisponibles(response.sucursales);
                } else {
                    toastr.warning('No hay sucursales o usuarios disponibles para registrar una nueva caja.');
                }
            },
            error: function () {
                toastr.error('Error al conectar con el servidor.');
            }
        });
    }

    // Cargar y mostrar sucursales disponibles en el modal
    function mostrarSucursalesDisponibles(sucursales) {
        const sucursalSelect = $('#sucursalSelect');
        sucursalSelect.empty();

        sucursales.forEach(sucursal => {
            if (!sucursalesAsignadas.has(sucursal.empNombreComercial)) {
                sucursalSelect.append(new Option(sucursal.empNombreComercial, sucursal.empId));
            }
        });
    }

    // Registrar apertura de caja
    $('#btnRegistrarCaja').click(function () {
        const sucursalId = $('#sucursalSelect').val();
        const sucursalNombre = $('#sucursalSelect option:selected').text();
        const montoInicial = parseFloat($('#montoInicialInput').val());

        if (!sucursalId || isNaN(montoInicial)) {
            toastr.error('Seleccione una sucursal y un monto inicial válido.');
            return;
        }

        $.ajax({
            url: '/kenpis/caja/abrir',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                empPadreId: empresaId,
                sucursalId: sucursalId,
                cajaMontoInicial: montoInicial,
                cajaUsuarioApertura: $('#usuarioId').val(),
                cajaAsignada: sucursalNombre, //campo a eliminar
                cajaEstado: true
            }),
            success: function (response) {
                toastr.success('Caja abierta correctamente.');
                $('#aperturarCajaModal').modal('hide');
                cargarCajasPorEmpresa();
            },
            error: function () {
                toastr.error('Error al abrir la caja.');
            }
        });
    });

    // Cargar el historial de cajas de la empresa
    function cargarCajasPorEmpresa() {
        $.ajax({
            url: `/kenpis/caja/listar/${empresaId}`,
            method: 'GET',
            success: function (cajas) {
                const tablaCajas = $('#usuariosBody');
                tablaCajas.empty();
                sucursalesAsignadas.clear();

                cajas.forEach((caja, index) => {
                    mostrarCajaEnTabla(caja, index);
                });
            },
            error: function () {
                toastr.error('Error al cargar el historial de cajas.');
            }
        });
    }

    // Mostrar cada caja en la tabla
    function mostrarCajaEnTabla(caja, index) {
        const estado = caja.cajaEstado ? 'Abierta' : 'Cerrada';
        if (caja.cajaEstado) sucursalesAsignadas.add(caja.cajaAsignada);

        const fechaApertura = caja.cajaFechaApertura ? formatDateTime(caja.cajaFechaApertura) : '-';
        const fechaCierre = caja.cajaFechaCierre ? formatDateTime(caja.cajaFechaCierre) : '-';
        const montoInicial = `S/ ${parseFloat(caja.cajaMontoInicial).toFixed(2)}`;
        const montoFinal = `S/ ${parseFloat(caja.cajaMontoFinal).toFixed(2)}`;
        const disableCloseButton = !caja.cajaEstado ? 'disabled' : '';

        // Utiliza el mapa de usuarios para obtener el nombre en lugar del ID
        const usuarioApertura = usuariosMap[caja.cajaUsuarioApertura] || '-';
        const usuarioCierre = usuariosMap[caja.cajaUsuarioCierre] || '-';

        const fila = `
            <tr>
                <td>${index + 1}</td>
                <td>
                    <div>Apertura: ${usuarioApertura}</div>
                    <div>Cierre: ${usuarioCierre}</div>
                </td>
                <td>${caja.cajaAsignada}</td>
                <td>
                    <div>Fecha Apertura: ${fechaApertura}</div>
                    <div>Fecha Cierre: ${fechaCierre}</div>
                </td>
                <td>
                    <div>Monto Inicial: ${montoInicial}</div>
                    <div>Monto Final: ${montoFinal}</div>
                </td>
                <td>${estado}</td>
                <td>
                    <button class="btn btn-info btn-sm btnCerrarCaja" data-caja-id="${caja.cajaId}" ${disableCloseButton}>Cerrar</button>
                </td>
            </tr>
            `;
        $('#usuariosBody').append(fila);
    }

    // Confirmar cierre de caja
    $(document).on('click', '.btnCerrarCaja', function () {
        cajaIdToClose = $(this).data('caja-id');
        botonCerrarCaja = $(this);
        $('#confirmarCierreModal').modal('show');
    });

    // Cerrar caja confirmada
    $('#confirmarCerrarCajaBtn').click(function () {
        if (cajaIdToClose) {
            $(this).prop('disabled', true);
            $.ajax({
                url: '/kenpis/caja/cerrar',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(cajaIdToClose),
                success: function () {
                    toastr.success('Caja cerrada correctamente.');
                    $('#confirmarCierreModal').modal('hide');
                    cargarCajasPorEmpresa();
                },
                error: function () {
                    toastr.error('Error al cerrar la caja.');
                    $(botonCerrarCaja).prop('disabled', false);
                },
                complete: function () {
                    $('#confirmarCerrarCajaBtn').prop('disabled', false);
                    cajaIdToClose = null;
                }
            });
        }
    });

    // Formatear fecha y hora
    function formatDateTime(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('es-PE', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });
    }

});
