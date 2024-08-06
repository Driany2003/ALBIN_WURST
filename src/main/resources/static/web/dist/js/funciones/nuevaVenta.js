$(document).ready(function () {
    let totalPagar;
    let detallesVenta = [];
    let clienteId = null;
    let categoriaActual = null;
    actualizarTotal();

    $(document).on('click', '.select-categoria', function (e) {
        e.preventDefault();
        var categoriaId = $(this).closest('.card').data('id');
        categoriaActual = categoriaId;
        cargarSubCategoria(categoriaId);
    });
    function cargarCategorias() {
        $.ajax({
            url: '/kenpis/producto/categorias',
            method: 'GET',
            success: function (categorias) {
                var contenedor = $('#detalle-container');
                contenedor.empty();
                categorias.forEach(function (categoria) {
                    var cardHtml = '<div   class="card m-2" style="width: 18rem;" data-id="' + categoria.proId + '">' +
                        '<div class="card-body">' +
                        '<h5 class="card-title">' + categoria.proCategoria + '</h5>' +
                        '<p class="card-text">' + categoria.proDescripcion + '</p>' +
                        '<img alt="Producto" class="img-responsive" src="data:image/jepg;base64,'+categoria.proImagen+'"/>'+
                        '<a href="#" class="btn btn-primary select-categoria">Seleccionar</a>' +
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

    $(document).on('click', '.select-subcategoria', function (e) {
        e.preventDefault();
        var subCategoriaId = $(this).closest('.card').data('id');
        cargarDetalleProducto(subCategoriaId);
    });

    function cargarSubCategoria(categoria) {
        $.ajax({
            url: '/kenpis/producto/find-all-by-type/' + categoria,
            method: 'GET',
            success: function (subCategorias) {
                var contenedor = $('#detalle-container');
                contenedor.empty();
                subCategorias.forEach(function (producto) {
                    var cardHtml = '<div class="card m-2" style="width: 18rem;" data-id="' + producto.proId + '" data-precio="' + producto.proPrecio + '">' +
                        '<div class="card-body">' +
                        '<h5 class="card-title">' + producto.proCategoria + '</h5>' +
                        '<p class="card-text">' + producto.proDescripcion + '</p>' +
                        '<img alt="Producto" class="img-responsive" src="data:image/jepg;base64,'+producto.proImagen+'"/>'+
                        '<p class="card-text">Precio: S/ ' + producto.proPrecio.toFixed(2) + '</p>' +
                        '<a href="#" class="btn btn-primary select-subcategoria">Seleccionar</a>' +
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

    $(document).on('click', '.select-subcategoria', function (e) {
        e.preventDefault();
        var subCategoriaId = $(this).closest('.card').data('id');
        var precio = $(this).closest('.card').data('precio');
        $('#subCategoria').val(subCategoriaId);
        $('#precioProducto').val(precio);
    });

    function cargarDetalleProducto(productoId) {

        $.ajax({
            url: '/kenpis/producto/find-by-id/' + productoId,
            method: 'GET',
            success: function (producto) {
                var contenedorDet = $('#detalle-container');
                contenedorDet.empty();
                var cardHtml = '<div class="card m-2" style="width: 18rem;" data-id="' + producto.proId + '" data-precio="' + producto.proPrecio + '">' +
                    '<div class="card-body">' +
                    '<h5 class="card-title">' + producto.proCategoria + '</h5>' +
                    '<p class="card-text-descripcion">' + producto.proDescripcion + '</p>' +
                    '<img alt="Producto" class="img-responsive" src="data:image/jepg;base64,'+producto.proImagen+'"/>'+
                    '<p class="card-text">Precio: S/ ' + producto.proPrecio.toFixed(2) + '</p>' +
                    '</div>' +
                    '<div class="quantity-container">' +
                    '<button class="quantity-button minus" type="button">-</button>' +
                    '<span class="quantity-display">1</span> ' +
                    ' <button class="quantity-button plus" type="button">+</button>' +
                    '</div>' +
                    '</div>';
                contenedorDet.append(cardHtml);
                $('#volverSubCategorias').show();
                $('#volverCategorias').hide();
                $('#guardarPedido').show();

            },
            error: function (xhr, status, error) {
                console.error('Error al obtener subcategorías:', error);
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
        cargarSubCategoria(categoriaActual);
    });

    //FUNCIONES PARA LOS CONTADORES
    $('#detalle-container').on('click', '.quantity-button.plus', function () {
        var quantityDisplay = $(this).siblings('.quantity-display');
        var currentQuantity = parseInt(quantityDisplay.text());
        quantityDisplay.text(currentQuantity + 1);
    });

    $('#detalle-container').on('click', '.quantity-button.minus', function () {
        var quantityDisplay = $(this).siblings('.quantity-display');
        var currentQuantity = parseInt(quantityDisplay.text());
        if (currentQuantity > 1) {
            quantityDisplay.text(currentQuantity - 1);
        }
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
        if (cantidad > 0) {
            var subtotal = cantidad * precio;
            detallesVenta.push({
                productoId: productoId,
                proDescripcion: producto,
                venDetCantidad: cantidad,
                venDetSubtotal: subtotal.toFixed(2),
                venDetPrecio: precio.toFixed(2)
            });
        }
        $('#ventasBody').empty();
        var detallesHtml = detallesVenta.map(function (detalle) {
            return '<tr>' +
                '<td>' + detalle.productoId + '</td>' +
                '<td>' + detalle.proDescripcion + '</td>' +
                '<td>' + detalle.venDetCantidad + '</td>' +
                '<td>S/ ' + detalle.venDetPrecio + '</td>' +
                '<td>S/ ' + detalle.venDetSubtotal + '</td>' +
                '<td><button class="btn btn-danger btn-sm eliminar-detalle">Eliminar</button></td>' +
                '</tr>';
        }).join('');
        $('#ventasBody').append(detallesHtml);
        actualizarTotal();
        $('#ventaForm')[0].reset();
        verificarTabla();
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

        $.ajax({
            url: '/kenpis/venta/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                empresaId: empresaId,
                usuarioId: usuarioId,
                clienteId: clienteId,
                detallesVentas: detallesVenta,
                venTotal: totalPagar,
                venTipoPago: tipoPago
            }),
            success: function (response) {
                console.log(response);
                alert('Pedido guardado correctamente.');
                $('#cliTelefono').val("");
                $('#cliNombre').val("");
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
                alert('Error al guardar el pedido. Intente nuevamente.');
                $('#pagarButton').prop('disabled', false);
            }
        });
    });

    $('#registrarCliente').click(function () {
        var nombre = $('#cliNombrePopap').val();
        var telefono = $('#cliTelefonoNoRegistrado').val();
        var correo = $('#cliCorreoPopap').val();

        if (nombre && telefono) {
            $.ajax({
                url: '/kenpis/cliente/create',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    cliNombre: nombre,
                    cliTelefono: telefono,
                    cliNotificacion: true,
                    cliCorreo: correo
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
                    alert('Error al registrar el cliente. Intente nuevamente.');
                }
            });
        } else {
            alert('Por favor, ingrese el nombre y el teléfono.');
        }
    });

    $('#buscarCliente').click(function () {
        var telefono = $('#cliTelefono').val();
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
                    alert('Error al buscar el cliente. Intente nuevamente.');
                }
            });
        } else {
            alert('Ingrese un número de teléfono válido.');
        }
    });
});





