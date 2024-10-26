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

    <!-- =========================MAMASHAROOO===================================== -->
    <div class="page-wrapper">
        <div class="page-breadcrumb">
            <div class="row">
                <div class="col-12 align-self-center">
                    <h4 class="page-title">INVENTARIO <span class="label label-rounded label-info">Listado</span></h4>
                </div>
            </div>
        </div>
        <br><br>
        <div id="product-card" class="product-card">

        </div>
        <%@ include file="includes/footer.jspf" %>
    </div>

    <style>
        .img-container {
            background-color: #e0f7fa;
            padding: 10px;
            border-radius: 5px;
            display: inline-block;
        }

        /* Ajuste del tamaño de la imagen */
        .img-product-list {
            width: 150px;
            height: auto;
            display: block;
        }

        .container-fluid {
            border: 1px solid #ddd;
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 8px;
        }

        ul.list-unstyled li.media-product {
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
            margin-bottom: 10px;
        }

        .media-product-title {
            margin-bottom: 20px;
        }

        .media-product-options {
            margin-top: 15px;
        }

        .media-product-options a, .media-product-options button {
            margin-left: 5px;
        }

        .product-card {
            background-color: #f8f9fa; /* Fondo gris claro */
            border: 1px solid #d1d1d1; /* Borde gris claro */
            border-radius: 10px; /* Bordes redondeados */
            padding: 15px; /* Espacio interno */
            margin-bottom: 20px; /* Espacio inferior entre tarjetas */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Sombra sutil para darle relieve */
        }

        /* Contenedor de la imagen con fondo celeste */
        .img-container {
            background-color: #e0f7fa; /* Celeste muy bajito */
            padding: 10px; /* Espacio entre la imagen y el borde del contenedor */
            border-radius: 5px; /* Bordes ligeramente redondeados */
            display: inline-block; /* Ajusta el tamaño al contenido */
        }

        /* Ajuste del tamaño de la imagen */
        .img-product-list {
            width: 150px; /* Ajusta el ancho según el tamaño deseado */
            height: auto; /* Mantiene la proporción de la imagen */
            display: block; /* Hace que la imagen sea un bloque dentro del contenedor */
        }


    </style>

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

    <script src="/static/web/dist/js/funciones/productoInventario.js"></script>
    <%@ include file="includes/all-jquery.jspf" %>
</body>
</html>