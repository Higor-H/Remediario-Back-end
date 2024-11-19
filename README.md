Projeto Spring - Sistema de Gestão de Perfis e Medicamento
Este projeto é uma API REST desenvolvida com Spring Framework para gerenciar usuários, perfis e medicamentos vinculados.

Pré-requisitos
Antes de executar o sistema, certifique-se de que os seguintes softwares estejam instalados:

Java JDK 17 ou superior
Maven (para gerenciamento de dependências)
Banco de Dados PostgreSQL (ou outro banco configurado)
Postman ou outra ferramenta para testar APIs (opcional)
Git (opcional, para clonar o repositório)

Passo a Passo de Instalação

Clone o repositório:
git clone <URL_DO_REPOSITÓRIO>  
cd <PASTA_DO_PROJETO> 

Compile o projeto com Maven:
mvn clean install 

Configure o arquivo application.properties para o banco de dados:
Localize o arquivo na pasta src/main/resources.
Substitua os valores padrão pelas credenciais do seu banco de dados.

spring.datasource.url=jdbc:postgresql://localhost:5432/<NOME_DO_BANCO>  
spring.datasource.username=<USUARIO_DO_BANCO>  
spring.datasource.password=<SENHA_DO_BANCO>  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  

Execute o projeto:
mvn spring-boot:run  
Acesse a aplicação:

URL padrão: http://localhost:8080
Configuração de Ambiente
Variáveis de Ambiente:

JWT_SECRET: Chave secreta para geração de tokens JWT.
DATABASE_URL: URL do banco de dados, caso utilize um serviço externo.

Exemplo (Linux/Mac):
export JWT_SECRET=MinhaChaveSecreta123  
export DATABASE_URL=jdbc:postgresql://localhost:5432/<NOME_DO_BANCO>  

Banco de Dados:
Crie o banco com o nome especificado no application.properties.
Execute as migrações automaticamente configuradas pelo Spring Data.

Lista de Endpoints
Autenticação
Método	Endpoint	Descrição	Parâmetros de Entrada	Resposta Esperada
POST	/auth/register	Registro de usuário	{ "email": "user@email.com", "password": "senha123", "name": "Usuário" }	201 Created com informações do usuário.
POST	/auth/login	Login do usuário	{ "email": "user@email.com", "password": "senha123" }	200 OK com token JWT.

Perfis
Método	Endpoint	Descrição	Parâmetros de Entrada	Resposta Esperada
GET	/profiles	Lista todos os perfis do usuário	Header: Authorization: Bearer <TOKEN>	Lista de perfis vinculados ao usuário.
POST	/profiles	Cria um novo perfil	{ "name": "Perfil 1" }	Perfil criado.

Medicamentos
Método	Endpoint	Descrição	Parâmetros de Entrada	Resposta Esperada
GET	/medicamento/list	Lista medicamentos de um perfil	Header: Authorization: Bearer <TOKEN> + profileId	Lista de medicamentos do perfil.
POST	/medicamento/create	Adiciona um novo medicamento	{ "name": "Paracetamol", "quantity": 10 }	Medicamento criado.

Autenticação
O sistema utiliza autenticação baseada em tokens JWT.

Como autenticar:
Faça login utilizando o endpoint /auth/login com e-mail e senha.
No retorno, copie o token JWT.
Insira o token no cabeçalho das requisições seguintes:
Authorization: Bearer <TOKEN>  

Exemplos de Uso
Registro de Usuário (cURL)
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{"email": "user@email.com", "password": "Senha@123", "name": "Usuário"}' 

Login de Usuário (cURL)
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"email": "user@email.com", "password": "Senha@123"}' 

Listagem de Perfis (cURL)
curl -X GET http://localhost:8080/profiles -H "Authorization: Bearer <TOKEN>" 

Adicionar Medicamento (cURL)
curl -X POST http://localhost:8080/medicines -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" -d '{"name": "Paracetamol", "quantity": 10}'  
