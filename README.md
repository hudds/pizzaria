# pizzaria
Um site de uma pizzaria fictícia que recebe e organiza pedidos. Projeto feito apenas para praticar.
O objetivo do sistema é coletar os pedidos dos clientes e exibi-los em uma tela para que os funcionários da pizzaria possam prepará-los.
O sistema também fornece ferramentas administrativas para cadastrar e editar pizzas, sabores e bebidas e gerenciar usuários cadastrados.

# Tecnologias usadas no projeto
A aplicação foi feita em Java 8 com o framework Spring Boot 2.0. Para a camada de persistência está sendo usado o Hibernate. As views são feitas com JSP, JavaScript e Bootstrap 4.

# Configurações de Persistência
As configurações de acesso ao banco de dados devem estar em um arquivo chamado `local-dev-environment.properties` que **precisa ser criado na pasta** `pizzaria/src/main/resources/`

O arquivo `local-dev-environment.properties` deve conter o seguinte conteúdo:

```
database.username=INSIRA O NOME DE USUÁRIO AQUI
database.password=INSIRA A SENHA AQUI
database.url=INSIRA A URL AQUI
```

O Hibernate está configurado para apenas validar as tabelas do banco de dados, ou seja, ele não cria tabelas automaticamente. Para mudar isso, vá no arquivo `src/main/resources/application.properties` e altere a propiedade `spring.jpa.hibernate.ddl-auto=validate` para `spring.jpa.hibernate.ddl-auto=update`.

O projeto está configurado para rodar com o banco de dados MySQL, para usar outro banco, é necessário especificar o driver correspondente na classe `/src/main/java/br/com/pizzaria/config/JpaConfig.java`, além de adicionar a nova dependência no arquivo pom.xml.

O pool de conexões utilizado na aplicação é o Hikari.
As configuraçes do pool de conexões e do DataSource estão localizadas na classe `/src/main/java/br/com/pizzaria/config/JpaConfig.java`. 

# Configurações do banco de dados
**Para ter acesso a todas as funcionalidades da aplicação é necessário ter um usuário administrador.**

O script SQL abaixo cria a base de dados, as tabelas e o usuário administrador. 

O nome de usuário é "admin" e a senha é "admin", caso queira mudar a senha, saiba que ela deve ser hasheada com a criptografia BCrypt, que é a criptografia que a aplicação utiliza para as senhas. **Também é possível mudar a senha dentro da própria aplicação** acesando o menu "Minha Conta" após fazer login.

[Script sql (MySQL) para a criação das tabelas do banco de dados e inserção do usuário admin.](https://gist.github.com/hudds/30d72c237598e5b7be097af21d5ee17d)

Caso queira popular a base de dados com exemplos, rode [este script sql (MySQL).](https://gist.github.com/hudds/db0cd12f7a0297000ce0ff9b231a19d0)

# Rodando a aplicação
Basta rodar a classe ``src/main/java/br/com/pizzaria/PizzariaApplication.java`` e em seguida acessar localhost:8080.
