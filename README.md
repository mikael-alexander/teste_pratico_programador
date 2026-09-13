# Guia de Execução

Este projeto é uma aplicação desktop desenvolvida em Java (Swing) e utiliza o PostgreSQL como banco de dados. Siga os passos abaixo para preparar o ambiente e rodar o projeto localmente.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas na sua máquina:
- **[Java JDK 26](https://jdk.java.net/26/)** (ou a versão compatível configurada no `pom.xml`)
- **[Apache Maven](https://maven.apache.org/)** (geralmente já vem embutido no NetBeans)
- **[Apache NetBeans IDE](https://netbeans.apache.org/)**
- **[PostgreSQL](https://www.postgresql.org/)** (Rodando localmente)

## Configuração do Banco de Dados

A aplicação cria as tabelas automaticamente na inicialização (`users` e `employees`), mas precisa que o servidor PostgreSQL esteja ativo e acessível com as credenciais corretas.

Por padrão, o projeto espera a seguinte configuração do banco:
- **URL:** `jdbc:postgresql://localhost:5432/postgres`
- **Database:** `postgres`
- **Usuário:** `postgres`
- **Senha:** `1234`

> ⚠️ **Nota:** Se o seu PostgreSQL estiver configurado com uma senha diferente (por exemplo, `postgres` ou sem senha) ou usando outra porta, você precisará alterar as constantes no arquivo `src/main/java/br/com/vaga_programador/teste_pratico/util/DatabaseConnection.java` antes de executar.

## Como Executar

### Via IDE Apache NetBeans

1. Abra o **Apache NetBeans**.
2. Vá no menu **File** (Arquivo) > **Open Project...** (Abrir Projeto...).
3. Navegue até a pasta do projeto (a pasta que contém o ícone com um "m", indicando que é um projeto Maven) e clique em **Open Project**.
4. Aguarde o NetBeans carregar o projeto e baixar as dependências do `pom.xml` (como o driver JDBC do PostgreSQL). Você pode acompanhar o progresso na barra de status inferior.
5. No painel de **Projects** (Projetos) à esquerda, expanda o projeto.
6. Clique com o botão direito sobre o nome do projeto (`teste_pratico`) e selecione **Clean and Build** (Limpar e Construir) para garantir que tudo seja compilado corretamente.
7. Para rodar, clique com o botão direito novamente no projeto e selecione **Run** (Executar), ou simplesmente pressione a tecla **F6** (ou o botão de "Play" verde no topo).
8. Como é a primeira execução, o NetBeans pode pedir para você selecionar a classe principal. Selecione a classe `br.com.vaga_programador.teste_pratico.Teste_pratico` e confirme.

### Opção Alternativa: Via Linha de Comando (Maven)

Caso prefira rodar pelo terminal integrado do NetBeans ou externo:
1. Abra o terminal na pasta raiz do projeto.
2. Compile e baixe as dependências: `mvn clean compile`
3. Execute a classe principal: `mvn exec:java`

## Possíveis Erros e Soluções

- **`Erro ao inicializar o banco de dados. Verifique a conexao e as credenciais.`**: Ocorre quando a aplicação não consegue se conectar ao PostgreSQL. Verifique se o serviço do PostgreSQL está rodando, se a porta 5432 está livre, e se a senha no arquivo `DatabaseConnection.java` bate com a sua senha local do banco.
- **Erro de compilação de versão do Java**: Caso o NetBeans acuse erro na versão do JDK, clique com o botão direito no projeto > **Properties** (Propriedades) > **Build** > **Compile** e verifique se a versão da plataforma Java bate com o exigido no `pom.xml`.
