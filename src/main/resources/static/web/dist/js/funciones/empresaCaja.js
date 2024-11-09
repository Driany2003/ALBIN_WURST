$(document).ready(function () {
    let sucursalesAsignadas = new Set();
    let usuariosAsignados = new Set();
    var empresaId = $('#empresaId').val();
    let cajaIdToClose = null;
    let botonCerrarCaja;

    verificarDisponibilidadParaRegistrar(empresaId);

    // Cambiamos a 'show.bs.modal' para que el AJAX solo se ejecute al abrir el modal inicialmente
    $('#btnAperturarCaja').on('click', function () {
        verificarDisponibilidadParaRegistrar(empresaId);
    });

    function verificarDisponibilidadParaRegistrar(empresaId) {
        $.ajax({
            url: `/kenpis/caja/listar-disponibles/${empresaId}`,
            method: 'GET',
            success: function (response) {
                if (response.status === "success") {
                    const sucursalesDisponibles = response.sucursales.filter(sucursal => !sucursalesAsignadas.has(sucursal.empNombreComercial));
                    const usuariosDisponibles = response.responsable.filter(usuario => !usuariosAsignados.has(usuario.usuNombre));

                    // Si hay disponibilidad, habilita el botón y abre el modal
                    if (sucursalesDisponibles.length > 0 && usuariosDisponibles.length > 0) {
                        $('#btnAperturarCaja').prop('disabled', false);
                        cargarSucursalesYResponsablesDisponibles(empresaId);
                        $('#aperturarCajaModal').modal('show');
                    } else {
                        $('#sucursalSelect').empty();
                        $('#usuarioEncargadoSelect').empty();
                        $('#montoInicialInput').val('');
                        toastr.warning('No hay sucursales o usuarios disponibles para registrar una nueva caja.');
                    }
                } else {
                    toastr.error('Error al verificar sucursales y responsables disponibles.');
                }
            },
            error: function () {
                toastr.error('Error al conectar con el servidor.');
            }
        });
    }

    function cargarSucursalesYResponsablesDisponibles(empresaId) {
        $.ajax({
            url: `/kenpis/caja/listar-disponibles/${empresaId}`,
            method: 'GET',
            success: function (response) {
                if (response.status === "success") {
                    const sucursalSelect = $('#sucursalSelect');
                    const responsableSelect = $('#usuarioEncargadoSelect');
                    sucursalSelect.empty();
                    responsableSelect.empty();

                    response.sucursales.forEach(sucursal => {
                        if (!sucursalesAsignadas.has(sucursal.empNombreComercial)) {
                            sucursalSelect.append(new Option(sucursal.empNombreComercial, sucursal.empId));
                        }
                    });

                    response.responsable.forEach(usuario => {
                        if (!usuariosAsignados.has(usuario.usuNombre)) {
                            responsableSelect.append(new Option(usuario.usuNombre, usuario.usuId));
                        }
                    });
                } else {
                    toastr.error('Error al obtener sucursales y responsables disponibles.');
                }
            },
            error: function () {
                toastr.error('Error al conectar con el servidor.');
            }
        });
    }

    $('#btnRegistrarCaja').click(function () {
        const sucursalNombre = $('#sucursalSelect option:selected').text();
        const usuarioNombre = $('#usuarioEncargadoSelect option:selected').text();
        const montoInicial = parseFloat($('#montoInicialInput').val());

        if (!usuarioNombre || !sucursalNombre || isNaN(montoInicial)) {
            toastr.error('Seleccione una sucursal, un usuario y un monto inicial válido.');
            return;
        }

        $.ajax({
            url: '/kenpis/caja/abrir',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                empPadreId: empresaId,
                cajaMontoInicial: montoInicial,
                cajaUsuarioApertura: usuarioNombre,
                cajaAsignada: sucursalNombre,
                cajaEstado: true
            }),
            success: function () {
                toastr.success('Caja abierta correctamente.');
                $('#aperturarCajaModal').modal('hide');
                cargarCajasPorEmpresa(empresaId);
            },
            error: function () {
                toastr.error('Error al abrir la caja.');
            }
        });
    });

    cargarCajasPorEmpresa(empresaId);

    function cargarCajasPorEmpresa(empresaId) {
        $.ajax({
            url: `/kenpis/caja/listar/${empresaId}`,
            method: 'GET',
            success: function (cajas) {
                const tablaCajas = $('#usuariosBody');
                tablaCajas.empty();
                sucursalesAsignadas.clear();
                usuariosAsignados.clear();

                cajas.forEach((caja, index) => {
                    const estado = caja.cajaEstado ? 'Abierta' : 'Cerrada';

                    if (caja.cajaEstado) {
                        sucursalesAsignadas.add(caja.cajaAsignada);
                        usuariosAsignados.add(caja.cajaUsuarioApertura);
                    }

                    const fechaApertura = caja.cajaFechaApertura ? `Fecha Apertura: ${formatDateTime(caja.cajaFechaApertura)}` : 'Fecha Apertura: -';
                    const fechaCierre = caja.cajaFechaCierre ? `Fecha Cierre: ${formatDateTime(caja.cajaFechaCierre)}` : 'Fecha Cierre: -';

                    const montoInicial = caja.cajaMontoInicial ? `Monto Inicial: S/ ${caja.cajaMontoInicial.toFixed(2)}` : 'Monto Inicial: -';
                    const montoFinal = caja.cajaMontoFinal ? `Monto Final: S/ ${caja.cajaMontoFinal.toFixed(2)}` : 'Monto Final: -';

                    const disableCloseButton = !caja.cajaEstado ? 'disabled' : ''; // Deshabilita el botón si la caja está cerrada
                    const fila = `
                <tr>
                    <td>${index + 1}</td>
                    <td>${caja.cajaUsuarioApertura}</td>
                    <td>${caja.cajaAsignada}</td>
                    <td>
                        <div>${fechaApertura}</div>
                        <div>${fechaCierre}</div>
                    </td>
                    <td>
                        <div>${montoInicial}</div>
                        <div>${montoFinal}</div>
                    </td>
                    <td>${estado}</td>
                    <td>
                        <button class="btn btn-info btn-sm btnCerrarCaja" data-caja-id="${caja.cajaId}" ${disableCloseButton}>Cerrar</button>
                    </td>
                </tr>
            `;
                    tablaCajas.append(fila);
                });
            },
            error: function () {
                toastr.error('Error al cargar el historial de cajas.');
            }
        });
    }


    $(document).on('click', '.btnCerrarCaja', function () {
        cajaIdToClose = $(this).data('caja-id');
        botonCerrarCaja = $(this);  // Almacena el botón de la fila
        $('#confirmarCierreModal').modal('show');
    });

    // Evento de confirmación en el modal para cerrar la caja
    $('#confirmarCerrarCajaBtn').on('click', function () {
        if (cajaIdToClose && botonCerrarCaja) {
            $('#confirmarCerrarCajaBtn').prop('disabled', true); // Deshabilita el botón de confirmación en el modal
            botonCerrarCaja.prop('disabled', true); // Deshabilita el botón de la fila específica

            // Llamada AJAX para cerrar la caja
            $.ajax({
                url: '/kenpis/caja/cerrar',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(cajaIdToClose),
                success: function () {
                    toastr.success('Caja cerrada correctamente.');
                    $('#confirmarCierreModal').modal('hide');  // Cierra el modal de confirmación
                    cargarCajasPorEmpresa(empresaId);  // Recarga la lista de cajas
                },
                error: function () {
                    toastr.error('Error al cerrar la caja.');
                    botonCerrarCaja.prop('disabled', false); // Solo se vuelve a habilitar en caso de error
                },
                complete: function () {
                    // Reinicia el botón del modal después de la operación
                    $('#confirmarCerrarCajaBtn').prop('disabled', false);
                    cajaIdToClose = null;
                    botonCerrarCaja = null;
                }
            });
        }
    });

    function formatDateTime(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const seconds = date.getSeconds().toString().padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
});
