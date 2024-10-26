<%@ page contentType="text/html; charset=UTF-8" %>
<html dir="ltr" lang="en">
<%@ include file="includes/header.jspf" %>
<body>
<!-- ============================================================== -->
<!-- Preloader - style you can find in spinners.css -->
<!-- ============================================================== -->
<%@ include file="includes/preloader.jspf" %>
<!-- ============================================================== -->
<!-- Main wrapper - style you can find in pages.scss -->
<!-- ============================================================== -->
<div id="main-wrapper">
    <!-- ============================================================== -->
    <!-- Topbar header - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <%@ include file="includes/topbar.jspf" %>
    <!-- ============================================================== -->
    <!-- End Topbar header -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <%@ include file="includes/left-sidebar.jspf" %>
    <!-- ============================================================== -->
    <!-- End Left Sidebar - style you can find in sidebar.scss  -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- Page wrapper  -->
    <!-- ============================================================== -->
    <!-- =========================MAMASHAROOO===================================== -->
    <div class="page-wrapper">
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">PRODUCTOS <span class="label label-rounded label-info">Listado</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-2">
                            <div class="d-flex justify-content-end mb-2">
                                <button class="btn btn-outline-primary mr-2" data-toggle="modal" data-target="#createProductModal">Ingresar Producto</button>
                                <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                                <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                            </div>
                        </div>
                        <div class="col-md-10">
                            <div class="d-flex justify-content-end mb-2">
                                <div class="input-group" style="width: 300px;">
                                    <input type="text" class="form-control" id="tableFilter" placeholder="Filtra Productos...">
                                    <div class="input-group-append">
                                    <span class="input-group-text">
                                        <i class="fas fa-search"></i>
                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="card-body p-2">
                        <div class="card-body p-2">
                            <div class="table-responsive">
                                <table id="productoTable" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Imagen</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Precio Costo</th>
                                        <th scope="col">Precio Venta</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Accion</th>
                                    </tr>
                                    </thead>
                                    <tbody id="productoBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL PARA PODER CREAR UN PRODCUCTO -->
        <div class="modal fade" id="createProductModal" tabindex="-1" role="dialog" aria-labelledby="createProductModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createProductModalLabel">Agregar Producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="createProductForm" role="form" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="categoria">Categoría</label>
                                <select class="form-control" id="categoria" required>
                                    <option value="" disabled selected>Seleccionar Categoria</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="nombreProducto">Nombre del Producto</label>
                                <input type="text" class="form-control" id="nombreProducto" placeholder="Ingresar Nombre del Producto" required>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="precioProductoCosto">Precio Costo</label>
                                    <input type="number" class="form-control" id="precioProductoCosto" placeholder="Ingresar Precio Costo" required>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="precioProductoVenta">Precio Venta</label>
                                    <input type="number" class="form-control" id="precioProductoVenta" placeholder="Ingresar Precio Venta" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="descripcionProducto">Descripcion del Producto</label>
                                <textarea class="form-control" id="descripcionProducto" rows="3" placeholder="Ingresar la Descripcion del Producto" required></textarea>
                            </div>
                            <hr class="hr hr-blurry" style="border: 3px solid lightblue;"/>
                            <div class="form-group">
                                <label for="descripcionProducto">Complementos</label>
                            </div>
                            <hr class="hr hr-blurry" style="border: 3px solid lightblue;"/>
                            <div class="form-group">
                                <label for="descripcionProducto">Imagen</label>
                            </div>
                            <div class="form-group text-center">
                                <label for="imagenProducto" class="btn btn-outline-secondary">Cargar</label>
                                <input type="file" id="imagenProducto" style="display: none;">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary" id="registrarProducto">Agregar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal de editar producto -->
        <div class="modal fade" id="editProductModal" tabindex="-1" role="dialog" aria-labelledby="editProductModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editProductModalLabel">Editar Producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="editarProductoForm">
                            <div class="form-group">
                                <label for="editCategoria">Categoría</label>
                                <select class="form-control" id="editCategoria" required></select>
                            </div>
                            <div class="form-group">
                                <label for="editNombreProducto">Nombre</label>
                                <input type="text" class="form-control" id="editNombreProducto" required>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="editPrecioProductoCosto">Precio Costo</label>
                                    <input type="number" class="form-control" id="editPrecioProductoCosto" required>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="editPrecioProductoVenta">Precio Venta</label>
                                    <input type="number" class="form-control" id="editPrecioProductoVenta" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="editDescripcionProducto">Descripción</label>
                                <textarea class="form-control" id="editDescripcionProducto" rows="3"></textarea>
                            </div>
                            <hr class="hr hr-blurry"/>
                            <div class="form-group">
                                <label for="descripcionProducto">Complementos</label>
                            </div>
                            <hr class="hr hr-blurry"/>
                            <div class="form-group">
                                <label for="descripcionProducto">Imagen</label>
                            </div>
                            <div id="editImgContainer">
                            </div>
                            <div class="form-group text-center">
                                <label for="editImagenProductoNuevo" class="btn btn-outline-secondary">Editar Imagen</label>
                                <input type="file" id="editImagenProductoNuevo" style="display: none;">
                            </div>
                            <input type="hidden" id="editProductId">
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary">Guardar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- customs css -->
<link href="/static/css/custom.css" rel="stylesheet">

<!-- customs -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/dragula/3.7.3/dragula.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.14.0/Sortable.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<link href="/static/web/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css" rel="stylesheet">
<link href="/static/web/assets/extra-libs/taskboard/css/lobilist.css" rel="stylesheet">
<link href="/static/web/assets/extra-libs/taskboard/css/jquery-ui.min.css" rel="stylesheet">
<link href="/static/web/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" rel="stylesheet">
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<link href="https://cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet">

<!--  functions-->
<script src="/static/web/dist/js/funciones/producto.js"></script>
<!-- import -->
<%@ include file="includes/all-jquery.jspf" %>
<!-- footer -->
<%@ include file="includes/footer.jspf" %>
<!-- End footer -->
</body>
</html>