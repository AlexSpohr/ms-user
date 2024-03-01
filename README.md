# Challenge III - PB Springboot Dez 2023
> Status: Entregue
-------------------------------------------------------------------------------------------------------


O MS User tem a responsabilidade de armazenar e gerenciar os dados de usuário.

### Colaborador - Alex Spohr

<table>
  <tr>
    <td>E-mail</td>
    <td>GitHub</td>
  </tr>
   <tr>
    <td>alex.spohr.pb@compasso.com.br</td>
    <td>AlexSpohr</td>
  </tr>
</table>

-------------------------------------------------------------------------------------------------------

### Tecnologias Utilizadas
<table>
  <tr>
    <td>Java</td>
    <td>Spring</td>
    <td>MySql</td>
  </tr>
  <tr>
    <td>17.*</td>
    <td>3.2</td>
    <td>8.0</td>
  </tr>
</table>

-------------------------------------------------------------------------------------------------------

### Setup
1. Clone o repositório
```
git clone https://github.com/AlexSpohr/ms-user.git
```
2. Configure o banco de dados no arquivo `application.properties`

3. Execute a aplicação
```
mvn spring-boot:run
```
A aplicação deverá estar em execução e acessível em http://localhost:8080/

-------------------------------------------------------------------------------------------------------

### Endpoints MS User

`POST - /v1/users`

Criar um novo usuário  
Para criar um novo usuário, é necessário enviar um JSON com os campos de forma correta para retornar 201,  
se já existe com o mesmo CPF ou e-mail, é retornado um erro 409. Em casos de dados de entrada inválido, é retornado  
um erro 422.  
  
Ao criar o usuário, são setadas as informações na entidade User, a senha é criptografada e o endereço é buscado  
no MS Address, caso o endereço não seja encontrado, é retornado um erro 404. A ligação entre os dois é feita  
a partir do OpenFeign. Após, o sistema passa as informações para um UserResponseDTO e retorna o usuário criado.

Request:
```
{
  "firstName": "Nome",
  "lastName": "Sobrenome",
  "cpf": "671.405.130-15",
  "birthdate": "2004-03-03",
  "email": "user@email.com",
  "cep": "01311-000",
  "password": "12345678",
  "active": true
}
```

Response:
```
{
    "id": 1,
    "address": {
        "cep": "01311000",
        "uf": "SP",
        "ddd": 11,
        "localidade": "São Paulo",
        "logradouro": "Avenida Paulista",
        "complemento": "até 609 - lado ímpar"
    },
    "firstName": "Nome",
    "lastName": "Sobrenome",
    "cpf": "671.405.130-15",
    "birthdate": "2004-03-03",
    "email": "user@email.com",
    "active": true
}
```


`GET - /v1/users/:id`

Exibe o usuário por ID.  
É verificado se o usuário está cadastrado no banco de dados, caso não esteja, é retornado um erro 404. Após  
a verificação, é retornado o usuário com o ID especificado se é de acordo com o ID do usuário logado, isso retorna  
um status 200, caso contrário, é retornado um erro 403.  

É retornado um UserResponseDTO com as informações do usuário. Para isso, é feita a busca no banco de dados e é chamado  
o MS Address para buscar o endereço do usuário a partir do address_id que vincula o endereço ao usuário.


Response:
```
{
    "id": 1,
    "address": {
        "cep": "01311000",
        "uf": "SP",
        "ddd": 11,
        "localidade": "São Paulo",
        "logradouro": "Avenida Paulista",
        "complemento": "até 609 - lado ímpar"
    },
    "firstName": "Nome",
    "lastName": "Sobrenome",
    "cpf": "929.633.000-93",
    "birthdate": "31/12/2003",
    "email": "user@email.com",
    "active": true
}
```



`PUT - /v1/users/:id/password`

Atualiza a senha do usuário.  
Segue a mesma lógica de conferir se o usuário logado está querendo atualizar pelo seu id. Caso ele consiga e passe uma  
nova senha com no mínimo 6 caracteres retorna um status ok 200. Retorna 404 se não for encontrado, 422 se os dados de   
entrada são inválidos e 403 se não tem acesso ao recurso.

Request:
```
{
  "password": "12345678910",
}
```


Response:

```
{
  200 OK
}
```

`PUT - /v1/users/:id`

Atualiza informações do usuário cadastrado.  
Confere se o usuário logado está querendo atualizar pelo seu id e passa as informações da maneira correta, retorna um  
status ok 200. Retorna 404 se não for encontrado, 422 se os dados de entrada são inválidos e 403 se não tem acesso  
ao recurso.

Request:
```
{
  "firstName": "testeupdate",
  "lastName": "sobrenomeupdate",
  "cpf": "424.658.700-17",
  "birthdate": "2004-03-03",
  "email": "testeupdate@email.com",
  "cep": "01311-000",
  "active": true
}
```


Response:

```
{
  200 OK
}
```
-------------------------------------------------------------------------------------------------------


### Link Swagger

http://localhost:8080/swagger-ui/index.html

-------------------------------------------------------------------------------------------------------


### Testes

Testes desenvolvidos para o controller, a cobertura não foi como esperada, tive uma dificuldade ao utilizar o  
assertEquals nos testes do service, na qual ao invés de comparar valores ele compara o endereço da memória, por  
esse motivo no momento demandei mais tempo que o esperado e não consegui entregar com 70% de cobertura. 


