$(document).ready(function () {
    let totalPagar;
    const currentUserId = 1;
    let detallesVenta = [];
    let clienteId = null;
    actualizarTotal();

    $('#ventaModal').on('show.bs.modal', function () {
        // Obtener categorías y llenar el selector
        $.ajax({
            url: '/kenpis/producto/categorias',
            method: 'GET',
            success: function (categorias) {
                var bebidaSelect = $('#tipoBebida');
                var chorizoSelect = $('#tipoChorizo');

                bebidaSelect.empty().append('<option value="">-- Seleccione --</option>');
                chorizoSelect.empty().append('<option value="">-- Seleccione --</option>');

                categorias.forEach(function (categoria) {
                    if (categoria !== 'Bebida') {
                        chorizoSelect.append('<option value="' + categoria + '">' + categoria + '</option>');
                    } else {
                        bebidaSelect.append('<option value="' + categoria + '">' + categoria + '</option>');
                    }
                });
            }
        });
    });

    // Cuando cambia la categoría de bebida
    $('#tipoBebida').change(function () {
        cargarProductos($(this).val());
    });

    // Cuando cambia la categoría de chorizo
    $('#tipoChorizo').change(function () {
        cargarProductos($(this).val());
    });

    function cargarProductos(categoria) {
        $.ajax({
            url: '/kenpis/producto/find-all-by-type/' + categoria,
            method: 'GET',
            success: function (productos) {
                var productoSelect = (categoria === 'Bebida') ? $('#bebida') : $('#chorizo');
                productoSelect.empty().append('<option value="">-- Seleccione --</option>');

                productos.forEach(function (producto) {
                    productoSelect.append('<option value="' + producto.proId + '" data-precio="' + producto.proPrecio + '">' + producto.proTipo + '</option>');
                });
            }
        });
    }

    // Añadir un nuevo chorizo
    $('#addChorizo').click(function () {
        const chorizoId = `chorizo-${Date.now()}`;
        const cantidadChorizosId = `cantidadChorizos-${Date.now()}`;
        $('#chorizos-container').append(`
            <div class="form-group">
                <label for="${chorizoId}" class="form-label">Sabor de Chorizo</label>
                <select id="${chorizoId}" class="form-select form-select-sm chorizo-select">
                    <option value="">-- Seleccione --</option>
                </select>
            </div>
            <div class="form-group">
                <label for="${cantidadChorizosId}">Cantidad de Chorizos</label>
                <input id="${cantidadChorizosId}" type="number" class="form-control form-control-sm cantidad-chorizo" placeholder="Cantidad" min="1">
            </div>
        `);
        cargarProductos('Chorizo');
    });

    // Añadir una nueva bebida
    $('#addBebida').click(function () {
        const bebidaId = `bebida-${Date.now()}`;
        const cantidadBebidasId = `cantidadBebidas-${Date.now()}`;
        $('#bebidas-container').append(`
            <div class="form-group">
                <label for="${bebidaId}" class="form-label">Bebida</label>
                <select id="${bebidaId}" class="form-select form-select-sm bebida-select">
                    <option value="">-- Seleccione --</option>
                </select>
            </div>
            <div class="form-group">
                <label for="${cantidadBebidasId}">Cantidad de Bebidas</label>
                <input id="${cantidadBebidasId}" type="number" class="form-control form-control-sm cantidad-bebida" placeholder="Cantidad" min="1">
            </div>
        `);
        cargarProductos('Bebida');
    });

    function actualizarTotal() {
        totalPagar = 0;
        $('#ventasBody tr').each(function () {
            const subtotal = parseFloat($(this).find('td').eq(3).text().replace('S/', '').replace(',', '.').replace(',', '.'));
            if (!isNaN(subtotal)) {
                totalPagar += subtotal;
            }
        });
        $('#totalPagar').text('S/' + totalPagar.toFixed(2));
    }

    // Guardar el pedido
    $('#guardarPedido').click(function () {
        detallesVenta = [];

        $('.chorizo-select').each(function () {
            var chorizoData = $(this).val();
            if (chorizoData) {
                var chorizo = $(this).find('option:selected').text();
                var cantidadChorizos = $(this).closest('.form-group').next().find('.cantidad-chorizo').val();
                var precioChorizo = $(this).find('option:selected').data('precio');
                if (cantidadChorizos > 0) {
                    var subtotalChorizo = cantidadChorizos * precioChorizo;
                    detallesVenta.push({
                        producto: chorizo,
                        venDetCantidad: cantidadChorizos,
                        venDetSubtotal: subtotalChorizo.toFixed(2),
                        venDetPrecio: precioChorizo.toFixed(2)
                    });
                }
            }
        });

        $('.bebida-select').each(function () {
            var bebidaData = $(this).val();
            if (bebidaData) {
                var bebida = $(this).find('option:selected').text();
                var cantidadBebidas = $(this).closest('.form-group').next().find('.cantidad-bebida').val();
                var precioBebida = $(this).find('option:selected').data('precio');
                if (cantidadBebidas > 0) {
                    var subtotalBebida = cantidadBebidas * precioBebida;
                    detallesVenta.push({
                        producto: bebida,
                        venDetCantidad: cantidadBebidas,
                        venDetSubtotal: subtotalBebida.toFixed(2),
                        venDetPrecio: precioBebida.toFixed(2)
                    });
                }
            }
        });

        var detallesHtml = detallesVenta.map(function (detalle) {
            return '<tr>' +
                '<td>' + detalle.producto + '</td>' +
                '<td>' + detalle.venDetCantidad + '</td>' +
                '<td>S/ ' + detalle.venDetPrecio + '</td>' +
                '<td>S/ ' + detalle.venDetSubtotal + '</td>' +
                '</tr>';
        }).join('');

        $('#ventasBody').append(detallesHtml);
        actualizarTotal();
        $('#ventaForm')[0].reset();
    });

    // Procesar el pago
    $('#pagarButton').click(function () {
        $.ajax({
            url: '/kenpis/venta/guardar',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                usuarioId: currentUserId,
                clienteId: clienteId,
                detalles: detallesVenta,
                total: totalPagar
            }),
            success: function (response) {
                alert('Pedido guardado correctamente.');
                $('#ventaModal').modal('hide');
                // Limpiar el formulario y las tablas
                $('#ventaForm')[0].reset();
                $('#ventasBody').empty();
                $('#chorizos-container').empty();
                $('#bebidas-container').empty();
                totalPagar = 0;
                $('#totalPagar').text('S/ 0.00');
                detallesVenta = [];
            },
            error: function () {
                alert('Error al guardar el pedido. Intente nuevamente.');
            }
        });
    });


    $('#registrarCliente').click(function() {
        var nombre = $('#cliNombrePopap').val();
        var telefono = $('#cliTelefonoNoRegistrado').val();
        if (nombre && telefono) {
            $.ajax({
                url: '/kenpis/cliente/create',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    cliNombre: nombre,
                    cliTelefono: telefono
                }),
                success: function (response) {
                    clienteId = response.cliId;
                    $('#clienteModal').modal('hide');
                    $('#cliNombre').prop('disabled', true);
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
                    //alert('Cliente ID : ' + response.cliId);
                    if (response.cliId != null) {
                        $('#cliNombre').val(response.cliNombre);
                        $('#cliNombre').prop('disabled', true);
                    } else {
<<<<<<< HEAD
                        console.log("hola");
                        $('#registrarCliente').show();
                        $('#cliNombre').val('');
                        $('#cliNombre').prop('disabled', true);
=======
                        $('#registrarCliente').show();
                        $('#cliNombre').val('');
                        $('#cliNombre').prop('disabled', false);
>>>>>>> 2baeb7a85d196e153dc5fa6c5a3ffcbf469fe1cb
                        $('#cliTelefonoNoRegistrado').val(telefono);
                        $('#clienteModal').modal('show'); // Mostrar el modal
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


