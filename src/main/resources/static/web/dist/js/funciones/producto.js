$(document).ready(function () {
    listarCategoria();
    cargarProductos();

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
                        <td><img src="${producto.proImagen}" alt="${producto.proDescripcion}" width="50"></td>
                        <td>${producto.proDescripcion}</td>
                        <td>${producto.proPrecio}</td>
                        <td>${producto.ProIsActive ? 'Sí' : 'No'}</td>
                        <td>${producto.estado}</td>
                        <td>
                            <button class="btn btn-sm btn-info" onclick="viewProducto(${producto.proId})">
                                <i class="fas fa-eye"></i>
                            </button>
                             <button class="btn btn-sm btn-warning" onclick="editProducto(${producto.proId})">
                                <i class="fas fa-pencil-alt"></i>
                             </button>
                            <button class="btn btn-sm btn-danger" onclick="deleteProducto(${producto.proId})">
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
});
