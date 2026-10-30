# Reserva de Citas Médicas – Patrones de Diseño (Java)

Ejemplo simple por consola que reserva citas médicas usando 3 patrones de diseño.

## Cómo ejecutar

Requiere Java 8 o superior.

```bash
javac Main.java
java Main
```

## Patrones usados

| Tipo           | Patrón    | Clase                   |
| -------------- | --------- | ----------------------- |
| Creacional     | Singleton | `Agenda`                |
| Estructural    | Adapter   | `WhatsAppAdapter`       |
| Comportamiento | Observer  | `Agenda` + `Observador` |

**Singleton (Creacional):** la clínica tiene una sola agenda. El constructor de `Agenda`
es privado y `getInstancia()` siempre devuelve el mismo objeto, así todas las citas
quedan en el mismo lugar.

**Adapter (Estructural):** `WhatsAppApi` es un servicio externo con su propio método
`sendMessage()`. Nuestro sistema solo sabe usar `notificar()`. `WhatsAppAdapter`
implementa `notificar()` y por dentro llama a `sendMessage()`, haciendo compatibles
las dos interfaces.

**Observer (Comportamiento):** la `Agenda` guarda una lista de observadores. Cada vez
que se reserva una cita, les avisa a todos (`Recepcion` y `WhatsAppAdapter`). Para
agregar otro aviso solo se crea una clase nueva que implemente `Observador`, sin
modificar la `Agenda`.

## Salida

```
Misma agenda? true
[Recepcion] Cita reservada: Ana Perez el 05/11/2026 09:00
[WhatsApp] Cita reservada: Ana Perez el 05/11/2026 09:00
[Recepcion] Cita reservada: Luis Rojas el 05/11/2026 10:00
[WhatsApp] Cita reservada: Luis Rojas el 05/11/2026 10:00
Citas en agenda: [Ana Perez - 05/11/2026 09:00, Luis Rojas - 05/11/2026 10:00]
```
