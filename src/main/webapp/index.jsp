<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personas</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Lista de Personas</h1>
        <table id="personsTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Edad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <!-- Datos se cargarán aquí dinámicamente -->
            </tbody>
        </table>

        <h2>Agregar Persona</h2>
        <form id="addPersonForm">
            <input type="text" id="name" placeholder="Nombre" required>
            <input type="number" id="age" placeholder="Edad" required>
            <button type="submit">Agregar</button>
        </form>
    </div>
    <script src="js/script.js"></script>
</body>
</html>