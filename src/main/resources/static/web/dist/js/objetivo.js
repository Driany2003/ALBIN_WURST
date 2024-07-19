$(document).ready(function () {
    var periodoId;
    var areasMap = {};
    var responsablesMap = {};
    var colaboradoresMap = {};
    var usuariosPorArea = {};
    var usuariosArea = {};

    cargarObjetivos();
    cargarAreas().then(setPeriodoAutomatico);


    function setPeriodoAutomatico() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: '/okr/configuraciones/periodos/trimestre-actual', type: 'GET', success: function (periodo) {
                    if (periodo) {
                        var periodoCompleto = periodo.perPeriodo + '-' + periodo.perTrimestre;
                        $('#Objetivotperiodo').val(periodoCompleto);
                        $('#ObjetivofechaI').val(new Date(periodo.perFechaInicio).toLocaleDateString());
                        $('#ObjetivofechaF').val(new Date(periodo.perFechaFin).toLocaleDateString());
                        periodoId = periodo.perId;
                        resolve();
                    } else {
                        reject('Período actual no encontrado');
                    }
                }, error: function (xhr, status, error) {
                    console.error('Error al obtener el período actual:', error);
                    reject(error);
                }
            });
        });
    }

    $('#btnGuardarOBJ').click(function () {
        var nombreObjetivo = $('#Objetivonom').val();
        var areaIds = obtenerAreasSeleccionadas();
        var periodo = $('#Objetivotperiodo').val();
        var fechaInicio = $('#ObjetivofechaI').val();
        var fechaFin = $('#ObjetivofechaF').val();
        var descripcion = $('#Objetivodescripcion').val();

        mostrarConfirmacionRegistro(nombreObjetivo, areaIds, periodo, fechaInicio, fechaFin, descripcion);
    });

    function mostrarConfirmacionRegistro(nombreObjetivo, areaIds, periodo, fechaInicio, fechaFin, descripcion) {
        var responsables = obtenerUsuariosSeleccionados('.propietario-checkbox:checked');
        var colaboradores = obtenerUsuariosSeleccionados('.colaborador-checkbox:checked');
        usuariosPorArea = obtenerUsuariosPorAreasSeleccionadas(areaIds);
        var responsablesNombres = responsables.map(id => responsablesMap[id] || 'Desconocido');
        var colaboradoresNombres = colaboradores.map(id => colaboradoresMap[id] || 'Desconocido');
        var nombresAreas = obtenerNombresAreasSeleccionadas();

        $('#confirmObjetivo').text(nombreObjetivo);
        $('#confirmArea').text(nombresAreas.join(', '));
        $('#confirmPeriodo').text(periodo);
        $('#confirmFechaInicio').text(fechaInicio);
        $('#confirmFechaFin').text(fechaFin);
        $('#confirmResponsables').text(responsablesNombres.join(', '));
        $('#confirmColaborador').text(colaboradoresNombres.join(', '));
        $('#confirmDescripcion').text(descripcion);

        $('#confirmarRegistroModal').modal('show');
        $('#btnConfirmarRegistro').off('click').on('click', function () {
            var promises = [];

            areaIds.forEach(function (areaId) {
                usuariosArea = usuariosPorArea[areaId] || {responsables: [], colaboradores: []};
                var data = {
                    periodoId: periodoId,
                    objEstado: 'En Proceso',
                    objNombre: nombreObjetivo,
                    objIsActive: '1',
                    objDescripcion: descripcion,
                    objUsuCreador: 13,
                    objFechaCreacion: new Date().toISOString(),
                    objUsuResponsables: usuariosArea.responsables.map(String),
                    objUsuColaboradores: usuariosArea.colaboradores.map(String),
                    objAreas: [areaId]
                };

                var promise = crearObjetivo(data);
                promises.push(promise);
            });


            Promise.all(promises)
                .then(function (responses) {
                    $('#confirmarRegistroModal').modal('hide');
                    toastr.success('Objetivos registrados exitosamente.');
                    cargarObjetivos();
                })
                .catch(function (error) {
                    toastr.error('Error al registrar uno o más objetivos.');
                });
        });
    }

    function obtenerUsuariosSeleccionados(selector) {
        var idsSeleccionados = [];
        $(selector).each(function () {
            if ($(this).is(':checked')) {
                idsSeleccionados.push($(this).val());
            }
        });
        return idsSeleccionados.map(Number);
    }

    function obtenerUsuariosPorAreasSeleccionadas(areaIds) {
        var usuariosPorArea = {};

        areaIds.forEach(function (areaId) {
            var responsables = obtenerUsuariosSeleccionados('.propietario-checkbox:checked[data-area="' + areaId + '"]');
            var colaboradores = obtenerUsuariosSeleccionados('.colaborador-checkbox:checked[data-area="' + areaId + '"]');

            usuariosPorArea[areaId] = {
                responsables: responsables,
                colaboradores: colaboradores
            };
        });

        return usuariosPorArea;
    }

    function obtenerAreasSeleccionadas() {
        var areaIds = [];
        $('#areasDropdown .dropdown-item input[type="checkbox"]:checked').each(function () {
            areaIds.push($(this).val());
        });
        return areaIds;
    }

    function obtenerNombresAreasSeleccionadas() {
        var nombresAreas = [];
        $('#areasDropdown .dropdown-item input[type="checkbox"]:checked').each(function () {
            nombresAreas.push($(this).next('label').text().trim());
        });
        return nombresAreas;
    }

    //formulario completar areas
    function cargarAreas() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: '/okr/empresa/areas/list', type: 'GET', success: function (data) {
                    var dropdownMenu = $('#areasDropdown');
                    dropdownMenu.empty();

                    data.forEach(function (area) {
                        areasMap[area.areaId] = area.areaNombre;

                        dropdownMenu.append(`
                            <a class="dropdown-item">
                                <input type="checkbox" id="area_${area.areaId}" value="${area.areaId}">
                                <label for="area_${area.areaId}">${area.areaNombre}</label>
                            </a>
                        `);
                    });

                    dropdownMenu.find('.dropdown-item input[type="checkbox"]').change(function () {
                        var selectedAreas = obtenerAreasSeleccionadas();
                        console.log('Áreas seleccionadas:', selectedAreas);
                        if (selectedAreas.length > 0) {
                            cargarUsuariosPorAreas(selectedAreas);
                        } else {
                            limpiarComboPropietarios();
                        }
                    });

                    resolve();
                }, error: function (xhr, status, error) {
                    console.error('Error al obtener las áreas:', error);
                    reject(error);
                }
            });
        });
    }

    function cargarUsuariosPorAreas(areaIds) {

        $.ajax({
            type: 'GET', url: '/okr/empresa/areas/usuarios/' + areaIds.join(','),
            traditional: true,
            success: function (response) {
                var comboPropietarios = $('#propietariosCheckboxes');
                var comboColaboradores = $('#colaboradoresCheckboxes');
                comboPropietarios.empty();
                comboColaboradores.empty();

                response.forEach(function (usuario) {
                    var areaNombre = areasMap[usuario.areaId] || 'Área desconocida';

                    var usuarioLabel = `${usuario.usuNombre} / ${areaNombre}`;

                    // Crear checkbox para propietario
                    var checkboxPropietario = $(`
                    <div class="form-check">
                        <input class="form-check-input propietario-checkbox" type="checkbox" id="propietario_${usuario.usuId}" value="${usuario.usuId}" data-area="${usuario.areaId}">
                        <label class="form-check-label" for="propietario_${usuario.usuId}">
                            ${usuarioLabel}
                        </label>
                    </div>
                `);

                    comboPropietarios.append(checkboxPropietario);

                    var isColaboradorSelected = obtenerUsuariosSeleccionados('.propietario-checkbox:checked').includes(usuario.usuId.toString());

                    if (!isColaboradorSelected) {
                        comboColaboradores.append(`
                        <div class="form-check">
                            <input class="form-check-input colaborador-checkbox" type="checkbox" id="colaborador_${usuario.usuId}" value="${usuario.usuId}" data-area="${usuario.areaId}">
                            <label class="form-check-label" for="colaborador_${usuario.usuId}">
                                ${usuarioLabel}
                            </label>
                        </div>
                    `);
                    }


                    responsablesMap[usuario.usuId.toString()] = usuarioLabel;
                    colaboradoresMap[usuario.usuId.toString()] = usuarioLabel;

                    // Evento de cambio para checkboxes de propietarios
                    checkboxPropietario.find('.propietario-checkbox').change(function () {
                        var isChecked = $(this).prop('checked');
                        var colaboradorCheckbox = $('#colaborador_' + usuario.usuId);

                        if (isChecked) {
                            colaboradorCheckbox.prop('disabled', true);
                        } else {
                            colaboradorCheckbox.prop('disabled', false);
                        }
                    });

                    // Evento de cambio para checkboxes de colaboradores
                    var checkboxColaborador = $('#colaborador_' + usuario.usuId);
                    checkboxColaborador.change(function () {
                        var isChecked = $(this).prop('checked');
                        var propietarioCheckbox = $('#propietario_' + usuario.usuId);

                        if (isChecked) {
                            propietarioCheckbox.prop('disabled', true);
                        } else {
                            propietarioCheckbox.prop('disabled', false);
                        }
                    });
                });
            }, error: function (xhr, status, error) {
                console.error('Error al obtener los usuarios por áreas:', error);
                toastr.error('Error al cargar los usuarios por áreas.');
            }
        });
    }

    function limpiarComboPropietarios() {
        $('#propietariosCheckboxes').empty();
        $('#colaboradoresCheckboxes').empty();
    }

    function crearObjetivo(data) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: '/okr/objetivos/create', type: 'POST', contentType: 'application/json', data: JSON.stringify(data), success: function (response) {
                    resolve(response);
                    console.log(JSON.stringify(data));
                },

                error: function (xhr, status, error) {
                    console.error('Error al crear el objetivo:', error);
                    reject(error);
                }
            });
        });
    }

    function cargarObjetivos() {
        $.ajax({
            url: '/okr/objetivos/list',
            type: 'GET',
            success: function (data) {
                mostrarObjetivos(data);
            }, error: function (xhr, status, error) {
                console.error('Error al obtener la lista de objetivos:', error);
            }
        });
    }

    function mostrarObjetivos(objetivos) {
        var tablaBody = $('#tabla_resultado tbody');
        tablaBody.empty();

        objetivos.forEach(function (objetivo) {
            var nombreObjetivo = objetivo.objNombre;

            var areaAsignada = objetivo.area.areaNombre;

            // Formatear responsables
            var responsables = objetivo.responsable.map(function (responsable) {
                var iniciales = responsable.usuIniciales;
                var nombreCompleto = `${responsable.usuNombre} ${responsable.usuApePaterno} ${responsable.usuApeMaterno}`;
                var circuloInicial = `<div class="iniciales-circulo" title="${nombreCompleto}">${iniciales}</div>`;
                return circuloInicial;
            }).join(' ');

                responsables += `<span class="iniciales-circulo mas-responsables" title="Más responsables" 
                data-responsables='${JSON.stringify(objetivo.responsable)}' data-colaboradores='${JSON.stringify(objetivo.colaboradores)}'>+</span>`;


            var estadoObjetivo = objetivo.objEstado;

            var tr = `
        <tr>
            <td>
                <span class="objetivo-nombre">${nombreObjetivo}</span>
                <br>
                <span class="objetivo-areas">Áreas: ${areaAsignada}</span>
            </td>
            <td>${responsables}</td>
           <td>
            <h5 style="text-align: center">${objetivo.periodo.periodoCompleto}</h5>
            <small style="text-align: center">Inicio: ${new Date(objetivo.periodo.perFechaInicio).toLocaleDateString()}</small><br>
            <small style="text-align: center">Fin: ${new Date(objetivo.periodo.perFechaFin).toLocaleDateString()}</small>
        </td>
            <td>${estadoObjetivo}</td>
            <td></td>
        </tr>
        `;

            tablaBody.append(tr);
        });

        $('.iniciales-circulo').css({
            display: 'inline-block',
            width: '40px',
            height: '40px',
            lineHeight: '40px',
            textAlign: 'center',
            borderRadius: '50%',
            backgroundColor: '#007bff',
            color: '#fff',
            margin: '3px',
            padding: '3px'
        });

        $('.mas-responsables').click(function () {
            var responsables = JSON.parse($(this).attr('data-responsables') || '[]');
            var colaboradores = JSON.parse($(this).attr('data-colaboradores') || '[]');

            var modalBody = $('#usuariosModal .modal-body');
            modalBody.empty();

            modalBody.append('<h6>Responsables:</h6>');
            responsables.forEach(function (responsable) {
                modalBody.append(`<p>${responsable.usuNombre} (${responsable.usuIniciales})</p>`);
            });

            modalBody.append('<h6>Colaboradores:</h6>');
            colaboradores.forEach(function (colaborador) {
                modalBody.append(`<p>${colaborador.usuNombre} (${colaborador.usuIniciales})</p>`);
            });

            $('#usuariosModal').modal('show');
        });
    }

});
