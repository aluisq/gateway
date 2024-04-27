# Passo a Passo para Implementação

## 1º -  Rodando os scripts no MYSQL8, levando em consideração que já exista o MYSQL8 instalado na máquina

    Os scripts se encontram na pasta src/main/resources
    -   create_neurotech_schema.sql (Script responsável por criar o schema no Mysql8) 
    -   create_table.sql (Script responsável por criar a tabela que será alimentada pela aplicação)

## 2º - Compilação da Aplicação

### Essa aplicação foi projetada para que seja compilado um FAT JAR, ou seja, um executável com todas as dependências embutidas

- Gerando o executável

    - Acesse a pasta raiz do projeto

    - Rodar o comando: ```mvn clean```

     - Rodar o comando: ```mvn package```

- Feito isso, dois jar serão gerados dentro de uma pasta nova chamada target

- Executando os jar,:
  - Para isso tenha certeza de ter instalado a versão 11 do Java, acesse a pasta target e execute o comando : ```java -jar .\Gateway-1.0-SNAPSHOT.jar```

    













