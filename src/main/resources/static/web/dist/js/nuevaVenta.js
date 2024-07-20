$(document).ready(function () {
    let chorizoIndex = 1;
    let bebidaIndex = 1;

    cargarDetallesDeVenta();
    function SeleccionarOpciones(selectClass, tipo) {
        $.ajax({
            type: 'GET',
            url: '/api/productos/tipo/' + tipo,
            success: function (response) {
                $('.' + selectClass).each(function () {
                    $(this).empty();
                    $(this).append($('<option>', { text: 'Elige una opción', value: '' }));
                    response.forEach(function (producto) {
                        $(this).append($('<option>', {
                            value: producto.proId,
                            text: producto.proNombre
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
        $('#chorizos-container').append(`
            <div class="form-group">
                <label for="chorizo-${chorizoIndex}" class="control-label col-form-label">Elige tu Chorizo Fav</label>
                <select id="chorizo-${chorizoIndex}" class="form-control form-control-sm chorizo-select">
                    <option value="">Elige tu Chorizo Fav</option>
                </select>
            </div>
            <div class="form-group">
                <label for="cantidadChorizos-${chorizoIndex}">Cantidad de Chorizos</label>
                <input id="cantidadChorizos-${chorizoIndex}" type="number" class="form-control form-control-sm cantidad-chorizo" placeholder="Cantidad" min="1">
            </div>
        `);
        SeleccionarOpciones(`chorizo-select`, 'chorizo');
        chorizoIndex++;
    });


    $('#addBebida').click(function () {
        $('#bebidas-container').append(`
            <div class="form-group">
                <label for="bebida-${bebidaIndex}" class="control-label col-form-label">Elige tu Bebida Fav</label>
                <select id="bebida-${bebidaIndex}" class="form-control form-control-sm bebida-select">
                    <option value="">Elige tu Bebida Fav</option>
                </select>
            </div>
            <div class="form-group">
                <label for="cantidadBebidas-${bebidaIndex}">Cantidad de Bebidas</label>
                <input id="cantidadBebidas-${bebidaIndex}" type="number" class="form-control form-control-sm cantidad-bebida" placeholder="Cantidad" min="1">
            </div>
        `);
        SeleccionarOpciones(`bebida-select`, 'bebida');
        bebidaIndex++;
    });


    $('#ventaForm').submit(function (event) {
        event.preventDefault();

        var detalles = [];

        $('.chorizo-select').each(function () {
            var chorizoId = $(this).val();
            var cantidadChorizos = $(this).closest('.form-group').next().find('.cantidad-chorizo').val();
            if (chorizoId && cantidadChorizos > 0) {
                detalles.push({
                    producto: { proId: chorizoId },
                    detvenCantidad: cantidadChorizos
                });
            }
        });

        $('.bebida-select').each(function () {
            var bebidaId = $(this).val();
            var cantidadBebidas = $(this).closest('.form-group').next().find('.cantidad-bebida').val();
            if (bebidaId && cantidadBebidas > 0) {
                detalles.push({
                    producto: { proId: bebidaId },
                    detvenCantidad: cantidadBebidas
                });
            }
        });

        var ventaData = { detallesVentas: detalles };
        console.log('Datos de la venta:', ventaData);

        $.ajax({
            type: 'POST',
            url: '/sys/nueva-venta/registrar',
            data: JSON.stringify(ventaData),
            contentType: 'application/json',
            success: function (response) {
                alert('Venta registrada exitosamente.');
                $('#ventaForm')[0].reset();
                $('#ventaModal').modal('hide');
            },
            error: function (error) {
                console.error('Error al registrar la venta:', error);
            }
        });
    });


        function cargarDetallesDeVenta() {
            $.ajax({
                type: 'GET',
                url: '/sys/nueva-venta/listar',
                success: function (response) {
                    var detallesHtml = response.map(function (detalle) {
                        return '<tr>' +
                            '<td>' + (detalle.producto ? detalle.producto.proNombre : 'N/A') + '</td>' +
                            '<td>' + detalle.detvenCantidad + '</td>' +
                            '<td>' + detalle.precioUnitario + '</td>' +
                            '<td>' + detalle.detvenSubtotal + '</td>' +
                            '</tr>';
                    }).join('');
                    $('#ventasBody').html(detallesHtml);
                },
                error: function (error) {
                    console.error('Error al obtener los detalles de ventas:', error);
                }
            });
        }



/*
    $('#celular').on('input', function () {
        let celular = $(this).val();

        if (celular.length === 9) {
            $.ajax({
                type: 'GET',
                url: '/api/clientes/telefono/' + celular,
                success: function (response) {
                    if (response) {
                        // If client exists, show popup with the client name
                        $('#clientName').text(response.nombre);
                        $('#clientInfoModal').modal('show');
                        $('#nombreCliente').val(response.nombre);
                    } else {
                        // If client does not exist
                        $('#clientInfoModal').modal('hide');
                        $('#nombreCliente').val('');
                        alert('Cliente no encontrado.');
                    }
                },
                error: function (error) {
                    console.error('Error al verificar el cliente:', error);
                }
            });
        }
    });
*/


});
