$(document).ready(function () {
    cargarProductos();
    function cargarProductos() {
        $.ajax({
            type: 'GET',
            url: '/kenpis/inventario/find-all',
            contentType: 'application/json',
            success: function (response) {
                const inventarioList = $('#product-card');
                inventarioList.empty();
                response.forEach(producto => {
                    inventarioList.append(`
                    <ul class="list-unstyled" style="padding: 5px;">
                        <li class="media media-product">
                            <div class="img-container">
                                <img class="mr-3 img-fluid img-product-list" src="data:image/jpeg;base64,${producto.proImagen}" alt="${producto.proDescripcion}">
                            </div>
                            <div class="media-body product-media-body">
                                <p class="text-uppercase text-center media-product-title"><strong>${producto.proDescripcion}</strong></p>
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-12 col-md-6 col-lg-3 col-product"><i class="far fa-money-bill-alt"></i> <strong>Precio:</strong> S/. ${producto.proPrecio.toFixed(2)}</div>
                                        <div class="col-12 col-md-6 col-lg-3 col-product"><i class="fas fa-clipboard-check"></i> <strong>Estado:</strong> ${producto.proIsActive ? 'Habilitado' : 'Deshabilitado'}</div>
                                        <div class="col-12 col-md-6 col-lg-3 col-product"><i class="fas fa-box"></i> <strong>Disponibles:</strong> ${producto.proInvStockInicial}</div>
                                        <div class="col-12 col-md-6 col-lg-3 col-product"><i class="fas fa-box-open"></i> <strong>Vendidos:</strong> ${producto.proInvStockVentas}</div>
                                        <div class="col-12 col-md-6 col-lg-3 col-product"><i class="fas fa-calendar-alt"></i> <strong>Vencimiento:</strong> En ${producto.proInvFechaCreacion} días</div>
                                    </div>
                                </div>
                                <div class="text-right media-product-options">
                                    <span><i class="fas fa-tools"></i> &nbsp; OPCIONES: </span>
                                    <a href="#" class="btn btn-info" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="Información detallada">
                                        <i class="fas fa-box-open"></i>
                                    </a>
                                    <a href="#" class="btn btn-secondary" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="Gestionar imagen">
                                        <i class="far fa-image"></i>
                                    </a>
                                    <a href="#" class="btn btn-success" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="Actualizar producto">
                                        <i class="fas fa-sync"></i>
                                    </a>
                                    <form class="FormularioAjax form-product" action="#" method="POST" data-form="delete" autocomplete="off">
                                        <input type="hidden" name="producto_id_del" value="${producto.proId}">
                                        <input type="hidden" name="modulo_producto" value="eliminar">
                                        <button type="submit" class="btn btn-warning" data-toggle="popover" data-trigger="hover" data-placement="top" data-content="Eliminar producto">
                                            <i class="far fa-trash-alt"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </ul> 
                `);
                });
                $('[data-toggle="popover"]').popover();
            },
            error: function () {
                toastr.error('Error al cargar los productos.');
            }
        });
    }
});