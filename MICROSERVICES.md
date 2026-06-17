# MICROSERVICES

Uma única unidade de implantação da aplicação (ex.: JAR, DLL, ...), chamamos de monolito (monólito, EN monolith) -- peça única.

2GiB memory, 1vCPU, ...
container #1 app.jar
2GiB memory, 1vCPU, ...
container #2 app.jar
2GiB memory, 1vCPU, ...
container #3 app.jar

Ainda é um monolito? SIM

Em oposição a um monolito? Separar em várias peças.

Microsserviço (microservice)

512MiB memory, 0.5vCPU, ...
container #1 app-user.jar (controller user, domain, entities, etc) - Java/Spring PostgreSQL
4GiB memory, 2vCPU, ...
container #2 app-pedidos.jar (pedido controller, domain, ...) - C#.NET/WebAPI SQLite
X container #3 app-fretes.jar (frete ...) - Node/Express Oracle

container #4 app-user.jar (controller user, domain, entities, etc)
container #5 app-pedidos.jar (pedido controller, domain, ...)
X container #6 app-fretes.jar (frete ...)

recomendation-api haskell

"máquinas separadas"
10.0.0.3     10.0.0.5
serviço-A    serviço-B
controller   
userService  ---->  controller -> notificationService
          pipe/db/messaging(rabbit-mq,kafka,etc)/http

insert into notificacao blabla       select from notificacao where processada IS NULL
                                     update notificacao set processada = true where ...

serviços
A -> B
B -> C
C -> F
D
E
F -> A
...


Começar com monolito e destacar microsserviços se e quando necessário.

Padrão para extrair um microsserviço de um monolito: Strangler Fig 
https://microservices.io/patterns/refactoring/strangler-application.html

Performance: Go lang, Rust, ...

