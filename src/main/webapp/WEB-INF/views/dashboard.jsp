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
        <!-- ============================================================== -->
        <!-- Bread crumb and right sidebar toggle -->
        <!-- ============================================================== -->
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">DASHBOARD <span class="label label-rounded label-info">Pedidos</span></h4>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- End Bread crumb and right sidebar toggle -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Container fluid  -->
        <!-- ============================================================== -->
        <div class="container-fluid">
            <!-- ============================================================== -->
            <!-- DATOS GENERALES -->
            <!-- ============================================================== -->
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-account-check font-20 text-info"></i>
                                    <p class="font-16 m-b-5">Registrado</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${registrados}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-currency-usd font-20 text-success"></i>
                                    <p class="font-16 m-b-5">Pagados</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${pagados}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-av-timer font-20 text-purple"></i>
                                    <p class="font-16 m-b-5">En proceso</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${enProceso}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-7">
                                    <i class="mdi mdi-check-all font-20 text-danger"></i>
                                    <p class="font-16 m-b-5">Atendido</p>
                                </div>
                                <div class="col-5">
                                    <h1 class="font-light text-right mb-0">${atendidos}</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- SEGUIMIENTO -->
            <!-- ============================================================== -->
            <div class="container-fluid">
                <div class="row d-flex justify-content-center">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Taskboard-Pacasmayo</h4>
                                <div id="todo-lists-basic" class="lobilists single-line">
                                    <div class="lobilist-wrapper">
                                        <div id="todo" class="lobilist lobilist-danger">
                                            <div class="lobilist-header ">
                                                <div class="lobilist-actions">
                                                    <div class="dropdown">
                                                        <button type="button" data-toggle="dropdown" class="btn btn-xs">
                                                            <i class="ti-view-grid"></i>
                                                        </button>
                                                        <div class="dropdown-menu dropdown-menu-right">
                                                            <div class="lobilist-default"></div>
                                                            <div class="lobilist-danger"></div>
                                                            <div class="lobilist-success"></div>
                                                            <div class="lobilist-warning"></div>
                                                            <div class="lobilist-info"></div>
                                                            <div class="lobilist-primary"></div>
                                                        </div>
                                                    </div>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-pencil"></i>
                                                    </button>
                                                    <button class="btn btn-xs btn-finish-title-editing">
                                                        <i class="ti-check-box"></i>
                                                    </button>
                                                    <button class="btn btn-xs btn-cancel-title-editing">
                                                        <i class="ti-close"></i>
                                                    </button>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-plus"></i>
                                                    </button>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-trash"></i>
                                                    </button>
                                                </div>
                                                <div class="lobilist-title">Registrado</div>
                                            </div>
                                            <div class="lobilist-body">
                                                <ul class="lobilist-items ">
                                                    <li data-id="1" class="lobilist-item">
                                                        <div class="lobilist-item-title">Floor coolsdded cinders</div>
                                                        <div class="lobilist-item-description">Thunder fulfilled travellers folly, wading, lake.</div>
                                                        <div class="lobilist-item-duedate">2015-01-31</div>
                                                        <label class="checkbox-inline lobilist-check"><input type="checkbox"></label>
                                                        <div class="todo-actions">
                                                            <div class="edit-todo todo-action">
                                                                <i class="ti-pencil"></i>
                                                            </div>
                                                            <div class="delete-todo todo-action">
                                                                <i class="ti-close"></i>
                                                            </div>
                                                        </div>
                                                        <div class="drag-handler">
                                                        </div>
                                                    </li>
                                                </ul>
                                                <form class="lobilist-add-todo-form hide">
                                                    <input type="hidden" name="id">
                                                    <div class="form-group">
                                                        <input type="text" name="title" class="form-control" placeholder="TODO title">
                                                    </div>
                                                    <div class="form-group">
                                                        <textarea rows="2" name="description" class="form-control" placeholder="TODO description"></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <input type="text" name="dueDate" class="datepicker form-control hasDatepicker" placeholder="Due Date" id="dp1718646903560">
                                                    </div>
                                                    <div class="lobilist-form-footer">
                                                        <button class="btn btn-primary btn-sm btn-add-todo">Add/Update</button>
                                                        <button type="button" class="btn btn-danger btn-sm btn-discard-todo">Cancel</button>
                                                    </div>
                                                </form>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="lobilist-wrapper">
                                        <div id="doing" class="lobilist lobilist-primary">
                                            <div class="lobilist-header ui-sortable-handle">
                                                <div class="lobilist-actions">
                                                    <div class="dropdown">
                                                        <button type="button" data-toggle="dropdown" class="btn btn-xs">
                                                            <i class="ti-view-grid"></i>
                                                        </button>
                                                        <div class="dropdown-menu dropdown-menu-right">
                                                            <div class="lobilist-default"></div>
                                                            <div class="lobilist-danger"></div>
                                                            <div class="lobilist-success"></div>
                                                            <div class="lobilist-warning"></div>
                                                            <div class="lobilist-info"></div>
                                                            <div class="lobilist-primary"></div>
                                                        </div>
                                                    </div>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-pencil"></i>
                                                    </button>
                                                    <button class="btn btn-xs btn-finish-title-editing">
                                                        <i class="ti-check-box"></i>
                                                    </button>
                                                    <button class="btn btn-xs btn-cancel-title-editing">
                                                        <i class="ti-close"></i>
                                                    </button>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-plus"></i>
                                                    </button>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-trash"></i>
                                                    </button>
                                                </div>
                                                    <div class="lobilist-title">Pagado</div>
                                            </div>
                                            <div class="lobilist-body">
                                                <ul class="lobilist-items ui-sortable">
                                                    <li data-id="1" class="lobilist-item">
                                                        <div class="lobilist-item-title">Floor coolsdded cinders</div>
                                                        <div class="lobilist-item-description">Thunder fulfilled travellers folly, wading, lake.</div>
                                                        <div class="lobilist-item-duedate">2015-01-31</div>
                                                        <label class="checkbox-inline lobilist-check"><input type="checkbox"></label>
                                                        <div class="todo-actions">
                                                            <div class="edit-todo todo-action">
                                                                <i class="ti-pencil"></i>
                                                            </div>
                                                            <div class="delete-todo todo-action">
                                                                <i class="ti-close"></i>
                                                            </div>
                                                        </div>
                                                        <div class="drag-handler"></div>
                                                    </li>
                                                </ul>
                                                <form class="lobilist-add-todo-form hide">
                                                    <input type="hidden" name="id">
                                                    <div class="form-group">
                                                        <input type="text" name="title" class="form-control" placeholder="TODO title">
                                                    </div>
                                                    <div class="form-group">
                                                        <textarea rows="2" name="description" class="form-control" placeholder="TODO description"></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <input type="text" name="dueDate" class="datepicker form-control hasDatepicker" placeholder="Due Date" id="dp1718646903561">
                                                    </div>
                                                    <div class="lobilist-form-footer">
                                                        <button class="btn btn-primary btn-sm btn-add-todo">Add/Update</button>
                                                        <button type="button" class="btn btn-danger btn-sm btn-discard-todo">Cancel</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="lobilist-wrapper">
                                        <div id="Done" class="lobilist lobilist-success">
                                            <div class="lobilist-header ui-sortable-handle">
                                                <div class="lobilist-actions">
                                                    <div class="dropdown">
                                                        <button type="button" data-toggle="dropdown" class="btn btn-xs">
                                                            <i class="ti-view-grid"></i>
                                                        </button>
                                                        <div class="dropdown-menu dropdown-menu-right">
                                                            <div class="lobilist-default"></div>
                                                            <div class="lobilist-danger"></div>
                                                            <div class="lobilist-success"></div>
                                                            <div class="lobilist-warning"></div>
                                                            <div class="lobilist-info"></div>
                                                            <div class="lobilist-primary"></div>
                                                        </div>
                                                    </div>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-pencil"></i>
                                                    </button>
                                                    <button class="btn btn-xs btn-finish-title-editing">
                                                        <i class="ti-check-box"></i>
                                                    </button>
                                                    <button class="btn btn-xs btn-cancel-title-editing">
                                                        <i class="ti-close"></i>
                                                    </button>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-plus"></i>
                                                    </button>
                                                    <button class="btn btn-xs">
                                                        <i class="ti-trash"></i>
                                                    </button>
                                                </div>
                                                <div class="lobilist-title">En proceso</div>
                                            </div>
                                            <div class="lobilist-body">
                                                <ul class="lobilist-items ui-sortable">
                                                    <li data-id="1" class="lobilist-item">
                                                        <div class="lobilist-item-title">Floor coolsdded cinders</div>
                                                        <div class="lobilist-item-description">Thunder fulfilled travellers folly, wading, lake.</div>
                                                        <div class="lobilist-item-duedate">2015-01-31</div>
                                                        <label class="checkbox-inline lobilist-check"><input type="checkbox"></label>
                                                        <div class="todo-actions">
                                                            <div class="edit-todo todo-action">
                                                                <i class="ti-pencil"></i>
                                                            </div>
                                                            <div class="delete-todo todo-action">
                                                                <i class="ti-close"></i>
                                                            </div>
                                                        </div>
                                                        <div class="drag-handler"></div>
                                                    </li>
                                                </ul>
                                                <form class="lobilist-add-todo-form hide">
                                                    <input type="hidden" name="id">
                                                    <div class="form-group">
                                                        <input type="text" name="title" class="form-control" placeholder="TODO title">
                                                    </div>
                                                    <div class="form-group">
                                                        <textarea rows="2" name="description" class="form-control" placeholder="TODO description"></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <input type="text" name="dueDate" class="datepicker form-control hasDatepicker" placeholder="Due Date" id="dp1718646903562"></div>
                                                    <div class="lobilist-form-footer">
                                                        <button class="btn btn-primary btn-sm btn-add-todo">Add/Update</button>
                                                        <button type="button" class="btn btn-danger btn-sm btn-discard-todo">Cancel</button>
                                                    </div>
                                                </form>
                                            </div>



                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                <!-- column -->
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- End Container fluid  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- footer -->
        <!-- ============================================================== -->
        <%@ include file="includes/footer.jspf" %>
        <!-- ============================================================== -->
        <!-- End footer -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- End Page wrapper  -->
    <!-- ============================================================== -->
</div>
<style>
    .drag-handler {
        cursor: move;
    }

    .lobilist-wrapper {
        min-width: 400px; /* Ajusta según tus necesidades */
        max-width: 400px; /* Ajusta según tus necesidades */
        flex: 1 0 auto; /* Evitar que se compriman verticalmente */
        margin: 10px; /* Espacio alrededor de cada tarjeta */
        display: flex;
        flex-direction: column;
    }

    .lobilist-header {
        display: flex;
        padding: 10px;
        border-bottom: 1px solid #ddd;
    }


    .lobilist-footer {
        padding: 10px;
        border-top: 1px solid #ddd;
    }


    .lobilists::-webkit-scrollbar-track {
        background: #f1f1f1; /* Color de fondo del track */
    }

</style>

<script src="/static/web/assets/extra-libs/DataTables/datatables.min.js"></script>
<script src="/static/web/dist/js/pages/datatable/datatable-basic.init.js"></script>
<script src="/static/web/assets/libs/toastr/build/toastr.min.js"></script>
<script src="/static/web/assets/extra-libs/toastr/toastr-init.js"></script>
<script src="/static/web/assets/libs/jquery-steps/build/jquery.steps.min.js"></script>
<script src="/static/web/assets/libs/jquery-validation/dist/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script src="/static/web/assets/libs/jquery/dist/jquery.min.js"></script>
<script src="/static/web/dist/js/funciones/dashboard.js"></script>>}
<script src="/static/web/assets/extra-libs/taskboard/js/jquery.ui.touch-punch-improved.js"></script>
<script src="/static/web/assets/extra-libs/taskboard/js/jquery-ui.min.js"></script>
<!-- Bootstrap tether Core JavaScript -->
<script src="/static/web/assets/libs/popper.js/dist/umd/popper.min.js"></script>
<script src="/static/web/assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- apps -->
<script src="/static/web/dist/js/app.min.js"></script>
<script src="/static/web/dist/js/app.init.js"></script>
<script src="/static/web/dist/js/app-style-switcher.js"></script>
<!-- slimscrollbar scrollbar JavaScript -->
<script src="/static/web/assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
<script src="/static/web/assets/extra-libs/sparkline/sparkline.js"></script>
<!--Wave Effects -->
<script src="/static/web/dist/js/waves.js"></script>
<!--Menu sidebar -->
<script src="/static/web/dist/js/sidebarmenu.js"></script>
<!--Custom JavaScript -->
<script src="/static/web/dist/js/custom.min.js"></script>
<!--This page JavaScript -->
<script src="/static/web/assets/extra-libs/taskboard/js/lobilist.js"></script>
<script src="/static/web/assets/extra-libs/taskboard/js/lobibox.min.js"></script>
<script src="/static/web/assets/extra-libs/taskboard/js/task-init.js"></script>

<!-- This page plugin CSS -->
<link href="/static/web/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css" rel="stylesheet">
<link href="https://cdn.materialdesignicons.com/5.4.55/css/materialdesignicons.min.css" rel="stylesheet">
<link rel="stylesheet" href="/static/web/assets/extra-libs/taskboard/css/lobilist.css">
<link rel="stylesheet" href="/static/web/assets/extra-libs/taskboard/css/jquery-ui.min.css">
<link rel="stylesheet" href="/static/web/assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
<link href="/static/web/dist/css/style.min.css" rel="stylesheet">


<!--  modal: NUEVA VENTA -->

<!-- ============================================================== -->
<!-- End Wrapper -->
<!-- ============================================================== -->
<!-- ============================================================== -->
<!-- customizer Panel - Chat -->
<!-- ============================================================== -->
<!-- ============================================================== -->
<!-- All Jquery -->
<!-- ============================================================== -->
<%@ include file="includes/all-jquery.jspf" %>
</body>
</html>
