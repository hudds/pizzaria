# pizzaria
**O projeto ainda está sendo desenvolvido.**
Um site de uma pizzaria fictícia que recebe e organiza pedidos. Projeto feito apenas para praticar.
O objetivo do sistema é coletar os pedidos dos clientes e exibi-los em uma tela para que os funcionários na pizzaria possam prepará-los.
O sistema também irá fornecer ferramentas administrativas para cadastrar e editar pizzas, sabores e bebidas.

# Tecnologias usadas no projeto
A aplicação está sendo feita em Java 8 com o framework Spring Boot 2.0. Para a camada de persistência está sendo utilizado o Hibernate com o banco de dados MySQL. As views são feitas com JSP, JSTL, JavaScript e Bootstrap 4.

# Configurações de Persistência
O pool de conexões utilizado na aplicação é o Hikari.
As configuraçes do pool de conexões e do DataSource estão localizadas na classe ``/src/main/java/br/com/pizzaria/config/JpaConfig.java``

O Hibernate está configurado para apenas validar as tabelas do banco de dados, ou seja, ele não cria tabelas automaticamente. Para mudar isso, vá no arquivo ``src/main/resources/application.properties``` e altere a propiedade ```spring.jpa.hibernate.ddl-auto=validate`` para ``spring.jpa.hibernate.ddl-auto=update``.
