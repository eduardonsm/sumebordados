# SumeBordados

Projeto fullstack composto por uma API Spring Boot (Backend) e uma interface React (Frontend).

## 📋 Pré-requisitos

Certifique-se de ter instalado em sua máquina:

Java JDK (17 ou superior) <br>
Maven <br>
Docker <br>
Node.js <br>
##  🚀 Backend (API)

O backend utiliza PostgreSQL (via Docker) e Spring Boot.

1. Subir o Banco de Dados

Na raiz do projeto, acesse a pasta backend e inicie o container do banco:
```
cd backend
docker compose up -d
```
2. Executar a Aplicação

Ainda na pasta backend, inicie o servidor Spring Boot:

Linux / Mac / Windows (PowerShell):
```
mvn spring-boot:run
```


Nota: Certifique-se de que o container do banco de dados já está rodando antes de iniciar o Spring Boot.

## 💻 Frontend (Web)

O frontend é desenvolvido em React. Abra um novo terminal para manter o backend rodando.

1. Instalar Dependências

Na raiz do projeto, acesse a pasta frontend e baixe os pacotes necessários:
```
cd frontend
npm install
```

2. Rodar o Projeto

Inicie o servidor de desenvolvimento:
```
npm run dev
```

O terminal exibirá o link de acesso local (geralmente http://localhost:5173).