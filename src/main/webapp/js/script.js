document.addEventListener('DOMContentLoaded', function() {
    fetchPersons();

    const form = document.getElementById('addPersonForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        addPerson();
    });
});

function fetchPersons() {
    fetch('/usuarios/usuarios')
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById('personsTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = '';
            data.forEach(person => {
                let row = tableBody.insertRow();
                row.innerHTML = `
                    <td>${person.id}</td>
                    <td>${person.nombre}</td>
                    <td>${person.anios}</td>
                    <td><button onclick="deletePerson(${person.id})">Eliminar</button></td>
                `;
            });
        })
        .catch(error => console.error('Error fetching persons:', error));
}

function addPerson() {
    const name = document.getElementById('name').value;
    const age = document.getElementById('age').value;

    const person = {
        nombre: name,
        anios: parseInt(age, 10)
    };

    fetch('/usuarios/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(person)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text); });
        }
        return response.json();
    })
    .then(data => {
        document.getElementById('name').value = '';
        document.getElementById('age').value = '';
        fetchPersons();
    })
    .catch(error => console.error('Error adding person:', error));
}

function deletePerson(id) {
    fetch(`/usuarios/usuarios/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text); });
        }
        fetchPersons();
    })
    .catch(error => console.error('Error deleting person:', error));
}