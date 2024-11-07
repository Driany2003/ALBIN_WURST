$(document).ready(function () {
    var empresaId = $('#empresaId').val();
    let totalPagar;
    let detallesVenta = [];
    let clienteId = null;
    let categoriaActual = null;
    actualizarTotal();
    ocultarCampoAlias();

    $('#aperturarCajaModal').on('show.bs.modal', function () {
    });

    $(document).on('click', '.card', function (e) {
        e.preventDefault();
        var categoriaId = $(this).data('id');
        if ($(this).hasClass('categoria-card')) {
            categoriaActual = categoriaId;
            cargarSubCategoria(categoriaId,empresaId);
        } else if ($(this).hasClass('subcategoria-card')) {
            var subCategoriaId = categoriaId;
            cargarDetalleProducto(subCategoriaId);
        }
    });

    function mostrarCampoAlias() {
        $('#alias-label').show();
        $('#alias-field').show();
    }

    function ocultarCampoAlias() {
        $('#alias-label').hide();
        $('#alias-field').hide();
    }

    function actualizarCampoAlias() {
        let telefono = $('#cliTelefono').val();
        if (telefono === '000000000') {
            mostrarCampoAlias();
        } else {
            ocultarCampoAlias();
        }
    }

    function cargarCategorias() {
        $.ajax({
            url: '/kenpis/producto/nuevaVenta-categorias',
            method: 'GET',
            data: {empId: empresaId},
            success: function (categorias) {
                var contenedor = $('#detalle-container');
                contenedor.empty();
                categorias.forEach(function (categoria) {
                    var cardHtml = '<div class="card categoria-card m-2" style="width: 18rem;" data-id="' + categoria.proId + '">' +
                        '<img alt="Producto" height="50" width="50" class="card-img-top" alt="No se pudo mostrar la imagen" src="' + categoria.proImagen + '"/>' +
                        '<div class="card-body">' +
                        '<h5 class="card-title">' + categoria.proCategoria + '</h5>' +
                        '<p class="card-text">' + categoria.proDescripcion + '</p>' +
                        '</div></div>';
                    contenedor.append(cardHtml);
                });
                $('#guardarPedido').hide();
                $('#volverCategorias').hide();
                $('#volverSubCategorias').hide();
            },
            error: function (xhr, status, error) {
                console.error('Error al obtener categorías:', error);
            }
        });
    }

    function cargarSubCategoria(categoria,empresaId) {
        $.ajax({
            url: '/kenpis/producto/find-all-by-type/' + categoria + '/' + empresaId,
            method: 'GET',
            success: function (subCategorias) {
                var contenedor = $('#detalle-container');
                contenedor.empty();
                subCategorias.forEach(function (producto) {
                    var cardHtml = '<div class="card subcategoria-card m-2" style="width: 18rem;" data-id="' + producto.proId + '" data-precio="' + producto.proPrecioVenta + '">' +
                        '<img alt="Producto" height="50px" width="50px" class="card-img-top" src="' + producto.proImagen + '"/>' +
                        '<div class="card-body">' +
                        '<h5 class="card-title">' + producto.proCategoria + '</h5>' +
                        '<p class="card-text">' + producto.proDescripcion + '</p>' +
                        '<p class="card-text">Precio: S/ ' + producto.proPrecioVenta.toFixed(2) + '</p>' +
                        '</div></div>';
                    contenedor.append(cardHtml);
                });
                $('#volverCategorias').show();
                $('#volverSubCategorias').hide();
                $('#guardarPedido').hide();
            },
            error: function (xhr, status, error) {
                console.error('Error al obtener subcategorías:', error);
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

                // Estructura del contenedor principal, dividida en dos columnas: producto y complementos
                var cardHtml = `
                <div class="d-flex flex-row">
                    <!-- Columna del producto -->
                    <div class="card m-2" style="width: 18rem;" data-id="${producto.proId}" data-precio="${producto.proPrecioVenta}">
                        <img alt="Producto" height="150px" width="100%" class="card-img-top" src="${producto.proImagen}"/>
                        <div class="card-body">
                            <h5 class="card-title">${producto.proCategoria}</h5>
                            <p class="card-text-descripcion">${producto.proDescripcion}</p>
                            <p class="card-text">Precio: S/ ${producto.proPrecioVenta.toFixed(2)}</p>
                        </div>
                        <div class="quantity-container">
                            <button class="quantity-button minus" type="button">-</button>
                            <span class="quantity-display">0</span>
                            <button id="agregarCantidadButton" class="quantity-button plus" type="button">+</button>
                        </div>
                    </div>

                    <!-- Columna de complementos en el costado derecho -->
                    <div class="complementos-section ml-4">
                        <h6>Complementos</h6>
                        ${complementos.map(complemento => `
                            <div class="complemento-item mb-2">
                                <label>
                                    <input type="checkbox" class="complemento-checkbox" data-id="${complemento.proCompId}" data-nombre="${complemento.proCompNombre}" > 
                                    ${complemento.proCompNombre}
                                </label>
                            </div>
                        `).join('')}
                    </div>
                </div>
            `;
                contenedorDet.append(cardHtml);
                $('#volverSubCategorias').show();
                $('#volverCategorias').hide();
                $('#guardarPedido').show();
            },
            error: function (xhr, status, error) {
                console.error('Error al obtener el detalle del producto:', error);
            }
        });
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
                console.error("Error al verificar el stock:", error);
                callback(false);
            }
        });
    }


    //redireccionar botones VOLVER
    $('#ventaModal').on('show.bs.modal', function () {
        cargarCategorias();
    });

    $('#volverCategorias').click(function () {
        cargarCategorias();
    });

    $('#volverSubCategorias').click(function () {
        cargarSubCategoria(categoriaActual,empresaId);
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

    $('#guardarPedido').click(function () {
        var productoId = $('.card[data-id]').data('id');
        var producto = $('.card[data-id]').find('.card-text-descripcion').text();
        var precio = $('.card[data-id]').data('precio');
        var cantidad = parseInt($('.quantity-display').text());
        var complementosSeleccionados = [];

        $('.complemento-checkbox:checked').each(function () {
            complementosSeleccionados.push($(this).data('nombre'));
        });

        if (cantidad > 0) {
            var subtotal = cantidad * precio;
            detallesVenta.push({
                productoId: productoId,
                proDescripcion: producto,
                venDetCantidad: cantidad,
                venDetPrecio: precio,
                venDetSubtotal: subtotal,
                venDetObservaciones: complementosSeleccionados.join(', ')

            });
        }
        $('#ventasBody').empty();
        var detallesHtml = detallesVenta.map(function (detalle) {
            return `<tr>
        <td class="align-middle text-center font-weight-bold">${detalle.productoId}</td>
        <td class="align-middle">
            <div><strong>${detalle.proDescripcion}</strong></div>
            <small class="text-muted">${detalle.venDetObservaciones}</small>
        </td>
        <td class="align-middle text-center">${detalle.venDetCantidad}</td>
        <td class="align-middle text-right">S/ ${detalle.venDetPrecio}</td>
        <td class="align-middle text-right font-weight-bold text-success">S/ ${detalle.venDetSubtotal}</td>
        <td class="align-middle text-center">
            <button class="btn btn-danger btn-sm eliminar-detalle">
                <i class="fas fa-trash-alt"></i> Eliminar
            </button>
        </td>
    </tr>`;
        }).join('');
        $('#ventasBody').append(detallesHtml);

        actualizarTotal();
        $('#ventaForm')[0].reset();
        verificarTabla();

        $('#ventaModal').modal('hide');
        $('.modal-backdrop').remove();
    });

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

    function verificarTabla() {
        if ($('#ventasBody tr').length > 0) {
            $('#pagarButton').prop('disabled', false);
        } else {
            $('#pagarButton').prop('disabled', true);
        }
    }

    $('#pagarButton').click(function () {
        var empresaId = $('#empresaId').val();
        var usuarioId = $('#usuarioId').val();
        var clienteId = $('#clienteId').val();
        var tipoPago = $('#venTipoPago').val();
        var telefono = $('#cliTelefono').val();

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
        if (!confirm('¿Está seguro de que desea guardar este pedido?')) {
            return;
        }

        if (telefono === '000000000') {
            var alias = $('#alias').val();
        }

        $.ajax({
            url: '/kenpis/venta/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                empresaId: empresaId,
                usuarioId: usuarioId,
                clienteId: clienteId,
                venAlias: alias,
                detallesVentas: detallesVenta,
                venTotal: totalPagar,
                venTipoPago: tipoPago

            }),
            success: function (response) {
                toastr.success('Pedido guardado correctamente.');
                $('#cliTelefono').val("");
                $('#cliNombre').val("");
                $('#alias').val("");
                $('#clienteId').val("");
                $('#venTipoPago').val("");
                $('#ventaModal').modal('hide');
                $('#ventaForm')[0].reset();
                $('#ventasBody').empty();
                $('#productos-container').empty();
                totalPagar = 0;
                $('#totalPagar').text('S/ 0.00');
                detallesVenta = [];
                verificarTabla();
            },
            error: function () {
                toastr.error('Error al guardar el pedido. Intente nuevamente.');
                $('#pagarButton').prop('disabled', false);
            }
        });
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

    $('#buscarCliente').click(function () {
        var telefono = $('#cliTelefono').val();
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
        } else {
            toastr.error('Ingrese un número de teléfono válido.');
        }
    });
});