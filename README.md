# COBAIA

## DESCRIPTION

Cobaia Toy Project para experimentar princípios e padrões arquiteturais

## REQUIREMENTS

Java 21
Gradle 8.13

## Persistence (Persistência)

Dado ou informação persistente (o oposto seria _"Transient"_ (transitório)) -- transiente quer dizer geralmente "em memória" (volátil -- cache CPU, RAM, ...).

Data
Access
Object

Ex.: Linguagem/Plataforma => UsuarioDAO, salvar, buscar, excluir, ... => DB, Arquivo, ..., RDBMS (SGBD, ex.: PostgreSQL).

POO: Programação Orientada a Objetos
- classes
- atributos
- métodos
- herança
- objetos/instâncias
- agregação
- composição

Linguagem Persistente (SQL)
- tabela 
- coluna (campo)
- linha ou registros
- relacionamentos
- chave primária e estrangeira

```java
class Aluno {
    Long matricula;
    String nome;
    Curso curso;
}

var a = new Aluno(111222, "Marcio Torres")
```

```SQL
CREATE TABLE aluno (
    INTEGER      matricula,
    VARCHAR(100) nome
);

INSERT INTO aluno (matricula, nome)
VALUES (111222, 'Marcio Torres');
```

Object/Relational Impedance Mismatch
> Diferença de representação no modelo de objetos e no modelo relacional.

```java
class AlunoDAO {
    // persistência nativa (sem "esconder" o SQL)
    Aluno buscar(Long matricula) {
        var con = openConnection();
        var st = con.prepareStatement("SELECT * FROM alunos WHERE matricula = ?");
        st.setLong(1, matricula);
        var result = st.executeQuery();
        var aluno = new Aluno();
        while (result.hasNext()) {
            aluno.setNome(result.getString("nome"));
            ...
        }
        return aluno;
    }
}
```

É solucionado com algum O/RM => Object/Relational Mapping:
    esconder a complexidade do mapeamento entre objeto e relacional.

Padrões mais usados para O/RM:

- DAO (padrão legado de persistência)
- Active Record (registro ativo);
- **Repository** (repositório)

## Java

Java
Persistence
API

Baseado em um projeto Hibernate (NHibernate) -- autor Gavin King (Software Engineer) -- originalmente baseado em XML, mas que hoje é declarativo (baseado em metadados).

Neste projeto, já usamos O/RM, o JPA estendido pelo Spring Data JPA.

O SQL é escrito pela _"engine"_ do O/RM.

## Considerações

- Usar O/RM? Sim, geralmente. Não, quando a performance for crítica;
- Evitar mapeamentos bi-direcionais, sempre que possível;
- EAGER ou LAZY? Depende da necessidade dos dependentes (objetos filho), se são sempre necesários: EAGER (antecipado), o padrão é LAZY (tardio). É possível escrever métodos find nos repositórios que façam o carregamento antecipado (EAGER)

// produto cartesiano do banco (_download do banco inteiro_)
A > B > C > D > E

Questões em aberto:

- O findAll está buscando todos os registros!!! Não deveria! E se houvesse 1.000.000 de relatórios? Solução: PAGINAR! (**NÃO SE FAZ QUERIES SEM LIMIT**);
- Herança para isolar o que é comum para todas as entidades (ex.: id, createdAt, updatedAt, ...);
- Retornar DTO em vez da Entidade no Controller;