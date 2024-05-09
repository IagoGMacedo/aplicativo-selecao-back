# Aplicativo Seleção
Teste técnico de aplicativo de Seleção para a Esig Group.

# O que foi feito
###### CRUD de tarefas na aplicação em REST
É possível criar, deletar, atualizar e remover tarefas pela aplicação, os endpoints utilizados foram feitos em REST.
###### CRUD de usuários (somente endpoints) em REST
É possível criar, deletar, atualizar e remover usuários pela aplicação, os endpoints utilizados foram feitos em REST. Somente Usuários com cargo ADMINISTRADOR podem fazer operações envolvendo usuário.

###### Controle de autenticação por usuário com login e senha
Diferentes usuários podem ser criados para interagir com a aplicação. É possível logar e interagir com as tarefas.
###### Autenticação e permissão de endpoints com JWT
Existem dois perfis de usuário: Administrador e Comum. Operações com tarefas são permitidas para ambos os usuários. Operações com outros Usuários só são permitidas para administradores.

 ###### Persistência com JPA e Hibernate
###### Estilização com Bootstrap

# Como executar os ambientes
O projeto divide-se em um back-end feito com Spring boot (este repositório), e um front-end feito com Angular, disponível no repositório https://github.com/IagoGMacedo/aplicativo-selecao-front.
Para inicializar o back-end, abra o projeto no Intelij e execute a aplicação spring, o programa executará por padrão na porta [http://localhost:8080/](http://localhost:8080/). Para inicializar o front-end, abra o projeto no vscode, execute primeiro o comando "npm install" para baixar as dependências da aplicação, e depois e digite "ng serve", o programa executará por padrão na porta [http://localhost:4200/](http://localhost:4200/).
