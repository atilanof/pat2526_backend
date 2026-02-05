## Documentación API Carrito

| Método | Ruta | Cuerpo (JSON) | Descripción | Respuestas |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/carritos` | N/A | Obtiene la lista de todos los carritos. | 200 OK |
| **POST** | `/carritos` | `{ "idCarrito": 1, "idArticulo": 101, "descripcion": "Boli", "unidades": 2, "precioFinal": 5.0 }` | Crea un nuevo carrito. | 201 Created |
| **GET** | `/carritos/{id}` | N/A | Busca un carrito por su ID. | 200 OK, 404 Not Found |
| **PUT** | `/carritos/{id}` | `{ "idCarrito": 1, "idArticulo": 101, "descripcion": "Boli Rojo", "unidades": 5, "precioFinal": 12.0 }` | Actualiza un carrito existente. | 200 OK, 404 Not Found |
| **DELETE** | `/carritos/{id}` | N/A | Elimina un carrito por ID. | 204 No Content |