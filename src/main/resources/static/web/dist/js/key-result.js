$(document).ready(function () {
    var formData;
    var colaboradoresSeleccionados = [];
    var periodoCompleto = "";
    var periodoId;

    obtenerKeyResults();
    $.ajax({
        type: 'GET',
        url: '/okr/objetivos/list',
        success: function (response) {
            var combo = $('#KeyResultObjetivo');
            combo.empty();
            combo.append($('<option>', {value: '', text: 'Seleccionar objetivo'}));
            response.forEach(function (objetivo) {
                combo.append($('<option>', {
                    value: objetivo.objId,
                    text: objetivo.objNombre
                }));
            });
        },
        error: function (error) {
            console.error('Error al cargar los objetivos:', error);
        }
    });


    $.ajax({
        url: '/okr/requerimientos/proyectos/list',
        type: 'GET',
        success: function (data) {
            var proyectoCombo = $('#KeyResultProyecto');
            proyectoCombo.empty();
            proyectoCombo.append($('<option>', {value: '', text: 'Seleccionar proyecto'}));
            data.forEach(function (proyecto) {
                proyectoCombo.append($('<option>', {
                    value: proyecto.proyId,
                    text: proyecto.proyNombre
                }));
            });
        },
        error: function (xhr, status, error) {
            console.error('Error al obtener proyecto:', error);
        }
    });


    function obtenerKeyResults() {
        $.ajax({
            type: 'GET',
            url: '/okr/key-results/list',
            success: function (response) {
                mostrarKeyResults(response);
                console.log("lo que trae " + response);
            },
            error: function (error) {
                console.error('Error al obtener los Key Results:', error);
            }
        });
    }

    function mostrarKeyResults(keyResults) {
        var tablaResultado = $('#tabla_resultado tbody');
        tablaResultado.empty();

        keyResults.forEach(function (keyResult) {
            var colaboradores = keyResult.colaboradores.map(function (colaborador) {
                var iniciales = colaborador.usuIniciales; // Aquí se obtienen las iniciales del colaborador
                var nombreCompleto = `${colaborador.usuNombre} ${colaborador.usuApePaterno} ${colaborador.usuApeMaterno}`;
                var circuloInicial = `<div class="iniciales-circulo" title="${nombreCompleto}">${iniciales}</div>`;
                return circuloInicial;
            }).join(' ');

            var fila = `
            <tr>
                <td>
                    <h6 class="font-weight-bold">${keyResult.keyNombre}</h6>
                    <small class="text-center">Baseline: ${keyResult.keyBaseline}% |</small>
                    <small class="text-center">Peso: ${keyResult.keyPeso} |</small>
                    <small class="text-center">Unidad: ${keyResult.keyUnidadMedida}</small>
                </td>
                 <td>
                    ${colaboradores}
                </td>
                
                <td>
                
                    <h5 style="text-align: center">${keyResult.periodo.periodoCompleto}</h5>
                    <small style="text-align: center">Inicio: ${new Date(keyResult.periodo.perFechaInicio).toLocaleDateString()}</small><br>
                    <small style="text-align: center">Fin: ${new Date(keyResult.periodo.perFechaFin).toLocaleDateString()}</small>
                </td>
                <td>
                    <h6 class="font-weight-bold" style="text-align: center">
                        ${keyResult.keyEstado}
                    </h6>
                </td>
                <td>
                    <div>
                        <a href="/okr/keyresult/find-by-id?id=${keyResult.keyId}">
                            <i data-toggle="tooltip" data-placement="top" title="Actualizar Datos" class="mdi mdi-account-edit"></i>
                        </a>&nbsp;&nbsp;
                        <a href="#" onclick="eliminarKeyResult(${keyResult.keyId});">
                            <i data-toggle="tooltip" data-placement="top" title="Eliminar Key Result" class="mdi mdi-delete"></i>
                        </a>&nbsp;&nbsp;
                    </div>
                </td>
            </tr>`;
                    tablaResultado.append(fila);
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
            margin: '2px'


        });
    }


    $('#btnGuardarKR').click(function () {
        colaboradoresSeleccionados = []; // Limpiar la lista antes de agregar los nuevos colaboradores
        $('#ColaboradoresDropdown input[type=checkbox]:checked').each(function () {
            colaboradoresSeleccionados.push($(this).val());
        });

        formData = {
            keyNombre: $('#KeyResultnom').val(),
            objId: $('#KeyResultObjetivo').val(),
            proId: $('#KeyResultProyecto').val(),
            keyBaseline: $('#KeyResultbaseline').val(),
            keyPeso: $('#KeyResultpeso').val(),
            keyUnidadMedida: $('#KeyResultSelectUnidad').val(),
            keyColaboradores: colaboradoresSeleccionados,
            perId: periodoId,
            keyEstado: 'Pendiente'
        };
        console.log(formData);

        $.ajax({
            type: 'POST',
            url: '/okr/key-results/create',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function (response) {
                $('#formulario').hide();
                obtenerKeyResults();
                toastr.success('Key Result registrado exitosamente.');
            },
            error: function (error) {
                toastr.error('Error al registrar el Key Result.');
            }
        });
    });

    $('#KeyResultObjetivo').change(function () {
        var objId = $(this).val();

        if (objId) {
            $.ajax({
                type: 'GET',
                url: '/okr/objetivos/' + objId + '/colaboradores',
                success: function (response) {
                    var colaboradores = response.colaboradores;
                    periodoCompleto = response.periodoCompleto;
                    periodoId = response.periodo.perId;
                    $('#keyResultperiodo').val(periodoCompleto);
                    $('#KeyResultfechaI').val(new Date(response.periodo.perFechaInicio).toLocaleDateString());
                    $('#KeyResultfechaF').val(new Date(response.periodo.perFechaFin).toLocaleDateString());

                    var dropdownColaboradores = $('#ColaboradoresDropdown');
                    dropdownColaboradores.empty();

                    colaboradores.forEach(function (colaborador) {
                        var checkbox = $('<input>').attr({
                            type: 'checkbox',
                            value: colaborador.usuId,
                            id: 'chk_' + colaborador.usuId
                        });
                        var label = $('<label>').attr('for', 'chk_' + colaborador.usuId).text(colaborador.usuNombre);

                        dropdownColaboradores.append(
                            $('<div>').addClass('form-check').append(
                                checkbox,
                                label
                            )
                        );
                    });
                },
                error: function (error) {
                    console.error('Error al obtener los colaboradores para el objetivo seleccionado:', error);
                    $('#keyResultperiodo').text('');
                    $('#KeyResultfechaI').text('');
                    $('#KeyResultfechaF').text('');
                    $('#ColaboradoresDropdown').empty();
                }
            });
        } else {
            $('#keyResultperiodo').text('');
            $('#KeyResultfechaI').text('');
            $('#KeyResultfechaF').text('');
            $('#ColaboradoresDropdown').empty();
        }
    });

    function eliminarKeyResult(keyResultId) {
        if (confirm('¿Estás seguro de que deseas eliminar este Key Result?')) {
            $.ajax({
                type: 'DELETE',
                url: '/okr/requerimientos/keyresults/eliminar/' + keyResultId,
                success: function (response) {
                    obtenerKeyResults(); // Actualizar la lista de Key Results
                    toastr.success('Key Result eliminado exitosamente.');
                },
                error: function (error) {
                    toastr.error('Error al eliminar el Key Result.');
                }
            });
        }
    }
});
