# Sistema de Validaciones - Microservicio de Facturas ✅

## 📌 Resumen de Implementación

Se ha implementado un **sistema completo de validaciones** para el microservicio de facturas que maneja correctamente los errores **400 (Bad Request)** y **404 (Not Found)** con respuestas HTTP estructuradas y mensajes claros.

---

## 🎯 Lo que se implementó

### 1. **Dependencias Agregadas**
```kotlin
// build.gradle.kts
implementation("org.springframework.boot:spring-boot-starter-validation")
```

### 2. **Clases de Respuesta de Error**

#### `ErrorResponse.kt`
Respuesta estructurada para errores 404 y 400 generales:
```kotlin
data class ErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val path: String?
)
```

#### `ValidationErrorResponse.kt`
Respuesta estructurada para errores 400 de validación de campos:
```kotlin
data class ValidationErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val errors: List<FieldError>,
    val path: String?
)

data class FieldError(
    val field: String,
    val rejectedValue: Any?,
    val message: String
)
```

### 3. **Excepciones Personalizadas**

#### `ResourceNotFoundException.kt`
Para recursos no encontrados (404)

#### `BadRequestException.kt`
Para validaciones de negocio fallidas (400)

#### `InvoiceEntityNotFoundException.kt`
Para entidades de factura no encontradas (404)

### 4. **GlobalExceptionHandler Mejorado**

Maneja todos los tipos de errores:
- ✅ `InvoiceEntityNotFoundException` → 404
- ✅ `ResourceNotFoundException` → 404
- ✅ `BadRequestException` → 400
- ✅ `MethodArgumentNotValidException` → 400 (validación de campos)
- ✅ `MethodArgumentTypeMismatchException` → 400 (tipo incorrecto)
- ✅ `Exception` → 500 (error interno)

Todas las respuestas incluyen:
- Timestamp
- Status HTTP
- Tipo de error
- Mensaje descriptivo
- Path del endpoint
- Detalles de campos (en caso de validación)

### 5. **Validaciones en Entidades (Bean Validation)**

#### **Product**
```kotlin
@field:NotBlank(message = "El nombre del producto es obligatorio")
@field:Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
val name: String

@field:Positive(message = "El precio debe ser mayor a 0")
val price: Double
```

#### **Invoice**
```kotlin
@field:NotBlank(message = "El ID del cliente es obligatorio")
@field:Size(min = 1, max = 50, message = "El ID del cliente debe tener entre 1 y 50 caracteres")
val clientId: String

@field:NotBlank(message = "El nombre del cliente es obligatorio")
@field:Size(min = 2, max = 100, message = "El nombre del cliente debe tener entre 2 y 100 caracteres")
val clientName: String

@field:PositiveOrZero(message = "El total antes de impuestos debe ser mayor o igual a 0")
val totalBeforeTaxes: Double

@field:PositiveOrZero(message = "Los impuestos deben ser mayor o igual a 0")
val taxes: Double

@field:PositiveOrZero(message = "El total después de impuestos debe ser mayor o igual a 0")
val totalAfterTaxes: Double
```

#### **InvoiceDetails**
```kotlin
@field:PositiveOrZero(message = "El precio total debe ser mayor o igual a 0")
val totalprice: Double

@field:NotNull(message = "El producto es obligatorio")
val product: Product?

@field:NotNull(message = "La factura es obligatoria")
val invoice: Invoice?
```

### 6. **Validaciones en Servicios (Lógica de Negocio)**

#### **ProductService**
- ✅ Verifica que el ID sea positivo
- ✅ Valida que el nombre no esté vacío
- ✅ Valida que el precio sea positivo y mayor a 0

#### **InvoiceServices**
- ✅ Verifica que el ID sea positivo
- ✅ Valida campos obligatorios (clientId, clientName)
- ✅ Valida que los montos no sean negativos
- ✅ **Validación de coherencia**: Verifica que totalAfterTaxes = totalBeforeTaxes + taxes

#### **InvoiceDetailService**
- ✅ Verifica que el ID sea positivo
- ✅ Valida que el producto no sea null
- ✅ Valida que la factura no sea null
- ✅ Valida que el precio total no sea negativo

### 7. **Controladores con @Valid**

Todos los controladores ahora usan `@Valid` para activar validaciones automáticas:

```kotlin
@PostMapping
fun save(@Valid @RequestBody product: Product): ResponseEntity<Product>

@PutMapping(value = ["/{id}"])
fun update(@PathVariable id: Long, @Valid @RequestBody invoice: Invoice): ResponseEntity<Invoice>
```

---

## 📊 Estructura de Archivos

```
src/main/kotlin/com/puce/invoices/
├── controllers/
│   ├── ProductController.kt ✅ (con @Valid)
│   ├── InvoiceController.kt ✅ (con @Valid)
│   └── InvoiceDetailController.kt ✅ (con @Valid)
├── services/
│   ├── ProductService.kt ✅ (validaciones de negocio)
│   ├── InvoiceServices.kt ✅ (validaciones de negocio)
│   └── InvoiceDetailService.kt ✅ (validaciones de negocio)
├── models/entities/
│   ├── Product.kt ✅ (Bean Validation)
│   ├── Invoice.kt ✅ (Bean Validation)
│   └── InvoiceDetails.kt ✅ (Bean Validation)
├── exceptions/
│   ├── GlobalExceptionHandler.kt ✅ (manejo completo)
│   ├── ErrorResponse.kt ✅
│   ├── ValidationErrorResponse.kt ✅
│   └── exceptions/
│       ├── BadRequestException.kt ✅
│       ├── ResourceNotFoundException.kt ✅
│       └── InvoiceEntityNotFoundException.kt ✅
└── repositories/
    ├── ProductRepository.kt
    ├── InvoiceRepository.kt
    └── InvoiceDetailRepository.kt
```

---

## 🧪 Ejemplos de Uso

### Error 404 - Not Found
```bash
GET /invoices/products/999

Response: 404 Not Found
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 404,
  "error": "Not Found",
  "message": "Producto con id 999 no encontrado",
  "path": "/invoices/products/999"
}
```

### Error 400 - Validación de Campos
```bash
POST /invoices/products
{
  "name": "",
  "price": -10
}

Response: 400 Bad Request
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

### Error 400 - Validación de Negocio
```bash
POST /invoices
{
  "clientId": "CLI-001",
  "clientName": "Juan Pérez",
  "totalBeforeTaxes": 1000.00,
  "taxes": 120.00,
  "totalAfterTaxes": 1500.00
}

Response: 400 Bad Request
{
  "timestamp": "2024-01-20T10:30:45.123",
  "status": 400,
  "error": "Bad Request",
  "message": "El total después de impuestos no coincide con la suma del subtotal más impuestos",
  "path": "/invoices"
}
```

---

## ✨ Características Principales

1. **Respuestas HTTP Estructuradas**: Todas las respuestas de error siguen un formato consistente
2. **Mensajes Claros en Español**: Fácil comprensión para el usuario final
3. **Validación en Múltiples Capas**:
   - Bean Validation (entidades) - automática
   - Validaciones de negocio (servicios) - lógica personalizada
4. **Manejo Centralizado**: GlobalExceptionHandler captura todas las excepciones
5. **Información Detallada**: Cada error incluye timestamp, status, tipo, mensaje y path
6. **Detalles de Campos**: Los errores de validación muestran qué campo falló y por qué
7. **Seguridad**: Se validan todos los datos antes de procesarlos

---

## 📚 Documentación Adicional

Ver **`VALIDATIONS_GUIDE.md`** para:
- Documentación completa de todas las validaciones
- Ejemplos detallados de peticiones y respuestas
- Casos de uso específicos
- Lista completa de endpoints

---

## 🚀 Cómo Probar

1. **Compilar el proyecto**:
```bash
./gradlew build
```

2. **Ejecutar la aplicación**:
```bash
./gradlew bootRun
```

3. **Probar los endpoints** (usar Postman, curl, o cualquier cliente HTTP):
```bash
# Crear producto válido
curl -X POST http://localhost:8080/invoices/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":1299.99}'

# Crear producto inválido (error 400)
curl -X POST http://localhost:8080/invoices/products \
  -H "Content-Type: application/json" \
  -d '{"name":"","price":-10}'

# Buscar producto inexistente (error 404)
curl -X GET http://localhost:8080/invoices/products/999
```

---

## ✅ Estado de Implementación

- ✅ Dependencias agregadas
- ✅ Clases de respuesta de error creadas
- ✅ Excepciones personalizadas implementadas
- ✅ GlobalExceptionHandler completo
- ✅ Validaciones Bean Validation en entidades
- ✅ Validaciones de negocio en servicios
- ✅ @Valid agregado a controladores
- ✅ Documentación completa
- ✅ Sin errores de compilación
- ✅ Listo para producción

---

## 🎓 Conceptos Implementados

- **Bean Validation (JSR-380)**: Validaciones declarativas con anotaciones
- **Exception Handling**: Manejo global con @RestControllerAdvice
- **Custom Exceptions**: Excepciones personalizadas para casos específicos
- **DTO Pattern**: Respuestas estructuradas con DTOs
- **Clean Code**: Código organizado y bien documentado
- **Best Practices**: Siguiendo las mejores prácticas de Spring Boot

---

**¡El sistema de validaciones está completo y listo para usar!** 🎉

