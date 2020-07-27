# pizzaria
**O projeto ainda está sendo desenvolvido.**
Um site de uma pizzaria fictícia que recebe e organiza pedidos. Projeto feito apenas para praticar.
O objetivo do sistema é coletar os pedidos dos clientes e exibi-los em uma tela para que os funcionários na pizzaria possam prepará-los.
O sistema também irá fornecer ferramentas administrativas para cadastrar e editar pizzas, sabores e bebidas.

# Tecnologias usadas no projeto
A aplicação está sendo feita em Java 8 com o framework Spring Boot 2.0. Para a camada de persistência está sendo utilizado o Hibernate com o banco de dados MySQL. As views são feitas com JSP, JSTL, JavaScript e Bootstrap 4.

# Configurações de Persistência
O pool de conexões utilizado na aplicação é o Hikari.
As configuraçes do pool de conexões e do DataSource estão localizadas na classe ``/src/main/java/br/com/pizzaria/config/JpaConfig.java``. Insira o nome de usuário e a senha do seu banco de dados MySQL nos métodos ``hikariConfig.setUsername("INSIRA O NOME DE USUÁRIO AQUI")`` e ``hikariConfig.setPassword("INSIRA A SENHA AQUI")``, como demonstrado abaixo:

```
@Configuration
public class JpaConfig {
	
	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		HikariConfig hikariConfig = new HikariConfig();
		
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setUsername("INSIRA O NOME DE USUÁRIO AQUI");
		hikariConfig.setPassword("INSIRA A SENHA AQUI");
		hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/pizzaria");
		hikariConfig.setMinimumIdle(10);
		hikariConfig.setMaximumPoolSize(20);
		hikariConfig.setMaxLifetime(1200000);
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	}
	
}
```


O Hibernate está configurado para apenas validar as tabelas do banco de dados, ou seja, ele não cria tabelas automaticamente. Para mudar isso, vá no arquivo ``src/main/resources/application.properties`` e altere a propiedade ``spring.jpa.hibernate.ddl-auto=validate`` para ``spring.jpa.hibernate.ddl-auto=update``.

# Configurações do banco de dados
Para ter acesso a todas as funcionalidades da aplicação é necessário ter um usuário administrador.

O script SQL abaixo cria a base de dados, as tabelas e o usuário administrador. 

O nome de usuário é "admin" e a senha é "admin", caso queira mudar a senha, saiba que ela deve ser encriptada com a criptografia BCcrypt, que é a criptografia que a aplicação utiliza para as senhas.

```
START TRANSACTION;
drop database if exists pizzaria ;
create database pizzaria;
use pizzaria;
create table TB_ROLES (AUTHORITY varchar(20) primary key);

create table TB_ENDERECOS ( 
	ID int primary key auto_increment,
    ESTADO varchar(2),
    CIDADE varchar(60),
    BAIRRO varchar(60),
    CEP varchar(10),
    NUMERO int,
    LOGRADOURO varchar(100),
    COMPLEMENTO varchar(100)
);

create table TB_USUARIOS (
	ID int primary key auto_increment, 
    EMAIL varchar(100) not null unique, 
    NOME_DE_USUARIO varchar(30) not null unique,
    SENHA varchar(300) not null,
    NOME varchar(100),
    ENDERECO_ID int,
    foreign key (ENDERECO_ID) references TB_ENDERECOS(ID)
);    

create table TB_SABORES (
	ID int primary key auto_increment,
    TITULO varchar(70),
    DESCRICAO varchar(300),
    TIPO varchar(20)
);

create table TB_PIZZAS (
	ID int primary key auto_increment,
    TITULO varchar(100),
    DESCRICAO varchar(200),
    PRECO decimal(19,4),
    TIPO_SABOR varchar(20)
);

create table TB_PEDIDOS_DE_PIZZAS (
	ID int primary key auto_increment,
    PIZZA_ID int,
    QUANTIDADE int,
    VALOR decimal(19,4),
    FOREIGN KEY (PIZZA_ID) REFERENCES TB_PIZZAS(ID)
);

create table TB_PEDIDOS_DE_PIZZAS_SABORES(
	PEDIDO_ID int,
    SABOR_ID int,
    foreign key (PEDIDO_ID) REFERENCES TB_PEDIDOS_DE_PIZZAS(ID),
    foreign key (SABOR_ID) REFERENCES TB_SABORES(ID)
);

create table TB_BEBIDAS(
	ID int primary key auto_increment,
    TITULO varchar(100),
    DESCRICAO varchar(200),
    VALOR decimal(19,4)
);

create table TB_PEDIDOS_DE_BEBIDAS (
	ID int primary key auto_increment,
    BEBIDA_ID int,
    QUANTIDADE int,
    VALOR decimal(19,4),
    FOREIGN KEY (BEBIDA_ID) REFERENCES TB_BEBIDAS(ID)
);

create table TB_PEDIDOS (
	ID int primary key auto_increment,
    CLIENTE_ID int,
    foreign key(CLIENTE_ID) references TB_USUARIOS(ID),
    ESTAGIO_DO_PEDIDO varchar(50),
    HORARIO_DO_PEDIDO DATETIME
);

create table TB_PEDIDO_PEDIDOS_DE_PIZZAS (
	PEDIDO_ID int,
    PIZZA_ID int,
    foreign key(PEDIDO_ID) references TB_PEDIDOS(ID),
    foreign key(PIZZA_ID) references TB_PEDIDOS_DE_PIZZAS(ID)
);

create table TB_PEDIDO_PEDIDOS_DE_BEBIDAS (
	PEDIDO_ID int,
    BEBIDA_ID int,
    foreign key(PEDIDO_ID) references TB_PEDIDOS(ID),
    foreign key(BEBIDA_ID) references TB_PEDIDOS_DE_BEBIDAS(ID)
);

create table TB_USUARIOS_ROLES(
	USUARIO_ID int,
    AUTHORITY varchar(20), 
	FOREIGN KEY (USUARIO_ID) REFERENCES TB_USUARIOS(ID),
    FOREIGN KEY (AUTHORITY) REFERENCES TB_ROLES(AUTHORITY)
);

create table TB_USUARIO_PEDIDOS(
	USUARIO_ID int,
    PEDIDO_ID int, 
	FOREIGN KEY (USUARIO_ID) REFERENCES TB_USUARIOS(ID),
    FOREIGN KEY (PEDIDO_ID) REFERENCES TB_PEDIDOS(ID)
);

insert into TB_ROLES values ('ROLE_ADMIN');
insert into TB_ROLES values ('ROLE_CLIENTE');
insert into TB_USUARIOS (ID, EMAIL, NOME_DE_USUARIO, SENHA) values (0,'admin_pizzaria@gmail.com', 'admin', '$2y$12$SLOi8sJp4rvcO6NXyWQltOa4zFOa29PkY.sl1PUGLEGp8RZvHolXG');
insert into TB_USUARIOS_ROLES (USUARIO_ID, AUTHORITY) values (1, 'ROLE_ADMIN');


COMMIT;
```
