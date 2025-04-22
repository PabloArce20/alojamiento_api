const baseUrl = 'http://localhost:8080';

// Función para cargar una lista de datos desde un endpoint (personas, clientes, etc.)
function cargarDatos(endpoint) {
    fetch(`${baseUrl}/${endpoint}`) // Se hace la solicitud al endpoint del backend
        .then(response => {
            if (!response.ok) {
                // Si la respuesta no fue exitosa, se lanza un error
                throw new Error(`Error al obtener /${endpoint}`);
            }
            return response.json(); // Convertir la respuesta a JSON
        })
        .then(data => {
            const contenedor = document.getElementById(`${endpoint}-tabla`);
            contenedor.innerHTML = ''; // Limpiar el contenedor antes de insertar nuevos datos

            if (data.length === 0) {
                // Si no hay datos, se muestra un mensaje
                contenedor.innerHTML = '<p>No hay datos disponibles.</p>';
                return;
            }

            const tabla = document.createElement('table'); // Crear tabla HTML
            const encabezado = document.createElement('tr'); // Fila para los encabezados

            // Crear encabezados a partir de las claves del primer objeto
            Object.keys(data[0]).forEach(clave => {
                const th = document.createElement('th');
                th.textContent = clave;
                encabezado.appendChild(th);
            });
            tabla.appendChild(encabezado); // Agregar la fila de encabezado a la tabla

            // Crear filas de datos
            data.forEach(item => {
                const fila = document.createElement('tr');
                // Recorrer los valores de cada objeto y crear celdas <td>
                Object.values(item).forEach(valor => {
                    const td = document.createElement('td');
                    td.textContent = valor;
                    fila.appendChild(td);
                });
                tabla.appendChild(fila); // Agregar la fila completa a la tabla
            });

            contenedor.appendChild(tabla); // Insertar la tabla en el contenedor HTML
        })
        .catch(error => {
            // Captura y muestra errores de red o de respuesta
            console.error(error);
            const contenedor = document.getElementById(`${endpoint}-tabla`);
            contenedor.innerHTML = `<p class="error">No se pudo cargar la información de /${endpoint}</p>`;
        });
}

// Función para cargar un registro específico por ID (por ejemplo, persona con id=3)
function cargarDatosPorId(endpoint, id, tablaId) {
    if (!id) {
        // Validar que se haya ingresado un ID
        document.getElementById(tablaId).innerHTML = '<p>Por favor, ingrese un ID.</p>';
        return;
    }

    fetch(`${baseUrl}/${endpoint}/${id}`) // Solicitud al endpoint con ID
        .then(response => {
            if (!response.ok) {
                // Manejo de errores específicos: 404 si no se encuentra, o error general
                if (response.status === 404) {
                    throw new Error(`No se encontró /${endpoint} con ID ${id}`);
                } else {
                    throw new Error(`Error al obtener /${endpoint}/${id}`);
                }
            }
            return response.json(); // Convertir a JSON
        })
        .then(data => {
            const contenedor = document.getElementById(tablaId);
            contenedor.innerHTML = ''; // Limpiar antes de mostrar nueva info

            const tabla = document.createElement('table'); // Crear tabla
            const encabezado = document.createElement('tr'); // Fila de encabezado
            const filaDatos = document.createElement('tr'); // Fila con los valores

            // Construir la fila de encabezado y la fila de datos en paralelo
            Object.keys(data).forEach(clave => {
                const th = document.createElement('th');
                th.textContent = clave;
                encabezado.appendChild(th);

                const td = document.createElement('td');
                td.textContent = data[clave];
                filaDatos.appendChild(td);
            });

            tabla.appendChild(encabezado);
            tabla.appendChild(filaDatos);
            contenedor.appendChild(tabla); // Insertar la tabla en el DOM
        })
        .catch(error => {
            // Mostrar errores si la solicitud falla
            console.error(error);
            const contenedor = document.getElementById(tablaId);
            contenedor.innerHTML = `<p class="error">${error.message}</p>`;
        });
}
