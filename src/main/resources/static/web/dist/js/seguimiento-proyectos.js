$(document).ready(function() {
    $('#todo-lists-basic .lobilist').sortable({
        connectWith: '#todo-lists-basic .lobilist .lobilist-items',
        handle: '.drag-handler',
        stop: function(event, ui) {
            console.log('Elemento movido', ui.item);
            // Aquí puedes añadir lógica adicional para el ordenamiento si es necesario
        }
    }).disableSelection();
});

