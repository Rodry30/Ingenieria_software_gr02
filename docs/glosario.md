[Inicio](/) > Glosario

# Glosario Técnico y de Negocio de FoodGest

Este glosario unifica las definiciones de los términos técnicos, metodologías y conceptos de negocio del dominio agrícola peruano empleados en el ecosistema **FoodGest**.

---

## A
*   **ADR (Architecture Decision Record):** Documento técnico ágil e inmutable que registra una decisión de diseño de arquitectura relevante adoptada por el equipo, detallando su contexto, causas, justificación y consecuencias asociadas.
*   **Agricultor verificado:** Estatus de seguridad otorgado por administradores de FoodGest a productores agrícolas después de confirmar la existencia real de su parcela (chacra), identidad legal y ficha RUC ante la SUNAT.

---

## C
*   **Calificación:** Evaluación numérica (estrellas) y reseña escrita que se registran compradores y agricultores mutuamente después de finalizar un pedido para construir una puntuación de confianza y reputación en el sistema.
*   **Conventional Commits:** Especificación estandarizada e imperativa para formatear los mensajes de commit de Git, permitiendo legibilidad inmediata y generación automática de notas de lanzamiento.
*   **CoSQ (Cost of Software Quality):** Métrica de gestión de calidad que calcula el costo incurrido en prevenir, evaluar y corregir fallos en el software antes y después de su despliegue comercial.

---

## D
*   **DDD Lite (Domain-Driven Design Lite):** Enfoque táctico de diseño de software adoptado en FoodGest que agrupa clases e infraestructura técnica en paquetes autocontenidos según dominios y límites de negocio bien diferenciados, evitando dependencias cruzadas complejas.
*   **DoD (Definition of Done):** Lista formal y obligatoria de criterios de calidad y verificación que una tarea técnica debe cumplir estrictamente antes de considerarse finalizada en el sprint.
*   **DoR (Definition of Ready):** Lista de requisitos mínimos de claridad, diseño y alcance técnico que una historia de usuario debe satisfacer para poder ser seleccionada en la planeación del sprint.

---

## E
*   **EMMSA (Empresa Municipal de Mercados S.A.):** Entidad administradora del Gran Mercado Mayorista de Lima (MPSA) en Santa Anita, cuyos registros históricos de ingreso de toneladas y variabilidad de precios se toman como base para el cálculo del precio promedio del mercado de referencia.
*   **Escrow (Fondo en Garantía):** Mecanismo de seguridad transaccional digital en el cual el capital de una compra permanece temporalmente retenido y custodiado de forma neutral por la plataforma de pagos de FoodGest, liberándolo al agricultor únicamente cuando el comprador confirme la entrega física de la mercancía.

---

## G
*   **GEOGRAPHY (Tipo de dato):** Tipo de datos espacial nativo provisto por PostGIS para representar coordenadas geográficas en elipses terrestres utilizando grados decimales y permitiendo cálculos métricos reales de distancias sin distorsiones por proyecciones cartográficas.
*   **Git Flow:** Marco de trabajo y estrategia ordenada para gestionar ramas en repositorios de Git a través de flujos claros para desarrollos, releases y parches críticos en producción.
*   **Guía de Remisión:** Documento emitido de forma legal y obligatoria por el remitente o transportista para sustentar el traslado físico terrestre de productos agrícolas en el Perú.

---

## J
*   **JWT (JSON Web Token):** Estándar de seguridad de la industria informática (RFC 7519) para transmitir identidades y claims de usuarios encriptados y firmados digitalmente, permitiendo una autenticación stateless.

---

## K
*   **KLOC (Thousands of Lines of Code):** Métrica clásica de tamaño de software que indica el total de miles de líneas de código fuente escritas en el proyecto, sirviendo de base para análisis estadísticos de productividad.

---

## M
*   **MPSA (Gran Mercado Mayorista de Lima):** Centro logístico de abasto agrícola mayorista principal del Perú que rige las tendencias y fluctuaciones diarias de precios de cosechas del país.
*   **MTTF (Mean Time To Failure):** Métrica de confiabilidad que calcula el tiempo medio esperado de funcionamiento de la aplicación antes de que ocurra una falla no controlada o caída del sistema.

---

## N
*   **Negociación:** Interfaz virtual bilateral donde un comprador propone una contraoferta (cambios en el precio sugerido o cantidad de toneladas a comprar) a un agricultor, y este último puede aceptar, rechazar o formular una respuesta.

---

## O
*   **Oferta:** Publicación activa de venta de un lote de cosecha agrícola realizada por un agricultor en el mapa de FoodGest, detallando el precio unitario base, el stock disponible actual, la ubicación espacial de despacho y las opciones de descuento por volumen.

---

## P
*   **Parcela / Chacra:** Terreno agrícola físico rural propiedad de un agricultor destinado al cultivo y cosecha de alimentos. Sus coordenadas geográficas exactas están registradas en el perfil especializado.
*   **PostGIS:** Extensión geoespacial de código abierto líder de la industria que convierte a PostgreSQL en una base de datos analítica capaz de indexar y resolver de forma veloz consultas geométricas complejas.
*   **Precio escalonado:** Esquema dinámico de descuentos aplicado sobre una oferta agrícola donde el costo unitario por kilogramo de cosecha disminuye proporcionalmente a medida que el comprador incrementa el volumen de compra.
*   **Precio de mercado:** Precio promedio diario sugerido de referencia de un determinado producto agrícola en el mercado peruano, estimado en base al consolidado histórico del MPSA para guiar a los agricultores a tasar de forma justa sus ofertas.

---

## S
*   **Saldo disponible:** Capital real líquido y disponible en la billetera de un agricultor que puede ser retirado de inmediato hacia su cuenta bancaria.
*   **Saldo retenido:** Dinero retenido en garantía temporal (escrow) dentro de la billetera de un agricultor, correspondiente a pedidos pagados por compradores que aún se encuentran en tránsito de despacho o en espera de conformidad física.
*   **SonarQube Quality Gate:** Conjunto de umbrales obligatorios de calidad estática definidos en la tubería CI/CD (ej. menos de 3% de duplicación, 0 fallos de seguridad críticos y más de 80% de cobertura) que bloquean o aprueban la subida de Pull Requests.
*   **Sprint:** Iteración de desarrollo ágil de marco Scrum de duración fija (en FoodGest establecida en 2 semanas) durante la cual el equipo produce un incremento de software utilizable y probado.

---

## T
*   **Tracking:** Proceso de rastreo continuo y geolocalizado en tiempo real del despacho terrestre de un pedido desde la parcela hasta el almacén del comprador, transmitido dinámicamente mediante WebSockets por el GPS del dispositivo móvil del transportista.
*   **Trato acordado:** Trámite comercial finalizado y aprobado por ambas partes (comprador y agricultor) mediante una negociación exitosa, cerrando la exclusividad de compra del lote.

---

## W
*   **Wallet (Monedero Digital):** Entidad virtual asociada a cada usuario que registra el saldo disponible, el saldo retenido en escrow y el historial de transacciones monetarias dentro del sistema.
*   **Work Product:** Cualquier entregable o componente de software materializable y medible (ej. una clase Java, un script de base de datos, o documentación de arquitectura) producido en el transcurso del desarrollo del proyecto.

---

## Ver también

- [Catálogo de Dominios de FoodGest](/arquitectura/dominios.md)
- [Stack Tecnológico del Proyecto](/arquitectura/stack-tecnologico.md)
- [ADR-01: Selección de PostgreSQL + PostGIS](/arquitectura/adr-01-postgresql-postgis.md)
