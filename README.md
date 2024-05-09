# Aplicativo Seleção
Teste técnico de aplicativo de Seleção para a Esig Group.

# O que foi feito
###### a) Criar uma aplicação Front-end utilizando Angular na versão mais recente.
foi utilizado Angular 17, Java 17 e Spring 3.2.5.
###### b) Desenvolver o backend utilizando Java e Spring Boot com persistência no banco de dados Postegres
###### c) Os endpoints devem ser em REST
###### d) Criar um controle de autenticação por usuário com login e senha e os endpoints devem autenticar através de token JWT.
###### e) Utilizar JPA e Hibernate.
###### g) Documentar a API usando o Swagger.

###### Estilização com bootstrap.

# Como executar os ambientes
O projeto divide-se em um back-end feito com Spring boot (este repositório), e um front-end feito com Angular, disponível no repositório https://github.com/IagoGMacedo/aplicativo-selecao-front.
Para inicializar o back-end, abra o projeto no Intelij e execute a aplicação spring, o programa executará por padrão na porta [http://localhost:8080/](http://localhost:8080/). Para inicializar o front-end, abra o projeto no vscode, execute primeiro o comando "npm install" para baixar as dependências da aplicação, e depois e digite "ng serve", o programa executará por padrão na porta [http://localhost:4200/](http://localhost:4200/).


# Testando

É possível usar os usuários mockados para acessar a aplicação e interagir com os endpoints:

**Usuário Admin**
login: admin
senha: 123

**Usuário Testador 1**
login: testador1
senha:123

Usuário Testador 2
login: testador2
senha:123

Para cadastrar mais usuários, é possível usar o endpoint "Registrar usuário". Os endpoints estão no arquivo "endpoints" na raiz do projeto e também documentados no link http://localhost:8080/swagger-ui/index.html (aplicação precisa estar aberta)
