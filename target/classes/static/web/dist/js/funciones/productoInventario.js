$(document).ready(function () {
    cargarProductos();


    function cargarProductos() {
        $.ajax({
            type: 'GET',
            url: '/kenpis/inventario/find-all',
            contentType: 'application/json',
            success: function (response) {
                const inventarioList = $('#productoBody');
                inventarioList.empty();
                response.forEach(producto => {
                    // Calcula los días para vencimiento
                    const fechaVencimiento = producto.proInvFechaVencimiento ? new Date(producto.proInvFechaVencimiento) : null;
                    const fechaHoy = new Date();
                    let diasParaVencimiento = "Sin fecha de vencimiento";

                    if (fechaVencimiento) {
                        // Diferencia en milisegundos y conversión a días
                        const diferenciaTiempo = fechaVencimiento - fechaHoy;
                        diasParaVencimiento = Math.ceil(diferenciaTiempo / (1000 * 60 * 60 * 24));
                        diasParaVencimiento = diasParaVencimiento >= 0 ? diasParaVencimiento : "Vencido";
                    }

                    inventarioList.append(`
                        <tr id="product-row-${producto.productoId}">
                            <td><img src="${producto.proImagen}" alt="${producto.proDescripcion}" width="50"></td>
                            <td><strong>${producto.proCategoria}</strong></td>
                            <td>
                                <ul>
                                    <li><i class="far fa-money-bill-alt"></i> <strong>Precio Costo:</strong> S/. ${producto.proPrecioCosto || 0.0}</li>
                                    <li><i class="far fa-money-bill-alt"></i> <strong>Precio Venta:</strong> S/. ${producto.proPrecioVenta || 0.0}</li>
                                    <li><i class="fas fa-calendar-alt"></i> <strong>Vencimiento en:</strong> ${diasParaVencimiento} días</li>
                                </ul>
                            </td>
                            <td>
                                <ul>
                                    <li><i class="fas fa-box"></i> <strong>Inicial:</strong> ${producto.proInvStockInicial || 0}</li>
                                    <li><i class="fas fa-box-open"></i> <strong>Vendidos:</strong> ${producto.proInvStockVentas || 0}</li>
                                </ul>
                            </td>
                            <td>${producto.proInvStockActual || 0}</td>
                            <td>${producto.proIsActive ? 'Activo' : 'Inactivo'}</td>
                            <td>
                                <button type="button" data-id="${producto.productoId}" class="btn btn-sm btn-warning editarProducto" data-toggle="tooltip" title="Editar Producto">
                                    <i class="fas fa-pencil-alt"></i>
                                </button>
                            </td>
                        </tr>
                    `);
                });

                // Evento para abrir el modal de edición
                $('.editarProducto').click(function () {
                    var productoId = $(this).data('id');
                    editarInventario(productoId);
                });
            },
            error: function () {
                toastr.error('Error al cargar el inventario.');
            }
        });
    }


    function editarInventario(productoId) {
        $.ajax({
            url: `/kenpis/inventario/find-by-id/${productoId}`,
            method: 'GET',
            success: function (response) {
                console.log("que llega " + response.proInvFechaVencimiento);
                let fechaVencimiento = "";
                if (response.proInvFechaVencimiento) {
                    const fechaCompleta = new Date(response.proInvFechaVencimiento);
                    const year = fechaCompleta.getFullYear();
                    const month = String(fechaCompleta.getMonth() + 1).padStart(2, '0');
                    const day = String(fechaCompleta.getDate()).padStart(2, '0');
                    fechaVencimiento = `${year}-${month}-${day}`;
                }
                $('#editarProductoId').val(response.productoId);
                $('#editarStock').val(response.proInvStockInicial);
                $('#editarFechaVencimiento').val(fechaVencimiento);
                $('#editarProductoModal').modal('show');
            },
            error: function () {
                toastr.error('Error al obtener los detalles del producto.');
            }
        });
    }

    // Manejar la actualización del producto
    $('#editarProductoForm').submit(function (e) {
        e.preventDefault();

        const fechaVencimientoInput = $('#editarFechaVencimiento').val();
        const fechaVencimiento = new Date(fechaVencimientoInput);
        const fechaHoy = new Date();

        // Ajuste para comparar solo la fecha (sin hora)
        fechaHoy.setHours(0, 0, 0, 0);
        fechaVencimiento.setHours(0, 0, 0, 0);

        // Validación: fecha de vencimiento no menor que la fecha actual
        if (fechaVencimiento < fechaHoy) {
            toastr.error('La fecha de vencimiento no puede ser menor a la fecha actual.');
            return; // Detener el envío del formulario si la validación falla
        }

        var requestData = {
            productoId: $('#editarProductoId').val(),
            proInvStockInicial: $('#editarStock').val(),
            proInvFechaVencimiento: fechaVencimientoInput // Enviar la fecha de vencimiento válida
        };

        $.ajax({
            url: '/kenpis/inventario/update',
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            success: function () {
                toastr.success('Producto actualizado correctamente.');
                $('#editarProductoModal').modal('hide');
                cargarProductos(); // Recargar la lista para reflejar los cambios
            },
            error: function () {
                toastr.error('Error al actualizar el producto.');
            }
        });
    });

});
