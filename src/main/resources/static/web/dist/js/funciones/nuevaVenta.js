$(document).ready(function () {
    let totalPagar;
    let detallesVenta = [];
    let clienteId = null;
    actualizarTotal();

    $('#ventaModal').on('show.bs.modal', function () {
        $.ajax({
            url: '/kenpis/producto/categorias',
            method: 'GET',
            success: function (categorias) {
                var categoriaSelect = $('#categoria');
                categoriaSelect.empty().append('<option value="">-- Seleccione --</option>');
                categorias.forEach(function (categoria) {
                    categoriaSelect.append('<option value="' + categoria.proId + '">' + categoria.proCategoria + '</option>');
                });
            },
            error: function (xhr, status, error) {
                console.error('Error al obtener categorías:', error);
            }
        });
    });
    // Cuando cambia la categoría de chorizo
    $('#categoria').change(function () {
        var categoriaId = $(this).val();
        cargarProductos(categoriaId);
    });


    function cargarProductos(categoria) {
        $.ajax({
            url: '/kenpis/producto/find-all-by-type/' + categoria,
            method: 'GET',
            success: function (subCategoria) {
                var subCategoriaSelect = $('#subCategoria');
                subCategoriaSelect.empty().append('<option value="">-- Seleccione --</option>');
                subCategoria.forEach(function (producto) {
                    subCategoriaSelect.append('<option value="' + producto.proId + '" data-precio="' + producto.proPrecio + '">' + producto.proCategoria + '</option>');
                });
            }
        });
    }

    $('#subCategoria').change(function () {
        var precio = $(this).find('option:selected').data('precio');
        $('#precioProducto').val(precio);
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

    // Guardar el pedido
    $('#guardarPedido').click(function () {

        var subCategoria = $('.subCategoria-select').find('option:selected').text();
        var subCategoriaId = $('#subCategoria').val();
        var precio = $('.subCategoria-select').find('option:selected').data('precio');
        var cantidad = $('#cantidad').val();
        //alert("Detalle categoria :: " + subCategoria + " - " + cantidad + " - " + precio);
        if (cantidad > 0) {
            var subtotal = cantidad * precio;
            detallesVenta.push({
                productoId: subCategoriaId,
                proCategoria: subCategoria,
                venDetCantidad: cantidad,
                venDetSubtotal: subtotal.toFixed(2),
                venDetPrecio: precio.toFixed(2)
            });
        }
        $('#ventasBody').empty();

        var detallesHtml = detallesVenta.map(function (detalle) {

            return '<tr>' +
                '<td>' + detalle.productoId + '</td>' +
                '<td>' + detalle.proCategoria + '</td>' +
                '<td>' + detalle.venDetCantidad + '</td>' +
                '<td>S/ ' + detalle.venDetPrecio + '</td>' +
                '<td>S/ ' + detalle.venDetSubtotal + '</td>' +
                '</tr>';
        }).join('');

        $('#ventasBody').append(detallesHtml);
        actualizarTotal();
        $('#ventaForm')[0].reset();
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

    // Procesar el pago
    $('#pagarButton').click(function () {
        var empresaId = $('#empresaId').val();
        var usuarioId = $('#usuarioId').val();
        var clienteId = $('#clienteId').val();
        var tipoPago = $('#venTipoPago').val();

        console.log("detalles de la venta " + detallesVenta);

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
                // Limpiar el formulario y las tablas
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





