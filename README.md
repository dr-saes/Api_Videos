# Api de Videos (api_aluraflix)



## Tecnologias utilizadas:

-  Java.
-  Maven.
-  InteliJ
-  SpringBoot.
-  Postgres.
-  Docker

___________________________________________________________________


## URI's para visualização pelo Heroku:

#### Todas as Videos:
....a desenvolver...

#### Todas as Categorias:
....a desenvolver...


___________________________________________________________________

## Rotas para:

- Usuarios


### Método: GET (lista todos usuários) - "ROLE_ADMIN"
#### URL: /usuarios



### Método: GET (lista usuário por Id) - "ROLE_ADMIN"
#### URL: /usuarios/{id}



### Método: GET (lista usuário por nome) - "ROLE_ADMIN"
#### URL: /usuarios?nome={nome}



### Método: POST (cadastra usuário) - "ROLE_ADMIN"
#### URL: /usuarios
##### BodyParams: Json com nome, email e senha: 	 
    
    {
      "nome": "nome",
      "email" "email@email.com",
      "senha": "123456"
    } 
    
###### Todos os usuarios criados recebem o perfil "ROLE_USUARIO"
    
    
    
    
### Método: PUT (atualiza usuário) - "ROLE_ADMIN"
#### URL: /usuarios/{id}
##### BodyParams: Json com nome, email e senha: 	 
    
    {
      "nome": "nome",
      "email" "email@email.com",
      "senha": "123456"
    } 
    
    
### Método: DELETE (deleta usuário por id) - "ROLE_ADMIN"
#### URL: /usuarios/{id}

___________________________________________________________________


 - Autenticação


### Método: POST (autentica usuário) 
### URL:  /auth 
    
##### BodyParams: Json com nome e senha: 	 
                                       
    {
      "nome": "nome",
      "senha": "123456"
    }   
    
###### Retorno: Bearer Token


___________________________________________________________________


- Videos

### Método: GET (lista todos videos) - "ROLE_ADMIN" e "ROLE_USUARIO"
#### URL: /videos



### Método: GET (lista video por Id) - "ROLE_ADMIN" e "ROLE_USUARIO"
#### URL: /videos/{id}



### Método: GET (lista video por titulo) - "ROLE_ADMIN" e "ROLE_USUARIO"
#### URL: /videos?titulo={titulo}



### Método: GET (lista video por id da categoria) - "ROLE_ADMIN" e "ROLE_USUARIO"
#### URL: /categorias/:{id}/videos


   
### Método: POST (cadastral um video) - "ROLE_ADMIN" e "ROLE_USUARIO"
#### URL: /videos

##### BodyParams: Json com dados do video. 	 
    
     {
          "titulo": "12 anos",
          "descricao": "12-anos",
          "url": "https://www.filme.com/amarelo",
          "categoria" : {
                            "id" : "3",
                            "titulo": "12-anos",
                            "cor": "AMARELO"
                        }
    }
    
###### Se os parametros de categoria estiverem nulos é atribuida a categoria Livre (id = 1, titulo = Livre , cor = VERDE)


    {
        "titulo": "Filme livre",
        "descricao": "Livre",
        "url": "https://www.filme.com/livre",
        "categoria" : {
                          "id" : "null",
                          "titulo": "null",
                          "cor": "null"
                        }
    }
    
     
    
### Método: PUT (atualiza um video) - "ROLE_ADMIN" 
#### URL: /videos/{id}

##### BodyParams: Json com dados do video. 	 
    
     {
          "titulo": "12 anos",
          "descricao": "12-anos",
          "url": "https://www.filme.com/amarelo",
          "categoria" : {
                            "id" : "3",
                            "titulo": "12-anos",
                            "cor": "AMARELO"
                        }
    }    
    
    
### Método: DELETE (deleta video por id) - "ROLE_ADMIN"
#### URL: /videos/{id}

___________________________________________________________________


 - Categorias    
 
 ### Método: GET (lista todas categorias) - "ROLE_ADMIN" 
#### URL: /categorias



### Método: GET (lista categoria por Id) - "ROLE_ADMIN" 
#### URL: /categorias/{id}



### Método: GET (lista categoria por titulo) - "ROLE_ADMIN" 
#### URL: /categorias?titulo={titulo}



   
### Método: POST (cadastral uma categoria) - "ROLE_ADMIN" 
#### URL: /categorias

##### BodyParams: Json com dados da categoria. 	 
    
     {
        "titulo": "12-anos",
        "cor": "AMARELO"
                        
    }
    
 
     
    
### Método: PUT (atualiza uma categoria) - "ROLE_ADMIN" 
#### URL: /categorias/{id}

##### BodyParams: Json com dados da categoria. 	 
    
    {
        "titulo": "Livre",
        "cor": "VERDE"
                        
    } 
    
    
### Método: DELETE (deleta categoria por id) - "ROLE_ADMIN"
#### URL: /videos/{id}

___________________________________________________________________
 
 
 
 
 
 
 
 
 
 
 
 
 
    
    
 
