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
Banco de Dados:
Crie o banco com o nome especificado no application.properties.
Execute as migrações automaticamente configuradas pelo Spring Data.

Lista de Endpoints:
Autenticação
Método	Endpoint	Descrição	Parâmetros de Entrada	Resposta Esperada
POST	/auth/register	Registro de usuário
{
  "email": "user@email.com", 
  "password": "senha123", 
  "name": "Usuário" 
}	
201 Created com informações do usuário.
POST	/auth/login	Login do usuário
{
  "email": "user@email.com", 
  "password": "senha123"
}
200 OK com token JWT.

Perfis
Método	Endpoint	Descrição	Parâmetros de Entrada	Resposta Esperada
GET	/profiles/list	Lista todos os perfis do usuário
Header: Authorization: Bearer <TOKEN>	
Lista de perfis vinculados ao usuário.

Medicamentos
Método	Endpoint	Descrição	Parâmetros de Entrada	Resposta Esperada
GET	/medicamento/list	Lista medicamentos de um perfil	Header: Authorization: Bearer <TOKEN> + profileId	Lista de medicamentos do perfil.
POST	/medicamento/create	Adiciona um novo medicamento	
{
  "nome": "Paracetamol", 
  "quantidade": 10,
  "dosagem": "10mg",
  "tipo": "comprimido"
}	
Medicamento criado.

Autenticação
O sistema utiliza autenticação baseada em tokens JWT.

Como autenticar:
Faça login utilizando o endpoint /auth/login com e-mail e senha.
No retorno, copie o token JWT.
Insira o token no cabeçalho das requisições seguintes:
Authorization: Bearer <TOKEN>  

Exemplos de Uso:
Registro de Usuário (cURL)
POST http://localhost:8080/auth/register
 method: 'POST',
        headers: 
          {
            'Content-Type': 'application/json',
          },
        body:
          {
            "email": "user@email.com", 
            "password": "Senha@123", 
            "name": "Usuário"
          } 

Login de Usuário (cURL)
POST http://localhost:8080/auth/login
method: 'POST',
        headers: 
          {
            'Content-Type': 'application/json',
          },
        body:
          {
            email: name,
            password: password,
          }

Listagem de Perfis (cURL)
GET http://localhost:8080/profiles/select
 method: 'GET',
          headers: 
            {
              'Content-Type': 'application/json',
              Authorization: 'Bearer ${token}`, // Passa o token no header
            }

Selecionar um Perfil (cURL)
GET http://localhost:8080/profiles/select/{perfilId}
method: 'POST',
        headers: 
          {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ${token}`,
          }

Adicionar Medicamento
POST http://localhost:8080/medicamento/create
method: 'POST',
        headers: 
          {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ${token}`,
            'Active-Profile': userId, // Passando o ID do perfil
          },
        body:
          {
            "nome": "Paracetamol", 
            "quantidade": 10,
            "dosagem": "10mg",
            "tipo": "comprimido"
          }

Listagem de medicamentos (cURL)
GET http://localhost:8080/medicamento/list
method: 'GET',
        headers: 
          {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ${token}`,
            'Active-Profile': profileId, 
          }
