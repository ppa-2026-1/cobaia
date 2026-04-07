# COBAIA

## DESCRIPTION

Cobaia Toy Project para experimentar princípios e padrões arquiteturais

## REQUIREMENTS

Java 21
Gradle 8.13

## Programação Imperativa

Ordens, passo-a-passo, fluxo definido.

Linguagens: Java, C#, PHP, Python, Ruby, C, JavaScript, ...

## Programação Declarativa

"Solicitação", ex.:

```plain
SELECIONAR nome, idade
DE alunos
ONDE idade >= 18
ORDENANDO POR nome
```

SQL
Structured
QuerySELECIONAR nome, idade
DE alunos
ONDE idade >= 18
ORDENANDO POR nome
Language

É uma linguagem declarativa.

Como as linguagens implementam a programação declarativa?

Metaprogramação: programas que criam programas.
Reflection (reflexão), envolve algum tipo de metadata, ex.: annotation, decorator, attribute.

```csharp
class A {
    [Alguma]
    void metodo() {

    }
}
```

```java
@Controller // annotation
class UsuarioController {

}
```

```python
def decorador():
    pass

@decorador
def f():
    pass