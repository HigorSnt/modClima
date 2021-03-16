# 🌥 modclima - Challenge

<p align="center">
  <a href="https://app.getpostman.com/run-collection/f61ec4a7c0969bcf0435">
    <img src="https://run.pstmn.io/button.svg" alt="Run in Postman" />
  </a>
  <a href="https://insomnia.rest/run/?label=modclima&uri=https%3A%2F%2Fraw.githubusercontent.com%2FHigorSnt%2FmodClima%2Fmain%2F.github%2Fmodclima.json%3Ftoken%3DAJVY2TBDJLYLBDACRAZGPGDALESNY" target="_blank"><img src="https://insomnia.rest/images/run.svg" alt="Run in Insomnia"></a>
</p>

<p align="center">
  <a href="#-sobre">Sobre</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-utilização">Utilização</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="./backend/README.md">Documentação da API</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="./frontend/README.md">Detalhes do Front-end</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-licença">Licença</a>
</p>

# 🕵 Sobre

Aplicação desenvolvida para o desafio fullstack da Cyan Agroanalytics.

# 🔗 Acessar API e Site

* [Site](https://modclima.netlify.app/)
* [API](https://modclima.herokuapp.com/)
* [Swagger](https://modclima.herokuapp.com/swagger-ui/index.html)
## 🛠 Utilização

- ### **Pré-requisitos**

  - É **necessário** possuir o **[Postgres](https://www.postgresql.org/download/)** instalado no computador
  - É necessário instalar a extensão **PostGis**, uma dica é utilizar o **Stack Builder**, instalado em conjunto ao Postgres.
  - Após instalar o postgres, é necessário criar um `database` com nome `modclima`. Em seguida, adicione a extensão do postges executando o comando `CREATE EXTENSION postgis`.
  - É **necessário** possuir o **[Git](https://git-scm.com/)** instalado e configurado no computador
  - É **necessário** possuir o **[Node.js](https://nodejs.org/en/)** instalado no computador
  - Também, é **preciso** ter um gerenciador de pacotes seja o **[NPM](https://www.npmjs.com/)** ou **[Yarn](https://yarnpkg.com/)**.
  - Por fim, é **necessário** instalar e configurar o **[Maven](https://maven.apache.org/install.html)**.

1. Faça um clone do repositório:

```sh
  $ git clone https://github.com/HigorSnt/modClima.git
```

2. Executando a Aplicação:

```sh
  # Inicialmente, é importante entrar na pasta gerada após o comando de clone
  $ cd modClima

  # Em seguida, é importante abrir a pasta da API
  $ cd backend
  # Agora, é necessário executar a API
  $ mvn spring-boot:run

  # Aplicação web
  $ cd frontend
  # Instalando as dependências do projeto.
  $ yarn # ou npm install
  # Inicie a aplicação web
  $ yarn start # ou npm start
```

## ⚓ Licença

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.
