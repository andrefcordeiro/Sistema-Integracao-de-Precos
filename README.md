# Sistema de Integração de Preços

Projeto da disciplina de Banco de Dados da Universidade Estadual de Londrina. Consiste em um Sistema de Integração de Preços que agrega informações de jogos em sites de vendas com o objetivo de gerar um histórico de dados, incluindo preço, avaliações e etc.

## Tecnologias utilizadas
- Java EE
- Tomcat 9.0.59
- PostgreSQL
- Python (Scrapy)

## Estrutura da aplicação
### :spider: <strong> Crawling </strong>
Para realizar a aquisição dos dados foram desenvolvidos <strong> scripts de crawling para 4 lojas diferentes</strong>. Esses scripts (spiders) se encontram no diretório 'crawling/spiders'.

Para executar um script basta executar o seguinte comando (à partir diretório 'crawling'):
```
scrapy crawl <nome_do_spider>
```
A saída da execução de um script é um <strong> arquivo .json</strong> que será gerado no diretório '/output/<nome_da_loja>/'


### :desktop_computer: <strong> Sistema Web </strong>
O sistema web é dividido em duas partes principais: a <strong>interface privada</strong> e a <strong>interface pública</strong>.

A <strong>interface privada</strong> define o ambiente onde serão feitas as inserções de dados no sistema. Seu papel principal é receber um arquivo .json resultante do crawling de uma loja como entrada, fazer a leitura desse arquivo e, por fim, realizar a inserção dos dados lidos no banco de dados.

A <strong>interface pública</strong> é responsável por apresentar os dados previamente inseridos no sistema por meio da interface privada. Por meio dela é possível pesquisar por jogos e visualizar seu histórico de preço, avaliações, perguntas de clientes. Além disso, também é possível visualizar estatísticas gerais sobre os dados inseridos, como classificação de gêneros de jogos, jogos mais bem avaliados, entre outros.
