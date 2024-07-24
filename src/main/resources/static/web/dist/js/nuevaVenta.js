$(document).ready(function () {
    let totalPagar = 0;

    function SeleccionarOpciones(selectClass, tipo) {
        $.ajax({
            type: 'GET',
            url: '/kenpis/producto/find-all-by-type/' + tipo,
            success: function (response) {
                $('.' + selectClass).each(function () {
                    $(this).empty();
                    $(this).append($('<option>', { text: 'Elige una opción', value: '' }));
                    response.forEach(function (producto) {
                        $(this).append($('<option>', {
                            value: JSON.stringify({ proId: producto.proId, proTipo: producto.proTipo, proPrecio: producto.proPrecio }),
                            text: producto.proTipo,
                            'data-precio': producto.proPrecio
                        }));
                    }.bind(this));
                });
            },
            error: function (error) {
                console.error('Error al obtener los productos:', error);
            }
        });
    }

    SeleccionarOpciones('chorizo-select', 'chorizo');
    SeleccionarOpciones('bebida-select', 'bebida');

    $('#addChorizo').click(function () {
        const chorizoId = `chorizo-${Date.now()}`;
        const cantidadChorizosId = `cantidadChorizos-${Date.now()}`;
        $('#chorizos-container').append(`
            <div class="form-group">
                <label for="${chorizoId}" class="control-label col-form-label">Sabor de Chorizo</label>
                <select id="${chorizoId}" class="form-control form-control-sm chorizo-select">
                    <option value="">-- Seleccione --</option>
                </select>
            </div>
            <div class="form-group">
                <label for="${cantidadChorizosId}">Cantidad de Chorizos</label>
                <input id="${cantidadChorizosId}" type="number" class="form-control form-control-sm cantidad-chorizo" placeholder="Cantidad" min="1">
            </div>
        `);
        SeleccionarOpciones('chorizo-select', 'chorizo');
    });

    $('#addBebida').click(function () {
        const bebidaId = `bebida-${Date.now()}`;
        const cantidadBebidasId = `cantidadBebidas-${Date.now()}`;
        $('#bebidas-container').append(`
            <div class="form-group">
                <label for="${bebidaId}" class="control-label col-form-label">Bebida</label>
                <select id="${bebidaId}" class="form-control form-control-sm bebida-select">
                    <option value="">-- Seleccione --</option>
                </select>
            </div>
            <div class="form-group">
                <label for="${cantidadBebidasId}">Cantidad de Bebidas</label>
                <input id="${cantidadBebidasId}" type="number" class="form-control form-control-sm cantidad-bebida" placeholder="Cantidad" min="1">
            </div>
        `);
        SeleccionarOpciones('bebida-select', 'bebida');
    });

    function actualizarTotal() {
        totalPagar = 0;
        $('#ventasBody tr').each(function () {
            const subtotal = parseFloat($(this).find('td').eq(3).text().replace('S/.', ''));
            if (!isNaN(subtotal)) {
                totalPagar += subtotal;
            }
        });
        $('#totalPagar').text('S/.' + totalPagar.toFixed(2));
    }

    $('#guardarPedido').click(function () {
        var detalles = [];

        $('.chorizo-select').each(function () {
            var chorizoData = $(this).val();
            if (chorizoData) {
                var chorizo = JSON.parse(chorizoData);
                var cantidadChorizos = $(this).closest('.form-group').next().find('.cantidad-chorizo').val();
                var precioChorizo = $(this).find('option:selected').data('precio');
                if (cantidadChorizos > 0) {
                    var subtotalChorizo = cantidadChorizos * precioChorizo;
                    detalles.push({
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
                var bebida = JSON.parse(bebidaData);
                var cantidadBebidas = $(this).closest('.form-group').next().find('.cantidad-bebida').val();
                var precioBebida = $(this).find('option:selected').data('precio');
                if (cantidadBebidas > 0) {
                    var subtotalBebida = cantidadBebidas * precioBebida;
                    detalles.push({
                        producto: bebida,
                        venDetCantidad: cantidadBebidas,
                        venDetSubtotal: subtotalBebida.toFixed(2),
                        venDetPrecio: precioBebida.toFixed(2)
                    });
                }
            }
        });

        var detallesHtml = detalles.map(function (detalle) {
            return '<tr>' +
                '<td data-producto=\'' + JSON.stringify(detalle.producto) + '\'>' + (detalle.producto ? detalle.producto.proTipo : 'N/A') + '</td>' +
                '<td>' + detalle.venDetCantidad + '</td>' +
                '<td>' + detalle.venDetPrecio + '</td>' +
                '<td>' + 'S/.' + detalle.venDetSubtotal + '</td>' +
                '</tr>';
        }).join('');

        $('#ventasBody').append(detallesHtml);
        actualizarTotal();
        $('#ventaForm')[0].reset();
    });

    $('#pagarButton').click(function () {
        var detalles = [];

        $('#ventasBody tr').each(function () {
            var productoData = $(this).find('td').eq(0).attr('data-producto');
            if (productoData) {
                try {
                    var producto = JSON.parse(productoData);
                    var cantidad = $(this).find('td').eq(1).text();
                    var precio = $(this).find('td').eq(2).text().replace('S/.', '');
                    var subtotal = $(this).find('td').eq(3).text().replace('S/.', '');

                    detalles.push({
                        producto: producto,
                        venDetCantidad: cantidad,
                        venDetPrecio: precio,
                        venDetSubtotal: subtotal
                    });
                } catch (e) {
                    console.error('Error parsing productoData:', e);
                }
            }
        });

        var ventaData = {
            detallesVentas: detalles,
            venTotal: totalPagar
        };
        console.log('Datos de la venta final:', ventaData);

        $.ajax({
            type: 'POST',
            url: '/kenpis/venta/create',
            data: JSON.stringify(ventaData),
            contentType: 'application/json',
            success: function (response) {
                alert('Venta registrada exitosamente.');
                $('#ventaForm')[0].reset();
                $('#ventaModal').modal('hide');
                $('#ventasBody').empty();
                $('#totalPagar').text('S/ 0.00');
            },
            error: function (error) {
                console.error('Error al registrar la venta:', error);
            }
        });
    });

    function cargarDetallesDeVenta() {
        $.ajax({
            type: 'GET',
            url: '/kenpis/venta/detalle/find-all',
            success: function (response) {
                var detallesHtml = response.map(function (detalle) {
                    return '<tr>' +
                        '<td data-producto=\'' + JSON.stringify(detalle.producto) + '\'>' + (detalle.producto ? detalle.producto.proTipo : 'N/A') + '</td>' +
                        '<td>' + detalle.venDetCantidad + '</td>' +
                        '<td>' + detalle.venDetPrecio + '</td>' +
                        '<td>' + 'S/.' + detalle.venDetSubtotal + '</td>' +
                        '</tr>';
                }).join('');
                $('#ventasBody').html(detallesHtml);
                actualizarTotal();
            },
            error: function (error) {
                console.error('Error al obtener los detalles de ventas:', error);
            }
        });
    }

    $('#cliTelefono').on('input', function () {
        let cliTel = $(this).val();

        if (cliTel.length === 9) {
            $.ajax({
                type: 'GET',
                url: '/api/usuarios/verificar/' + cliTel,
                success: function (response) {
                    if (response) {
                        $('#clientName').text(response.cliNombre);
                        $('#clientInfoModal').modal('show');
                        $('#nombreCliente').val(response.cliNombre);
                    } else {
                        $('#clientInfoModal').modal('show');
                        $('#clientInfoModal .modal-body').text('Usuario no registrado.');
                        $('#nombreCliente').val('');
                    }
                },
                error: function (error) {
                    console.error('Error al verificar el celular:', error);
                }
            });
        } else {
            $('#nombreCliente').val('');
        }
    });
});
