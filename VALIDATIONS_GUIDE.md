# Guía de Validaciones del Microservicio de Facturas

Este documento describe todas las validaciones implementadas en el microservicio de facturas y cómo funcionan los errores 400 (Bad Request) y 404 (Not Found).

## 📋 Índice
1. [Tipos de Errores](#tipos-de-errores)
2. [Validaciones por Entidad](#validaciones-por-entidad)
3. [Ejemplos de Respuestas](#ejemplos-de-respuestas)
4. [Ejemplos de Peticiones](#ejemplos-de-peticiones)

---

## 🔴 Tipos de Errores

### Error 400 - Bad Request
Se devuelve cuando:
- Los datos enviados no cumplen con las validaciones de campos (Bean Validation)
- Los datos no cumplen con las reglas de negocio
- Se envía un tipo de dato incorrecto (ej: texto donde se espera un número)

### Error 404 - Not Found
Se devuelve cuando:
- Se busca un recurso que no existe
- Se intenta actualizar o eliminar un recurso inexistente

---

## ✅ Validaciones por Entidad

### Product (Producto)

#### Validaciones de Campos:
- **name** (nombre):
  - ✓ Obligatorio (no puede estar vacío)
  - ✓ Longitud mínima: 2 caracteres
  - ✓ Longitud máxima: 100 caracteres

- **price** (precio):
  - ✓ Debe ser mayor a 0
  - ✓ No puede ser negativo

#### Validaciones de Negocio:
- El ID debe ser un número positivo (para búsquedas)
- El nombre no puede estar en blanco
- El precio debe ser mayor a 0

---

### Invoice (Factura)

#### Validaciones de Campos:
- **clientId** (ID del cliente):
  - ✓ Obligatorio (no puede estar vacío)
  - ✓ Longitud mínima: 1 carácter
  - ✓ Longitud máxima: 50 caracteres

- **clientName** (nombre del cliente):
  - ✓ Obligatorio (no puede estar vacío)
  - ✓ Longitud mínima: 2 caracteres
  - ✓ Longitud máxima: 100 caracteres

- **totalBeforeTaxes** (total antes de impuestos):
  - ✓ Debe ser mayor o igual a 0

- **taxes** (impuestos):
  - ✓ Debe ser mayor o igual a 0

- **totalAfterTaxes** (total después de impuestos):
  - ✓ Debe ser mayor o igual a 0

#### Validaciones de Negocio:
- El ID debe ser un número positivo (para búsquedas)
- El ID del cliente es obligatorio
- El nombre del cliente es obligatorio
- Los montos no pueden ser negativos
- **Validación de coherencia**: El total después de impuestos debe ser igual a la suma del subtotal más los impuestos (con margen de error de 0.01)

---

### InvoiceDetails (Detalle de Factura)

#### Validaciones de Campos:
- **totalprice** (precio total):
  - ✓ Debe ser mayor o igual a 0

- **product** (producto):
  - ✓ Obligatorio (no puede ser null)

- **invoice** (factura):
  - ✓ Obligatoria (no puede ser null)

#### Validaciones de Negocio:
- El ID debe ser un número positivo (para búsquedas)
- El producto es obligatorio
- La factura es obligatoria
- El precio total no puede ser negativo

---

## 📤 Ejemplos de Respuestas

### Error 404 - Recurso No Encontrado

```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 404,
  "error": "Not Found",
  "message": "Producto con id 999 no encontrado",
  "path": "/invoices/products/999"
}
```

### Error 400 - Validación de Campos

```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Validation Failed",
  "message": "Error de validación en los campos enviados",
  "errors": [
    {
      "field": "name",
      "rejectedValue": "",
      "message": "El nombre del producto es obligatorio"
    },
    {
      "field": "price",
      "rejectedValue": -10.0,
      "message": "El precio debe ser mayor a 0"
    }
  ],
  "path": "/invoices/products"
}
```

### Error 400 - Bad Request (Regla de Negocio)

```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Bad Request",
  "message": "El total después de impuestos no coincide con la suma del subtotal más impuestos",
  "path": "/invoices"
}
```

### Error 400 - Tipo de Dato Incorrecto

```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Bad Request",
  "message": "El parámetro 'id' debe ser de tipo Long",
  "path": "/invoices/products/abc"
}
```

---

## 🔧 Ejemplos de Peticiones

### ✅ Crear Producto - Correcto

**POST** `/invoices/products`

```json
{
  "name": "Laptop Dell XPS 15",
  "price": 1299.99
}
```

**Respuesta: 201 Created**
```json
{
  "id": 1,
  "name": "Laptop Dell XPS 15",
  "price": 1299.99,
  "createdAt": "2024-01-20T10:30:45.123",
  "updatedAt": "2024-01-20T10:30:45.123"
}
```

---

### ❌ Crear Producto - Error de Validación

**POST** `/invoices/products`

```json
{
  "name": "",
  "price": -100
}
```

**Respuesta: 400 Bad Request**
```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Validation Failed",
  "message": "Error de validación en los campos enviados",
  "errors": [
    {
      "field": "name",
      "rejectedValue": "",
      "message": "El nombre del producto es obligatorio"
    },
    {
      "field": "price",
      "rejectedValue": -100.0,
      "message": "El precio debe ser mayor a 0"
    }
  ],
  "path": "/invoices/products"
}
```

---

### ✅ Crear Factura - Correcto

**POST** `/invoices`

```json
{
  "clientId": "CLI-001",
  "clientName": "Juan Pérez",
  "totalBeforeTaxes": 1000.00,
  "taxes": 120.00,
  "totalAfterTaxes": 1120.00
}
```

**Respuesta: 201 Created**
```json
{
  "id": 1,
  "clientId": "CLI-001",
  "clientName": "Juan Pérez",
  "totalBeforeTaxes": 1000.00,
  "taxes": 120.00,
  "totalAfterTaxes": 1120.00,
  "invoiceDetails": [],
  "createdAt": "2024-01-20T10:30:45.123",
  "updatedAt": "2024-01-20T10:30:45.123"
}
```

---

### ❌ Crear Factura - Error de Coherencia

**POST** `/invoices`

```json
{
  "clientId": "CLI-001",
  "clientName": "Juan Pérez",
  "totalBeforeTaxes": 1000.00,
  "taxes": 120.00,
  "totalAfterTaxes": 1500.00
}
```

**Respuesta: 400 Bad Request**
```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Bad Request",
  "message": "El total después de impuestos no coincide con la suma del subtotal más impuestos",
  "path": "/invoices"
}
```

---

### ❌ Buscar Producto Inexistente

**GET** `/invoices/products/999`

**Respuesta: 404 Not Found**
```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 404,
  "error": "Not Found",
  "message": "Producto con id 999 no encontrado",
  "path": "/invoices/products/999"
}
```

---

### ❌ ID Inválido (Tipo Incorrecto)

**GET** `/invoices/products/abc`

**Respuesta: 400 Bad Request**
```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Bad Request",
  "message": "El parámetro 'id' debe ser de tipo Long",
  "path": "/invoices/products/abc"
}
```

---

### ❌ Crear Producto - Nombre Muy Corto

**POST** `/invoices/products`

```json
{
  "name": "A",
  "price": 100.00
}
```

**Respuesta: 400 Bad Request**
```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Validation Failed",
  "message": "Error de validación en los campos enviados",
  "errors": [
    {
      "field": "name",
      "rejectedValue": "A",
      "message": "El nombre debe tener entre 2 y 100 caracteres"
    }
  ],
  "path": "/invoices/products"
}
```

---

### ❌ Crear Factura - Cliente Vacío

**POST** `/invoices`

```json
{
  "clientId": "",
  "clientName": "",
  "totalBeforeTaxes": 100.00,
  "taxes": 12.00,
  "totalAfterTaxes": 112.00
}
```

**Respuesta: 400 Bad Request**
```json
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Validation Failed",
  "message": "Error de validación en los campos enviados",
  "errors": [
    {
      "field": "clientId",
      "rejectedValue": "",
      "message": "El ID del cliente es obligatorio"
    },
    {
      "field": "clientName",
      "rejectedValue": "",
      "message": "El nombre del cliente es obligatorio"
    }
  ],
  "path": "/invoices"
}
```

---

## 🛠️ Componentes de Validación Implementados

### 1. **Validaciones de Bean Validation** (Entidades)
- Anotaciones: `@NotBlank`, `@Positive`, `@PositiveOrZero`, `@NotNull`, `@Size`
- Se validan automáticamente en los controladores con `@Valid`

### 2. **Validaciones de Negocio** (Servicios)
- Validación de IDs positivos
- Validación de campos obligatorios
- Validación de coherencia de datos (ej: totales de factura)
- Lanzamiento de excepciones personalizadas: `BadRequestException`, `InvoiceEntityNotFoundException`

### 3. **Manejo Global de Excepciones** (GlobalExceptionHandler)
- `@RestControllerAdvice`: Intercepta todas las excepciones
- Transforma excepciones en respuestas JSON estructuradas
- Maneja diferentes tipos de errores:
  - `MethodArgumentNotValidException` → 400 con detalles de campos
  - `BadRequestException` → 400 con mensaje personalizado
  - `InvoiceEntityNotFoundException` / `ResourceNotFoundException` → 404
  - `MethodArgumentTypeMismatchException` → 400 con tipo esperado
  - `Exception` → 500 (error interno del servidor)

---

## 📝 Notas Importantes

1. **Todas las respuestas de error incluyen**:
   - `timestamp`: Fecha y hora del error
   - `status`: Código HTTP del error
   - `error`: Tipo de error
   - `message`: Mensaje descriptivo
   - `path`: Ruta del endpoint que generó el error

2. **Las validaciones de Bean Validation** se ejecutan automáticamente antes de llegar al servicio gracias a la anotación `@Valid` en los controladores.

3. **Las validaciones de negocio** se ejecutan en la capa de servicio y pueden lanzar excepciones personalizadas.

4. **El GlobalExceptionHandler** captura todas las excepciones y las transforma en respuestas HTTP estructuradas y claras.

5. **Los mensajes están en español** para facilitar la comprensión del usuario final.

---

## 🚀 Endpoints Disponibles

### Productos
- `GET /invoices/products` - Listar todos los productos
- `GET /invoices/products/{id}` - Buscar producto por ID
- `POST /invoices/products` - Crear nuevo producto

### Facturas
- `GET /invoices` - Listar todas las facturas
- `GET /invoices/{id}` - Buscar factura por ID
- `POST /invoices` - Crear nueva factura
- `PUT /invoices/{id}` - Actualizar factura existente
- `DELETE /invoices/{id}` - Eliminar factura

### Detalles de Factura
- `GET /invoices/details` - Listar todos los detalles
- `GET /invoices/details/{id}` - Buscar detalle por ID
- `POST /invoices/details` - Crear nuevo detalle
- `PUT /invoices/details/{id}` - Actualizar detalle existente
- `DELETE /invoices/details/{id}` - Eliminar detalle

---

## ✨ Ventajas de este Sistema de Validaciones

1. **Mensajes claros y estructurados**: Los usuarios reciben información detallada sobre qué salió mal
2. **Validación en múltiples capas**: Bean Validation (entidades) + Validaciones de negocio (servicios)
3. **Respuestas consistentes**: Todas las respuestas de error siguen el mismo formato
4. **Fácil mantenimiento**: Las validaciones están centralizadas y bien organizadas
5. **Seguridad**: Se validan los datos antes de procesarlos
6. **Debugging facilitado**: Los mensajes de error incluyen toda la información necesaria

---

**Fecha de última actualización**: 2024-01-20
**Versión del documento**: 1.0

