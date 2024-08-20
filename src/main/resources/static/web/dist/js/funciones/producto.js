$(document).ready(function () {
    listarCategoria();
    cargarProductos();

    //lista las categorias para el registro de productos
    function listarCategoria() {
        $.ajax({
            url: '/kenpis/producto/categorias',
            method: 'GET',
            success: function (data) {
                var seleccionarCategoria = $('#categoria');
                seleccionarCategoria.empty();
                seleccionarCategoria.append('<option value="" disabled selected>Seleccionar Una Categoria</option>');
                data.forEach(function (categoria) {
                    seleccionarCategoria.append('<option value="' + categoria.proId + '">' + categoria.proDescripcion + '</option>');
                });
            },
            error: function (error) {
                console.error('Error al obtener las categorías:', error);
            }
        });
    }

    function cargarProductos() {
        $.ajax({
            type: 'GET',
            url: '/kenpis/producto/find-all',
            contentType: 'application/json',
            success: function (response) {
                const productoBody = $('#productoBody');
                productoBody.empty();
                response.forEach(producto => {
                    productoBody.append(`
                    <tr>
                        <td><img src="data:image/jpeg;base64,${producto.proImagen}" alt="${producto.proDescripcion}" width="50"> ${producto.proDescripcion}</td>
                        <td> S/. ${producto.proPrecio.toFixed(2)}</td>
                       <td>
                            <label class="switch">
                                <input type="checkbox" class="estado-checkbox" data-id="1" ${producto.proIsActive ? 'checked' : ''}>
                                <span class="slider"></span>
                            </label>
                       </td>
                       <td>
                             <button class="btn btn-sm btn-warning" onclick="editarProducto(${producto.proId})">
                                <i class="fas fa-pencil-alt"></i>
                             </button>
                            <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${producto.proId})">
                                <i class="fas fa-trash"></i>
                            </button>
                       </td>
                    </tr>
                `);
                });
            },
            error: function () {
                toastr.error('Error al cargar los productos.');
            }
        });
    }


    //Regsitrar Producto
    $('#registrarProducto').click(function () {
        var nombreProducto = $('#nombreProducto').val();
        var precioProducto = $('#precioProducto').val();
        var categoriaProducto = $('#categoria').val();
        var descripcionProducto = $('#descripcionProducto').val();
        var imagenProducto = $('#imagenProducto').val();
        $.ajax({
            url: '/kenpis/producto/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                proCategoria: nombreProducto,
                proPrecio: precioProducto,
                padreId: categoriaProducto,
                proDescripcion: descripcionProducto,
                proImagen: imagenProducto,
                proIsActive: true,
                empId: 1
            }),
            success: function (response) {
                $('#nombreProducto').val('');
                $('#precioProducto').val('');
                $('#categoria').val('');
                $('#descripcionProducto').val('');
                $('#imagenProducto').val('');
                toastr.success('Producto registrado correctamente.');
                cargarProductos();
            },
            error: function () {
                toastr.error('Error al registrar el producto. Intente nuevamente.');
            }
        });
    });

});

//Elimina Producto
function eliminarProducto(id) {

    if (confirm('¿Está seguro de que desea eliminar este producto?')) {
        $.ajax({
            url: '/kenpis/producto/delete/' + id,
            method: 'DELETE',
            success: function (response) {
                toastr.success('Producto eliminado correctamente.');
                $('#product-row-' + id).remove();
            },
            error: function () {
                toastr.error('Error al eliminar el producto. Intente nuevamente.');
            }
        });
    }
}

//Editar producto