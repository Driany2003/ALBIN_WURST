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
                    <h4 class="page-title">Complementos <span class="label label-rounded label-info">Listado</span></h4>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="card mt-1">
                <div class="card-header">
                    <div class="form-row">
                        <div class="col-md-2">
                            <div class="d-flex justify-content-end mb-2">
                                <button class="btn btn-outline-primary mr-2" data-toggle="modal" data-target="#createModal">Ingresar</button>
                                <input id="empresaId" type="hidden" value="${empresaSession.empId}"/>
                                <input id="usuarioId" type="hidden" value="${usuSessionId}"/>
                            </div>
                        </div>
                        <div class="col-md-10">
                            <div class="d-flex justify-content-end mb-2">
                                <div class="input-group" style="width: 300px;">
                                    <input type="text" class="form-control" id="tableFilter" placeholder="Filtrar...">
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
                                <table id="tablaLista" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Empresa</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col">Accion</th>
                                    </tr>
                                    </thead>
                                    <tbody id="tablaBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL :: CREATE -->
        <div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="createModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createModalLabel">Agregar Complemento</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="createForm" role="form" method="post">
                            <div class="form-group">
                                <label for="complementoTipo">Tipo</label>
                                <input type="text" class="form-control" id="complementoTipo" placeholder="Ingresar Tipo de Complementos" required>
                            </div>
                            <hr class="hr hr-blurry" style="border: 3px solid lightblue;"/>
                            <div class="form-group">
                                <table id="complementosTable" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Precio</th>
                                    </tr>
                                    </thead>
                                    <tbody id="complementosTableBody">
                                    <tr>
                                        <td>
                                            <input type="text" class="form-control" id="complementoNombre" placeholder="Nombre" required>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" id="complementoPrecio" placeholder="Precio" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="text" class="form-control" id="complementoNombre1" placeholder="Nombre" required>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" id="complementoPrecio1" placeholder="Precio" required>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <hr class="hr hr-blurry" style="border: 3px solid lightblue;"/>
                            <div class="form-group text-right">
                                <button class="btn btn-outline-primary mr-2"> ( + )</button>
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

        <!-- MODAL :: EDIT -->
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Editar</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="editForm" role="form" method="post">
                            <div class="form-group">
                                <input type="hidden" id="proComplementoIdPadre">
                                <label for="complementoTipoEdit">Tipo de Complementos</label>
                                <input type="text" class="form-control" id="complementoTipoEdit" required>
                            </div>
                            <hr class="hr hr-blurry" style="border: 3px solid lightblue;"/>
                            <div class="form-group">
                                <table id="complementosTableEdit" class="table table-sm table-bordered table-striped">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Precio</th>
                                        <th>Accion</th>
                                    </tr>
                                    </thead>
                                    <tbody id="complementosTableBodyEdit">
                                    <tr>
                                        <td>
                                            <input type="hidden" id="proComplementoId_edit">
                                            <input type="text" class="form-control" id="complementoNombre_edit" placeholder="Nombre" required>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" id="complementoPrecio_edit" placeholder="Precio" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="hidden" id="proComplementoId_edit_2">
                                            <input type="text" class="form-control" id="complementoNombre_edit_1" placeholder="Nombre" required>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" id="complementoPrecio_edit_1" placeholder="Precio" required>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <hr class="hr hr-blurry" style="border: 3px solid lightblue;"/>
                            <div class="form-group text-right">
                                <button class="btn btn-outline-primary mr-2"> ( + )</button>
                            </div>
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

<!--  functions -->
<script src="/static/web/dist/js/funciones/productoComplemento.js"></script>
<!-- import -->
<%@ include file="includes/all-jquery.jspf" %>
<!-- footer -->
<%@ include file="includes/footer.jspf" %>
</body>
</html>