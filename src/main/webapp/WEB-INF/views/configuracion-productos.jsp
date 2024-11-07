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
                                <input id="usuarioNivel" type="hidden" value="${usuSessionNivel}"/>

                            </div>
                        </div>
                        <div class="col-md-10">
                            <div class="d-flex justify-content-end mb-2">
                                <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                    <div id="filtro-container" class="mr-3">
                                        <select id="empresaSelection" class="form-control">
                                            <option value="">-- Seleccione una Empresa --</option>
                                        </select>
                                    </div>
                                </c:if>

                                <!-- Filtro de Productos -->
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
                                        <th scope="col">PRODUCTO</th>
                                        <th scope="col">PRECIO COSTO</th>
                                        <th scope="col">PRECIO VENTA</th>
                                        <th scope="col">ESTADO</th>
                                        <th scope="col">ACCION</th>
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
            <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createProductModalLabel">Agregar Producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="createProductForm" role="form" method="post" enctype="multipart/form-data">
                            <!-- Pestañas -->
                            <ul class="nav nav-tabs" id="productTab" role="tablist">
                                <!-- Pestaña Empresa (solo visible para administradores) -->
                                <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                    <li class="nav-item">
                                        <a class="nav-link active" id="empresa-tab" data-toggle="tab" href="#empresa" role="tab" aria-controls="empresa" aria-selected="true">
                                            <i class="fas fa-building" style="margin-right: 5px;"></i> Empresa
                                        </a>
                                    </li>
                                </c:if>
                                <!-- Pestaña Categoría -->
                                <li class="nav-item">
                                    <a class="nav-link" id="categoria-select-tab" data-toggle="tab" href="#categoria-select" role="tab" aria-controls="categoria-select" aria-selected="false">Categoría</a>
                                </li>
                                <!-- Pestaña Producto (nombre, descripción, complementos e imagen) -->
                                <li class="nav-item">
                                    <a class="nav-link" id="producto-tab" data-toggle="tab" href="#producto" role="tab" aria-controls="producto" aria-selected="false">Producto</a>
                                </li>
                                <!-- Pestaña Precio -->
                                <li class="nav-item">
                                    <a class="nav-link" id="precio-tab" data-toggle="tab" href="#precio" role="tab" aria-controls="precio" aria-selected="false">Precio</a>
                                </li>
                            </ul>

                            <!-- Contenido de las pestañas -->
                            <div class="tab-content mt-4" id="productTabContent">

                                <!-- Empresa -->
                                <div class="tab-pane fade show active" id="empresa" role="tabpanel" aria-labelledby="empresa-tab">
                                    <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                        <div class="form-group">
                                            <label for="empresaSelect">Seleccionar Empresa</label>
                                            <select class="form-control" id="empresaSelect">
                                                <option value="" disabled selected>Seleccione una Empresa</option>
                                            </select>
                                        </div>
                                    </c:if>
                                </div>

                                <!-- Categoría -->
                                <div class="tab-pane fade" id="categoria-select" role="tabpanel" aria-labelledby="categoria-select-tab">
                                    <div class="form-group">
                                        <label for="categoria">Categoría</label>
                                        <div class="input-group">
                                            <select class="form-control" id="categoria" required>
                                                <option value="" disabled selected>Seleccionar Categoría</option>
                                            </select>
                                            <div class="input-group-append">
                                                <button class="btn btn-outline-secondary" type="button" id="agregarNuevaCategoria">Agregar Nueva Categoría</button>
                                            </div>
                                        </div>

                                        <!-- Campo de nueva categoría con logo -->
                                        <div id="nuevaCategoria" class="mt-3 p-3 border rounded" style="display: none; position: relative;">
                                            <!-- Botón de cerrar -->
                                            <button type="button" id="cerrarNuevaCategoria" class="close" aria-label="Close" style="position: absolute; top: 10px; right: 10px;">
                                                <span aria-hidden="true">&times;</span>
                                            </button>

                                            <h5>Agregar Nueva Categoría</h5>
                                            <input type="text" class="form-control mb-2" id="ingresarNombreCategoria" placeholder="Nombre de la Nueva Categoría" required>

                                            <label for="imagenCategoria" class="d-block">Cargar Logo de la Categoría</label>
                                            <input type="file" class="form-control-file mb-2" id="imagenCategoria" accept="image/png, image/jpeg" required>

                                            <!-- Vista previa de la imagen -->
                                            <div class="text-center mt-2">
                                                <img id="categoriaImagenPrevia" src="#" alt="Vista previa del logo" style="display: none; width: 100px; height: 100px; margin-top: 10px;">
                                            </div>

                                            <button type="button" class="btn btn-sm btn-success mt-3" id="guardarCategoriaBtn">Guardar Categoría</button>
                                        </div>
                                    </div>
                                </div>

                                <!-- Producto (nombre, descripción, complementos e imagen) -->
                                <div class="tab-pane fade" id="producto" role="tabpanel" aria-labelledby="producto-tab">
                                    <div class="form-group">
                                        <label for="nombreProducto">Nombre del Producto</label>
                                        <input type="text" class="form-control" id="nombreProducto" placeholder="Ingresar Nombre del Producto" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="descripcionProducto">Descripción del Producto</label>
                                        <textarea class="form-control" id="descripcionProducto" rows="3" placeholder="Ingresar la Descripción del Producto" required></textarea>
                                    </div>
                                    <div id="complementos-container" class="form-group">
                                        <label for="complemento1">Complementos del Producto</label>
                                        <div class="d-flex flex-wrap">
                                            <!-- Ejemplo de switch de complemento -->
                                            <div class="custom-control custom-switch m-1">
                                                <input class="custom-control-input" id="complemento1">
                                                <label class="custom-control-label" for="complemento1"><p>No hay empresa seleccionada</p></label>
                                            </div>
                                        </div>
                                    </div>
                                    <hr class="hr hr-blurry" style="border: 2px solid lightgray;"/>
                                    <div class="form-group text-center mt-2">
                                        <label for="imagenProducto">Imagen del Producto</label>
                                        <div class="form-group">
                                            <img id="imagenPreview" src="#" alt="Imagen del Producto" style="display: none; width: 100px; height: 100px; margin-top: 10px;">
                                        </div>
                                        <label for="imagenProducto" class="btn btn-secondary">Cargar Imagen</label>
                                        <input type="file" class="form-control-file" id="imagenProducto" name="imagenProducto" style="visibility:hidden;" accept="image/png, image/jpeg">
                                    </div>
                                </div>

                                <!-- Precio -->
                                <div class="tab-pane fade" id="precio" role="tabpanel" aria-labelledby="precio-tab">
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
                                </div>
                            </div>

                            <!-- Footer -->
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
            <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editProductModalLabel">Editar Producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="editarProductoForm" role="form" method="post" enctype="multipart/form-data">
                            <!-- Pestañas -->
                            <ul class="nav nav-tabs" id="editProductTab" role="tablist">
                                <!-- Pestaña Empresa (solo visible para administradores) -->
                                <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                    <li class="nav-item">
                                        <a class="nav-link active" id="edit-empresa-tab" data-toggle="tab" href="#edit-empresa" role="tab" aria-controls="edit-empresa" aria-selected="true">
                                            <i class="fas fa-building" style="margin-right: 5px;"></i> Empresa
                                        </a>
                                    </li>
                                </c:if>
                                <!-- Pestaña Categoría -->
                                <li class="nav-item">
                                    <a class="nav-link" id="edit-categoria-tab" data-toggle="tab" href="#edit-categoria" role="tab" aria-controls="edit-categoria" aria-selected="false">Categoría</a>
                                </li>
                                <!-- Pestaña Producto -->
                                <li class="nav-item">
                                    <a class="nav-link" id="edit-producto-tab" data-toggle="tab" href="#edit-producto" role="tab" aria-controls="edit-producto" aria-selected="false">Producto</a>
                                </li>
                                <!-- Pestaña Precio -->
                                <li class="nav-item">
                                    <a class="nav-link" id="edit-precio-tab" data-toggle="tab" href="#edit-precio" role="tab" aria-controls="edit-precio" aria-selected="false">Precio</a>
                                </li>
                            </ul>

                            <!-- Contenido de las pestañas -->
                            <div class="tab-content mt-4" id="editProductTabContent">
                                <input type="hidden" id="editProductId">
                                <!-- Empresa -->
                                <div class="tab-pane fade show active" id="edit-empresa" role="tabpanel" aria-labelledby="edit-empresa-tab">
                                    <c:if test="${sessionScope.usuSessionNivel == 'ADMINISTRADOR'}">
                                        <div class="form-group">
                                            <label for="editEmpresaSelect">Seleccionar Empresa</label>
                                            <select class="form-control" id="editEmpresaSelect">
                                                <option value="" disabled selected>Seleccione una Empresa</option>
                                            </select>
                                        </div>
                                    </c:if>
                                </div>

                                <!-- Categoría -->
                                <div class="tab-pane fade" id="edit-categoria" role="tabpanel" aria-labelledby="edit-categoria-tab">
                                    <div class="form-group">
                                        <label for="editCategoria">Categoría</label>
                                        <select class="form-control" id="editCategoria" name="editCategoria"></select>
                                    </div>
                                </div>

                                <!-- Producto -->
                                <div class="tab-pane fade" id="edit-producto" role="tabpanel" aria-labelledby="edit-producto-tab">
                                    <div class="form-group">
                                        <label for="editNombreProducto">Nombre del Producto</label>
                                        <input type="text" class="form-control" id="editNombreProducto" placeholder="Ingresar Nombre del Producto" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="editDescripcionProducto">Descripción del Producto</label>
                                        <textarea class="form-control" id="editDescripcionProducto" rows="3" placeholder="Ingresar la Descripción del Producto" required></textarea>
                                    </div>
                                    <!-- Complementos -->
                                    <div id="editComplementosContainer" class="form-group">
                                        <label for="editComplemento1">Complementos del Producto</label>
                                        <div class="d-flex flex-wrap">
                                            <div class="custom-control custom-switch m-1">
                                                <input type="checkbox" class="custom-control-input" id="editComplemento1">
                                                <label class="custom-control-label" for="editComplemento1">Complemento 1</label>
                                            </div>
                                            <!-- Agrega más switches aquí según necesites -->
                                        </div>
                                    </div>
                                    <hr class="hr hr-blurry" style="border: 2px solid lightgray;"/>
                                    <!-- Imagen del Producto -->
                                    <div class="form-group text-center mt-2">
                                        <label for="editImagenProducto">Imagen del Producto</label>
                                        <div class="form-group">
                                            <img id="editImagenPreview" src="#" alt="Imagen del Producto" style="display: none; width: 100px; height: 100px; margin-top: 10px;">
                                        </div>
                                        <label for="editImagenProducto" class="btn btn-secondary">Cargar Imagen</label>
                                        <input type="file" class="form-control-file" id="editImagenProducto" name="editImagenProducto" style="visibility:hidden;" accept="image/png, image/jpeg">
                                    </div>
                                </div>

                                <!-- Precio -->
                                <div class="tab-pane fade" id="edit-precio" role="tabpanel" aria-labelledby="edit-precio-tab">
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <label for="editPrecioProductoCosto">Precio Costo</label>
                                            <input type="number" class="form-control" id="editPrecioProductoCosto" name="editPrecioProductoCosto" placeholder="Ingresar Precio Costo">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="editPrecioProductoVenta">Precio Venta</label>
                                            <input type="number" class="form-control" id="editPrecioProductoVenta" name="editPrecioProductoVenta" placeholder="Ingresar Precio Venta">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Footer -->
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

<style>

    #nuevaCategoria {
        background-color: #f8f9fa; /* Color de fondo ligero */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Sombra suave */
        max-width: 400px;
        margin: 0 auto;
    }

    #guardarCategoriaBtn {
        width: 100%; /* Botón ancho */
    }

    #cerrarNuevaCategoria {
        font-size: 1.2rem;
        border: none;
        background: none;
        color: #333;
        cursor: pointer;
    }

    #cerrarNuevaCategoria:hover {
        color: #ff4d4d; /* Cambia a rojo al pasar el mouse */
    }

    .switch-complemento {
        position: relative;
        display: inline-block;
        width: 40px;
        height: 20px;
    }

    .switch-complemento input {
        opacity: 0;
        width: 0;
        height: 0;
    }

    .slider-complemento {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        transition: 0.4s;
        border-radius: 34px;
    }

    .slider-complemento:before {
        position: absolute;
        content: "";
        height: 14px;
        width: 14px;
        left: 3px;
        bottom: 3px;
        background-color: white;
        transition: 0.4s;
        border-radius: 50%;
    }

    input:checked + .slider-complemento {
        background-color: #66bb6a; /* Color para el switch activo */
    }

    input:checked + .slider-complemento:before {
        transform: translateX(20px);
    }

    .small {
        color: #666; /* Color para el texto de los subcomplementos */
    }

</style>

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