function addTask() {
    const taskInput = document.getElementById('new-task');
    const taskText = taskInput.value;
    if (taskText === '') return;

    const newTask = document.createElement('li');
    newTask.textContent = taskText;
    newTask.onclick = moveTask;

    document.getElementById('todo').querySelector('ul').appendChild(newTask);
    taskInput.value = '';
}

function moveTask() {
    const currentColumn = this.parentNode.parentNode.id;
    let nextColumn;

    if (currentColumn === 'todo') {
        nextColumn = 'doing';
    } else if (currentColumn === 'doing') {
        nextColumn = 'done';
    } else {
        return;
    }

    document.getElementById(nextColumn).querySelector('ul').appendChild(this);
}
