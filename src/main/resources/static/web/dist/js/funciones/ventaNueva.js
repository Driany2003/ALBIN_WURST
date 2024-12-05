$(document).ready(function () {
    var empresaId = $('#empresaId').val();
    let usuarioId = $('#usuarioId').val();
    let clienteId = null;
    let totalPagar = 0;
    let cajaAbierta = null;
    var detallesVenta = [];
    let categoriaActual = null;

    bloquearCampos();
    $.ajax({
        url: '/kenpis/caja/obtener-caja-seleccionada',
        method: 'GET',
        success: function (response) {
            if (response.cajaId) {
                cajaAbierta = response.cajaId;
                $('#sucursalNombreLabel p').text(response.sucursalNombre || 'Sucursal no encontrada');
                habilitarCampos();
            } else {
                listarCajasAbiertasPorEmpresa();
            }
        },
        error: function () {
            toastr.error('Error al verificar la caja seleccionada.');
        }
    });

    function listarCajasAbiertasPorEmpresa() {
        $.ajax({
            url: `/kenpis/caja/listar-cajas-activas/${empresaId}`,
            method: 'GET',
            success: function (response) {
                console.log("Respuesta del servidor:", response);
                if (response.status === "success" && response.cajas.length > 0) {
                    cargarCajasDisponibles(response.cajas);
                    $('#seleccionarCajaModal').modal('show');
                } else {
                    toastr.warning('No hay cajas activas abiertas disponibles para esta empresa.');
                }
            },
            error: function () {
                toastr.error('Error al conectar con el servidor para listar cajas.');
            }
        });
    }

    function cargarCajasDisponibles(cajas) {
        const cajaSelect = $('#cajaSelect');
        cajaSelect.empty();
        cajas.forEach(caja => {
            cajaSelect.append(new Option(`Caja ID: ${caja.cajaId} - Sucursal: ${caja.cajaAsignada}`, JSON.stringify({id: caja.cajaId, nombre: caja.cajaAsignada})));
        });
    }

    $('#btnSeleccionarCaja').click(function () {
        const selectedOption = JSON.parse($('#cajaSelect').val());

        if (!selectedOption || !selectedOption.id) {
            toastr.error('Seleccione una caja activa para continuar.');
            return;
        }

        $.ajax({
            url: '/kenpis/caja/seleccionar',
            method: 'POST',
            data: {cajaId: selectedOption.id, sucursalNombre: selectedOption.nombre},
            success: function () {
                $('#seleccionarCajaModal').modal('hide');
                cajaAbierta = selectedOption.id;
                console.log(`Caja seleccionada guardada en la sesión: ${selectedOption.id}`);
                $('#sucursalNombreLabel p').text(selectedOption.nombre);
                habilitarCampos();
            },
            error: function () {
                toastr.error('Error al guardar la caja seleccionada.');
            }
        });
    });


    //bloquear agregar pedido mientras no se complete los campos de nombre,alias,tipopago
    $('.btn-primary[data-target="#ventaModal"]').prop('disabled', true);

    // Función para bloquear los campos
    function bloquearCampos() {
        $('.btn-primary[data-target="#ventaModal"]').prop('disabled', true);
        $('#cliTelefono, #venTipoPago').prop('disabled', true);
    }

    // Función para habilitar los campos
    function habilitarCampos() {
        $('.btn-primary[data-target="#ventaModal"]').prop('disabled', false);
        $('#cliTelefono, #venTipoPago').prop('disabled', false);
    }

    verificarCamposRequeridos();

    function verificarCamposRequeridos() {
        const telefono = $('#cliTelefono').val().trim();
        const nombre = $('#cliNombre').val().trim();
        const tipoPago = $('#venTipoPago').val();

        if (telefono && nombre && tipoPago) {
            $('.btn-primary[data-target="#ventaModal"]').prop('disabled', false);
        } else {
            $('.btn-primary[data-target="#ventaModal"]').prop('disabled', true);
        }
    }

    $('#cliTelefono, #cliNombre, #venTipoPago').on('input change', verificarCamposRequeridos);

    function actualizarCampoAlias() {
        const telefono = $('#cliTelefono').val();
        if (telefono === '000000000') {
            $('#alias-field').show();
        } else {
            $('#alias-field').hide();
        }
    }

    actualizarCampoAlias();

    $('#cliTelefono').on('input', actualizarCampoAlias);

    //flujo para cargar la categoria, sub-categoria, detalle-producto

    $(document).on('click', '.card', function (e) {
        e.preventDefault();
        var categoriaId = $(this).data('id');
        if ($(this).hasClass('categoria-card')) {
            categoriaActual = categoriaId;
            cargarSubCategoria(categoriaId, empresaId);
        } else if ($(this).hasClass('subcategoria-card')) {
            var subCategoriaId = categoriaId;
            cargarDetalleProducto(subCategoriaId);
        }
    });


    function cargarCategorias() {
        console.log("Cargando categorías para empresa ID:", empresaId);
        if (!empresaId) {
            toastr.error('Empresa no seleccionada. No se pueden cargar las categorías.');
            return;
        }

        $.ajax({
            url: '/kenpis/producto/nuevaVenta-categorias',
            method: 'GET',
            data: {empId: empresaId},
            success: function (categorias) {
                console.log("Categorías recibidas:", categorias); // Verifica los datos recibidos
                var contenedor = $('#detalle-container');
                contenedor.empty(); // Limpia el contenedor antes de llenar con nuevas categorías

                if (categorias && categorias.length > 0) {
                    categorias.forEach(function (categoria) {
                        // Genera el HTML de cada tarjeta
                        var cardHtml = `
                    <div class="card categoria-card m-3" style="width: 15rem;" data-id="${categoria.proId}">
                        <img class="card-img-top categoria-img" src="${categoria.proImagen}" alt="Imagen de ${categoria.proCategoria}">
                        <div class="card-body text-center">
                            <h6 class="card-title font-weight-bold">${categoria.proCategoria}</h6>
                            <p class="card-text text-muted">${categoria.proDescripcion}</p>
                        </div>
                    </div>`;
                        console.log("Generando HTML para categoría:", categoria.proCategoria, cardHtml); // Verifica el HTML generado
                        contenedor.append(cardHtml); // Agrega el HTML al contenedor
                    });
                } else {
                    toastr.warning('No se encontraron categorías para la empresa.');
                }

                $('#guardarPedido').hide();
                $('#volverCategorias').hide();
                $('#volverSubCategorias').hide();
            },
            error: function (xhr, status, error) {
                console.error('Error al obtener categorías:', error);
                toastr.error('Error al obtener categorías:', error);
            }
        });
    }


    function cargarSubCategoria(categoria, empresaId) {
        $.ajax({
            url: '/kenpis/producto/find-all-by-type/' + categoria + '/' + empresaId,
            method: 'GET',
            success: function (subCategorias) {
                var contenedor = $('#detalle-container');
                contenedor.empty();
                subCategorias.forEach(function (producto) {
                    var cardHtml = `
                    <div class="card subcategoria-card m-3" style="width: 250px;" data-id="${producto.proId}" data-precio="${producto.proPrecioVenta}">
                        <img class="card-img-top producto-img" src="${producto.proImagen}" alt="Imagen de ${producto.proCategoria}">
                        <div class="card-body text-center">
                            <h6 class="card-title font-weight-bold">${producto.proCategoria}</h6>
                            <p class="card-text text-muted">${producto.proDescripcion}</p>
                            <p class="card-text text-primary font-weight-bold">Precio: S/ ${producto.proPrecioVenta.toFixed(2)}</p>
                        </div>
                    </div>`;
                    contenedor.append(cardHtml);
                });
                $('#volverCategorias').show();
                $('#volverSubCategorias').hide();
                $('#guardarPedido').hide();
            },
            error: function (xhr, status, error) {
                toastr.error('Error al obtener subcategorías:', error);
            }
        });
    }


    function cargarDetalleProducto(productoId) {
        $.ajax({
                url: '/kenpis/producto/find-by-id/' + productoId,
                method: 'GET',
                success: function (response) {
                    var producto = response.findById;
                    var complementos = response.complementos;
                    var contenedorDet = $('#detalle-container');
                    contenedorDet.empty();

                    var cardHtml = `
                <div class="d-flex flex-row justify-content-center align-items-start">
                    <!-- Columna del producto -->
                    <div class="card producto-card m-2" style="width: 300px;" data-id="${producto.proId}" data-precio="${producto.proPrecioVenta}">
                        <img alt="Producto" class="card-img-top producto-img" src="${producto.proImagen}" />
                        <div class="card-body text-center">
                            <h5 class="card-title font-weight-bold">${producto.proCategoria}</h5>
                            <p class="card-text-descripcion text-muted">${producto.proDescripcion}</p>
                            <p class="card-text font-weight-bold text-primary">Precio: S/ ${producto.proPrecioVenta.toFixed(2)}</p>
                        </div>
                        <div class="quantity-container">
                            <button class="quantity-button minus" type="button">-</button>
                            <span class="quantity-display">0</span>
                            <button id="agregarCantidadButton" class="quantity-button plus" type="button">+</button>
                        </div>
                    </div>
                     <!-- Columna de complemento -->
                  <div class="complementos-section mt-4">
                    <h6 class="font-weight-bold">Complementos</h6>
                    <div class="complementos-list">
                        ${complementos.map(complemento => `
                        <div class="complemento-item mb-3">
                            <label class="d-flex align-items-center">
                                <input type="checkbox" class="complemento-checkbox" data-complemento-id="${complemento.proCompId}">
                                <span class="complemento-label ml-2">${complemento.proCompNombre}</span>
                            </label>
                            <div class="subcomplementos-list" style="display: none; margin-left: 20px;">
                                ${complemento.subComplementos.split(',').map(sub => `
                                    <div class="subcomplemento-item mb-1 d-flex align-items-center">
                                        <input type="checkbox" data-subcomplemento="${sub.trim()}" id="sub-${sub.trim()}">
                                        <label for="sub-${sub.trim()}" class="subcomplemento-label ml-2">${sub.trim()}</label>
                                    </div>
                                `).join('')}
                            </div>
                        </div>`).join('')}
                    </div>
                  </div>
                </div>
                    `;
                    contenedorDet.append(cardHtml);
                    $('.complemento-checkbox').on('change', function () {
                        const $this = $(this);
                        const subcomplementosContainer = $this.closest('.complemento-item').find('.subcomplementos-list');

                        if ($this.is(':checked')) {
                            subcomplementosContainer.show();
                        } else {
                            subcomplementosContainer.hide();
                        }
                    });
                    $('#volverSubCategorias').show();
                    $('#volverCategorias').hide();
                    $('#guardarPedido').show();
                },
                error: function (xhr, status, error) {
                    toastr.error('Error al obtener el detalle del producto:', error);
                }
            }
        )
        ;
    }


//FUNCIONES PARA LOS CONTADORES
    $('#detalle-container').on('click', '.quantity-button.plus', function () {
        var quantityDisplay = $(this).siblings('.quantity-display');
        var currentQuantity = parseInt(quantityDisplay.text());
        var productoId = $(this).closest('.card').data('id'); // Obtener el ID del producto

        // Verificar stock antes de incrementar la cantidad
        verificarStock(productoId, currentQuantity + 1, function (stockSuficiente) {
            if (stockSuficiente) {
                quantityDisplay.text(currentQuantity + 1);
                $('#agregarCantidadButton').prop('disabled', false);
            } else {
                toastr.error("Stock insuficiente para la cantidad solicitada.");
                $('#agregarCantidadButton').prop('disabled', true);
            }
        });
    });

    $('#detalle-container').on('click', '.quantity-button.minus', function () {
        var quantityDisplay = $(this).siblings('.quantity-display');
        var currentQuantity = parseInt(quantityDisplay.text());
        if (currentQuantity > 1) {
            quantityDisplay.text(currentQuantity - 1);
            $('#agregarCantidadButton').prop('disabled', false);
        }
    });

    function verificarStock(productoId, cantidad, callback) {
        $.ajax({
            url: '/kenpis/inventario/verificar-stock',
            method: 'GET',
            data: {
                productoId: productoId,
                cantidad: cantidad
            },
            success: function (stockSuficiente) {
                callback(stockSuficiente);
            },
            error: function (xhr, status, error) {
                toastr.error("Error al verificar el stock:", error);
                callback(false);
            }
        });
    }


    $('#ventaModal').on('show.bs.modal', function () {
        cargarCategorias();
    });


    $('#volverCategorias').click(function () {
        cargarCategorias();
    });

    $('#volverSubCategorias').click(function () {
        cargarSubCategoria(categoriaActual, empresaId);
    });

    function actualizarTotal() {
        totalPagar = 0;
        $('#ventasBody tr').each(function () {
            const subtotal = parseFloat($(this).find('td').eq(4).text().replace('S/', '').replace(',', '.'));
            if (!isNaN(subtotal)) {
                totalPagar += subtotal;
            }
        });
        $('#totalPagar').text('S/' + totalPagar.toFixed(2));
    }

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
                cajaUsuarioApertura: usuarioId,
                cajaAsignada: sucursalNombre,
                cajaEstado: true
            }),
            success: function (response) {
                toastr.success('Caja abierta correctamente.');
                cajaAbierta = response.cajaId;
                $('#montoInicial').text(`Monto Inicial: S/ ${response.cajaMontoInicial.toFixed(2)}`);
                $('#aperturarCajaModal').modal('hide');
            },
            error: function () {
                toastr.error('Error al abrir la caja.');
            }
        });
    });

    $('#guardarPedido').click(function () {
        var productoId = $('.card[data-id]').data('id');
        var producto = $('.card[data-id]').find('.card-text-descripcion').text();
        var precio = $('.card[data-id]').data('precio');
        var cantidad = parseInt($('.quantity-display').text());
        var complementosSeleccionados = [];

        $('.complemento-checkbox:checked').each(function () {
            var complemento = $(this).data('complementoId');
            var subcomplementosSeleccionados = [];

            // Recorre los subcomplementos seleccionados para este complemento
            $(this).closest('.complemento-item').find('.subcomplemento-item input:checked').each(function () {
                subcomplementosSeleccionados.push($(this).data('subcomplemento'));
            });

            complementosSeleccionados.push({
                complemento: $(this).siblings('.complemento-label').text(),
                subcomplementos: subcomplementosSeleccionados.join(', ')
            });
        });

        if (cantidad > 0) {
            var subtotal = cantidad * precio;
            detallesVenta.push({
                productoId: productoId,
                proDescripcion: producto,
                venDetCantidad: cantidad,
                venDetPrecio: precio,
                venDetSubtotal: subtotal,
                venDetObservaciones: complementosSeleccionados.map(c => `${c.complemento}: ${c.subcomplementos}`).join('  ')
            });
        }
        $('#ventasBody').empty();
        var detallesHtml = detallesVenta.map(function (detalle) {
            return `<tr>
                        <td class="align-middle text-center">
                            <button class="btn btn-danger btn-sm eliminar-detalle">
                                <i class="fas fa-trash-alt"></i> Eliminar
                            </button>
                        </td>
                        <td class="align-middle">
                            <div><strong>${detalle.proDescripcion}</strong></div>
                            <small class="text-muted">${detalle.venDetObservaciones}  </small>
                        </td>
                        <td class="align-middle text-center">${detalle.venDetCantidad}</td>
                        <td class="align-middle text-right">S/ ${detalle.venDetPrecio}</td>
                        <td class="align-middle text-right font-weight-bold text-success">S/ ${detalle.venDetSubtotal}</td>
            </tr>`;
        }).join('');

        $('#ventasBody').append(detallesHtml);

        actualizarTotal();

        $('#ventaForm')[0].reset();
        verificarTabla();

        $('#ventaModal').modal('hide');
        $('.modal-backdrop').remove();
    });

    function verificarTabla() {
        if ($('#ventasBody tr').length > 0) {
            $('#pagarButton').prop('disabled', false);
        } else {
            $('#pagarButton').prop('disabled', true);
        }
    }

//PARA PODER ELIMINAR UN ELEMENTO DE LA TABLA
    $('#ventasBody').on('click', '.eliminar-detalle', function () {
        var row = $(this).closest('tr');
        var productoId = row.find('td').eq(0).text();
        detallesVenta = detallesVenta.filter(function (detalle) {
            return detalle.productoId != productoId;
        });
        row.remove();
        actualizarTotal();
        verificarTabla();
    });

    $('#pagarButton').prop('disabled', true);

// Deshabilitar el botón de ventas si no hay caja abierta
    $('.btn-primary[data-target="#ventaModal"]').prop('disabled', true);


// Escucha el evento de caja abierta desde el archivo de caja
    $(document).on("cajaAbierta", function (event, cajaId) {
        cajaAbierta = cajaId;
        $('.btn-primary[data-target="#ventaModal"]').prop('disabled', false);
    });


// Escucha el evento de caja cerrada para deshabilitar el botón de ventas
    $(document).on("cajaCerrada", function () {
        cajaAbierta = null;
        $('.btn-primary[data-target="#ventaModal"]').prop('disabled', true); // Deshabilita el botón de ventas
    });


    $('#pagarButton').click(function () {
        var clienteId = $('#clienteId').val();
        var tipoPago = $('#venTipoPago').val();
        if (!clienteId) {
            toastr.error('Por favor, seleccione un cliente.');
            return;
        }
        if (!tipoPago) {
            toastr.error('Por favor, seleccione un tipo de pago.');
            return;
        }
        if (totalPagar <= 0) {
            toastr.error('El total a pagar debe ser mayor a 0.');
            return;
        }

        // Muestra el monto total en el modal y abre el modal de confirmación
        $('#modalTotalPagar').text(totalPagar.toFixed(2));
        $('#confirmarPagoModal').modal('show');
    });


// Cuando el usuario confirma el pago en el modal
    $('#confirmarPagoButton').click(function () {
        var empresaId = $('#empresaId').val();
        var usuarioId = $('#usuarioId').val();
        var clienteId = $('#clienteId').val();
        var tipoPago = $('#venTipoPago').val();
        var telefono = $('#cliTelefono').val();
        var alias = telefono === '000000000' ? $('#alias').val() : null;

        // Obtén `cajaSeleccionada` de la sesión antes de proceder con el pago
        $.ajax({
            url: '/kenpis/caja/obtener-caja-seleccionada',
            method: 'GET',
            success: function (cajaSeleccionada) {
                if (!cajaSeleccionada) {
                    toastr.error('No puede realizar ventas sin una caja seleccionada.');
                    return;
                }

                // Procede con la solicitud de pago usando `cajaSeleccionada` de la sesión
                $.ajax({
                    url: '/kenpis/venta/create',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        empresaId: empresaId,
                        usuarioId: usuarioId.toString(),
                        clienteId: clienteId,
                        cajaId: cajaSeleccionada.cajaId,
                        sucursalId: sessionStorage.getItem('sucursalId'),
                        venAlias: alias,
                        detallesVentas: detallesVenta,
                        venTotal: totalPagar,
                        venTipoPago: tipoPago
                    }),
                    success: function (response) {
                        toastr.success('Pedido guardado correctamente.');
                        mostrarDetalleVenta();
                        $('#ventaForm')[0].reset();
                        $('#ventasBody').empty();
                        $('#cliNombre').val("");
                        $('#venTipoPago').val("");
                        $('#cliTelefono').val("");
                        $('#alias').val("");
                        totalPagar = 0;
                        detallesVenta = [];
                        $('#totalPagar').text('S/ 0.00');
                        verificarTabla();
                        $('#confirmarPagoModal').modal('hide');
                    },
                    error: function () {
                        toastr.error('Error al guardar el pedido. Intente nuevamente.');
                        $('#pagarButton').prop('disabled', false);
                    }
                });
            },
            error: function () {
                toastr.error('Error al verificar la caja seleccionada.');
            }
        });
    });

    function mostrarDetalleVenta() {
        let detalles = detallesVenta;
        let total = totalPagar; // Usa el total acumulado
        let vendedorNombre = $('#vendedorNombre').val();
        let horaPago = new Date().toLocaleString(); // Fecha y hora actual
        let tipoPago = $('#venTipoPago').val(); // Método de pago seleccionado

        // Datos de la empresa
        let empresaNombre = $('#empresaNombre').val();
        let empresaLogo = $('#empresalogo').val();
        let empresaRuc = $('#empresaRuc').val();
        let empresaTelefono = $('#empresaTelefono').val();
        let sucursalNombre = $('#sucursalNombreLabel p').text() || 'Sucursal no disponible';

        // Genera el HTML del voucher
        let voucherHtml = `
        <div class="voucher" style="font-family: Arial, sans-serif; font-size: 12px; line-height: 1.5; width: 300px; margin: auto; text-align: center;">
            <div style="text-align: center; margin-bottom: 10px;">
                <img src="${empresaLogo}" alt="Logo de Empresa" style="max-width: 300px; height: auto; margin-bottom: 10px;">
            </div>
            <div style="border-top: 1px solid #000; border-bottom: 1px solid #000; padding: 10px 0; margin-bottom: 10px;">
                <p style="margin: 0;"><strong>${empresaNombre}</strong></p>
                <p style="margin: 0;"><strong>Sucursal: ${sucursalNombre}</strong></p>
                <p style="margin: 0;">RUC: ${empresaRuc}</p>
                <p style="margin: 0;">Teléfono: ${empresaTelefono}</p>
            </div>
            <div style="text-align: left; margin-bottom: 5px;">
                <p><strong>Vendedor:</strong> ${vendedorNombre}</p>
                <p><strong>Fecha y Hora:</strong> ${horaPago}</p>
                <p><strong>Método de Pago:</strong> ${tipoPago}</p>
            </div>
            <div style="border-top: 1px solid #000; border-bottom: 1px solid #000; padding: 10px 0; margin-bottom: 10px;">
    ${detalles.map(item => `
                        <!-- Contenedor principal del producto -->
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 5px;">
                            <!-- Nombre del producto y cantidad -->
                            <p style="margin: 0;">
                                <strong>${item.proDescripcion}</strong> 
                                (${item.venDetCantidad} x S/ ${item.venDetPrecio.toFixed(2)})
                            </p>
                            <!-- Subtotal alineado a la derecha -->
                            <p style="margin: 0; text-align: right;">
                                Subtotal: S/ ${item.venDetSubtotal.toFixed(2)}
                            </p>
                        </div>
                        ${
                            item.venDetObservaciones && item.venDetObservaciones.length > 0
                                ? `
                                                <!-- Contenedor de observaciones -->
                                                <div style="margin-left: 10px; margin-top: 5px;">
                                                    ${item.venDetObservaciones.split('  ').map(obs => {
                                    const [complemento, subcomplementos] = obs.split(':');
                                    return `
                                                            <p style="margin: 0; font-weight: bold;">${complemento}:</p>
                                                            <ul style="margin: 0; padding-left: 20px; list-style-type: none;">
                                                                ${
                                        subcomplementos
                                            ? subcomplementos.split(',').map(sub => `<li>+ ${sub.trim()}</li>`).join('')
                                            : ''
                                    }
                                                            </ul>
                                                        `;
                                }).join('')}
                                                </div>
                                            `
                                : ''
                        }
                    `).join('')}
               </div>

            <div style="text-align: right; margin-bottom: 10px;">
                <p><strong>Total:</strong> S/ ${total.toFixed(2)}</p>
            </div>
            <div style="border-top: 1px solid #000; padding-top: 10px; text-align: center;">
                <p>¡MUCHAS GRACIAS POR SU PREFERENCIA!</p>
            </div>
            <div style="text-align: center; margin-top: 10px;">
                <button id="imprimirVoucher" style="background-color: #4CAF50; color: white; padding: 8px 16px; border: none; cursor: pointer;">
                    Imprimir Voucher
                </button>
            </div>
        </div>
    `;
        // Mostrar el voucher en el modal o en un contenedor
        $('#detalleVentaModal .modal-body').html(voucherHtml);
        $('#detalleVentaModal').modal('show');
    }


    $(document).on('click', '#imprimirVoucher', function () {
        var contenidoVoucher = document.querySelector('.voucher').outerHTML;
        var ventanaImpresion = window.open('', '', 'height=500, width=800');
        ventanaImpresion.document.write('<html><head><title>Voucher de Venta</title></head><body>');
        ventanaImpresion.document.write(contenidoVoucher);
        ventanaImpresion.document.write('</body></html>');
        ventanaImpresion.document.close();
        ventanaImpresion.print();
    });

    $('#registrarCliente').click(function () {
        var nombre = $('#cliNombrePopap').val();
        var telefono = $('#cliTelefonoNoRegistrado').val();
        var correo = $('#cliCorreoPopap').val();
        var empresaId = $('#empresaId').val();

        if (nombre && telefono) {
            $.ajax({
                url: '/kenpis/cliente/create',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    cliNombre: nombre,
                    cliTelefono: telefono,
                    cliNotificacion: true,
                    cliCorreo: correo,
                    empId: empresaId
                }),
                success: function (response) {
                    toastr.success('Cliente creado Exitosamente');
                    clienteId = response.cliId;
                    $('#clienteId').val(clienteId);
                    $('#cliNombrePopap').val('');
                    $('#cliTelefonoNoRegistrado').val('');
                    $('#cliCorreoPopap').val('correo@correo.com');
                    $('#clienteModal').modal('hide');
                    $('#cliNombre').prop('disabled', true);
                    $('#cliNombre').val(nombre);
                    $('#registrarCliente').hide();
                },
                error: function () {
                    toastr.error('Error al registrar el cliente. Intente nuevamente.');
                }
            });
        } else {
            toastr.error('Por favor, ingrese el nombre y el teléfono.');
        }
    });

    $('#cliTelefono').on('input', function () {
        var telefono = $(this).val();
        actualizarCampoAlias();

        if (telefono.length === 9) {
            $.ajax({
                url: '/kenpis/cliente/find-by-telefono/' + telefono,
                method: 'GET',
                success: function (response) {
                    if (response.cliId != null) {
                        $('#cliNombre').val(response.cliNombre);
                        $('#clienteId').val(response.cliId);
                        $('#cliNombre').prop('disabled', true);
                        $('#registrarCliente').hide();
                    } else {
                        $('#registrarCliente').show();
                        $('#cliNombre').val('');
                        $('#cliNombre').prop('disabled', false);
                        $('#cliTelefonoNoRegistrado').val(telefono);
                        $('#clienteModal').modal('show');
                    }
                },
                error: function () {
                    toastr.error('Error al buscar el cliente. Intente nuevamente.');
                }
            });
        } else if (telefono.length > 9) {
            toastr.error('Ingrese un número de teléfono válido (9 dígitos).');
        }
    });

});