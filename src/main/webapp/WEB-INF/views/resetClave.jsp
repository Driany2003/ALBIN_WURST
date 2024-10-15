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

        <!-- Botón para abrir el modal -->
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#resetClaveModal">
            Resetear Clave
        </button>

        <!-- Modal -->
        <div class="modal fade" id="resetClaveModal" tabindex="-1" aria-labelledby="resetClaveModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="resetClaveModalLabel">
                            <i class="fas fa-user-lock" style="margin-right: 5px;"></i>
                            Resetear Contraseña
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="resetClaveForm">
                            <ul class="nav nav-tabs" id="resetPassword" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="resetPassword-modal" data-toggle="tab" role="tab"  aria-selected="true">
                                        <i class="fas fa-key" style="margin-right: 5px;"></i>
                                        Contraseña
                                    </a>
                                </li>
                            </ul>
                            <br>
                            <input type="hidden" id="usuId" value="${usuario.usuId}">

                            <!-- Clave Actual -->
                            <div class="form-group">
                                <label for="claveActual" class="bmd-label-floating">Clave Actual
                                    <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese su clave actual."></i>
                                </label>
                                <div class="input-group">
                                    <input type="password" class="form-control" id="claveActual" placeholder="Ingrese su clave actual" required>
                                    <div class="input-group-append">
                                        <button id="validarClave" type="button" class="btn btn-outline-secondary" title="Validar Clave">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <!-- Nueva Clave (oculta inicialmente) -->
                            <div id="nuevaClaveSection" style="display: none;">
                                <div class="form-group mt-3">
                                    <label for="nuevaClave" class="bmd-label-floating">Nueva Clave
                                        <i class="fas fa-question-circle info-icon" data-toggle="tooltip" data-placement="right" title="Ingrese su nueva clave."></i>
                                    </label>
                                    <input type="password" class="form-control" id="nuevaClave" placeholder="Ingrese su nueva clave" required>
                                </div>
                                <!-- Botón para guardar nueva clave -->
                                <button id="guardarNuevaClave" type="button" class="btn btn-primary">Guardar Nueva Clave</button>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>


        <style>
            .input-group .btn {
                border-radius: 0 0.25rem 0.25rem 0; /* Ajustar bordes */
            }

        </style>


        <%@ include file="includes/footer.jspf" %>
    </div>
</div>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">


<script src="/static/web/dist/js/funciones/resetClave.js"></script>
<%@ include file="includes/all-jquery.jspf" %>
</body>
</html>