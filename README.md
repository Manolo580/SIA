Sistema Veterinario

Guía rápida de uso. Al abrir el programa, elige el modo:

- **1. Modo Consola**
- **2. Modo Ventana**

---

## Clientes

- Al registrar un cliente, **el RUT va sin puntos ni guion**. Ejemplo: `112223334`.
- El nombre y el teléfono no pueden quedar vacíos.
- Para **buscar, editar o eliminar** un cliente, se hace por su RUT.
- Al **eliminar** un cliente, se eliminan también todas sus mascotas.
- Al **editar**, puedes cambiar solo el teléfono, o el nombre y el teléfono juntos.

## Mascotas

- Para registrar una mascota, primero debe existir el cliente dueño (se busca por RUT).
- El **ID de la mascota** es a elección tuya, y no puede repetirse con uno ya existente.
- La especie puede ser **Perro**, **Gato**, u **Otro** (si es otro, se escribe la especie a mano).
- La raza es opcional: si se deja vacía, queda como "Sin raza".
- Para **buscar, editar o eliminar** una mascota, se hace por su ID.
- Al **editar**, puedes cambiar solo el nombre, o el nombre y la edad juntos.
- Al **eliminar** una mascota, el cliente dueño no se ve afectado (sigue registrado con el resto de sus mascotas).

## Servicios

- Cada servicio tiene un **ID** (a elección) y un **tipo** (ej: Baño, Vacuna, Consulta Medica, Urgencia).
- El precio de un servicio **no se define acá**: se define al agendar una cita.

## Citas

- Para agendar una cita se necesita: el RUT del cliente, el ID de una mascota **de ese cliente**, y el ID de un servicio **ya creado**.
- La **fecha** va en formato `dd-mm-aaaa` (ejemplo: `08-09-2026`).
- El **descuento** es opcional: si no aplica, se deja vacío.
- **Calendario**: muestra solo las citas pendientes, ordenadas por fecha y hora.
- **Historial**: muestra solo las citas ya finalizadas.
- Para **finalizar** una cita se necesita el ID de la cita. Si el servicio es médico (contiene "medic", "urgencia" o "vacuna" en el tipo), se puede ingresar un resultado y, si se quiere, actualizar el estado de salud de la mascota.

## Guardar los datos

- **Modo Consola**: se guarda automático al elegir "Salir" en el menú principal.
- **Modo Ventana**: **no se guarda solo**. Hay que apretar el botón "Guardar Datos" (abajo de la ventana) antes de cerrar. Si cierras con la X sin apretarlo, se pierde lo hecho en esa sesión.
