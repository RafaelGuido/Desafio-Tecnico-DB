# Desafio Técnico - Empresa DB

### Descrição do projeto :seedling:

API REST que faz a validação e atualização de conta e envia para a Receita Federal por CSV.

### Cenário :pushpin:

Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi já possiu mais de 4 milhões de contas ativas. Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, antes as 10:00 da manhã na abertura das agências.

### Requisito :rotating_light:

Usar o "serviço da receita" (fake) para processamento automático do arquivo.

### Funcionalidade :sparkles:

0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma nova coluna.

### Formato CSV :point_down:

agencia;conta;saldo;status;  
0101;12225-6;100,00;A  
0101;12226-8;3200,50;A  
3202;40011-1;-35,12;I  
3202;54001-2;0,00;P  
3202;00321-2;34500,00;B  
...

### Resolução do problema :bulb:

A aplicação recebe um CSV onde é transformado em uma lista de array de string, para ser encaminhado ao serviço da Receita Federal. Após a validação o serviço retornará 'true' ou 'false' para saber se a conta foi atualizada. Obtendo essa reposta é adicionado esta coluna no CSV.

### Pré-requisitos :thumbsup:

- JDK 11 e Maven
- IntelliJ ou Eclipse

### Foi utilizado :point_down:

- Postman
- Docker
- Spring Web (dependência)
- OpenCSV (dependência)
- SpringFox (dependência)
- SwaggerUI (dependência)
- SonarLint (plugin)
- Gitmoji Plus (plugin)
    
### Caso ocorra problema para subir o Docker em Windows :point_down:
- Clone com -> `git clone https://github.com/RafaelGuido/Desafio-Tecnico-DB.git  --config core.autocrlf=input`
    
### Subir aplicação via DOCKER :point_down:

- Iniciar docker no PC
- no diretório do projeto clonado -> `./mvnw package -Pdocker`
- após o package -> `docker-compose up`

### Instalação da aplicação :point_down:

- IntelliJ/Eclipse: Importar como projeto Maven.
- `mvn install`
    
### Iniciar aplicação :point_down:

- Rodar a classe DesafioTecnicoDbApplication

### Comando para os testes :point_down:

- `./mvnw test` (teste unitário)

### Documentação da API :speech_balloon:

- Basta acessar '/swagger-ui.html' na porta em uso

## Comportamento da API: :anger:

`PUT /receitas/atualiza-conta`

##### Body

    key: file
    value: (seuArquivo.csv)

##### Response

    HTTP/1.1 200 OK
    Content-Type: text/csv

    
    agencia;conta;saldo;status;atualizado;  
    0101;12225-6;100,00;A;true  
    0101;12226-8;3200,50;A;true  
    3202;40011-1;-35,12;I;true  
    3202;54001-2;0,00;P;true  
    3202;00321-2;34500,00;B;true  


    
