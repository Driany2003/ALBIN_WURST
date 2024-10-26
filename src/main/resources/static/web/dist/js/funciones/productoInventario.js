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
                    inventarioList.append(`
                    <tr id="product-row-${producto.proId}">
                        <td><img src="data:image/jpeg;base64,${producto.proImagen}" alt="${producto.proDescripcion}" width="50"></td>
                        <td><strong>${producto.proCategoria}</strong></td>
                        <td>
                            <ul>
                                <li><i class="far fa-money-bill-alt"></i> <strong>Precio Costo:</strong> S/. ${producto.proPrecioCosto.toFixed(2)}</li>
                                <li><i class="far fa-money-bill-alt"></i> <strong>Precio Venta:</strong> S/. ${producto.proPrecioVenta.toFixed(2)}</li>
                                <li><i class="fas fa-calendar-alt"></i> <strong>Vencimiento:</strong> En <strong>${producto.proInvFechaCreacion}</strong> días</li>
                            </ul>
                        </td>
                        <td>
                            <ul>
                                <li><i class="fas fa-box"></i> <strong>Disponibles:</strong> ${producto.proInvStockInicial}</li>
                                <li><i class="fas fa-box-open"></i> <strong>Vendidos:</strong> ${producto.proInvStockVentas}</li>
                            </ul>
                        </td>
                        <td>${producto.proInvStockActual}</td>
                        <td>${producto.proIsActive ? 'Habilitado' : 'Deshabilitado'}</td>
                        <td>
                          <button type="button" data-id="${producto.proId}" class="btn btn-sm btn-warning editarProducto" data-toggle="tooltip" data-placement="top" title="Editar Producto">
                            <i class="fas fa-pencil-alt"></i>
                          </button>
                          <button type="button" data-id="${producto.proId}" class="btn btn-sm btn-danger eliminarProducto" data-toggle="tooltip" data-placement="top" title="Eliminar Producto">
                            <i class="fas fa-trash"></i>
                          </button>
                        </td>
                    </tr>
                `);
                });
                $('[data-toggle="popover"]').popover();
            },
            error: function () {
                toastr.error('Error al cargar el inventario.');
            }
        });
    }
});