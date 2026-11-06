# Enciclopedia da Copa

Projeto interdisciplinar escolar para consultar jogadores de futebol, favoritar perfis e avaliar jogadores. A aplicacao usa Java 17, Spring Boot 3.5.16, Spring MVC, Thymeleaf, Bootstrap 5, Spring Data JPA e PostgreSQL.

## 1. Funcionalidades

### Visitante e usuario comum
- Consultar a lista de jogadores e pesquisar por nome ou selecao.
- Abrir a pagina de detalhes de cada jogador.
- Criar uma conta e entrar com e-mail e senha.
- Favoritar e remover jogadores dos favoritos.
- Avaliar cada jogador com uma nota de 1 a 5 e um comentario opcional.
- Alterar a propria avaliacao. Cada conta possui somente uma avaliacao por jogador.

### Administrador
- Entrar com uma conta administrativa.
- Cadastrar, editar e excluir jogadores.
- Enviar foto do computador (JPG, PNG ou WEBP, até 5 MB) ou informar uma URL de imagem.
- Somente o perfil `ADMIN` acessa `/admin/**`.

## 2. Requisitos

- JDK 17 ou superior.
- Maven 3.6.3 ou superior.
- PostgreSQL instalado e em execucao.
- pgAdmin para administrar o banco (opcional; a aplicacao conecta diretamente ao PostgreSQL).

## 3. Configurar o banco de dados

O projeto esta configurado para usar o banco `Enciclopedia_db`, com usuario `postgres` e senha `postdba`, conforme a configuracao informada para o computador da escola. No pgAdmin, confirme se esse banco existe com essa grafia. Se o nome do banco, usuario ou senha forem diferentes na sua maquina, ajuste `src/main/resources/application.properties`.

A configuracao esta em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Enciclopedia_db
spring.datasource.username=postgres
spring.datasource.password=postdba
spring.jpa.hibernate.ddl-auto=update
app.admin.email=admin@copa.com
app.admin.password=admin123
```

Altere usuario e senha para os mesmos dados utilizados no PostgreSQL da maquina. Tambem e possivel definir as variaveis de ambiente `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` sem modificar o arquivo.

O Hibernate cria e atualiza as tabelas automaticamente. Nao e necessario importar um arquivo `.sql`. Para funcionar com o PostgreSQL 9.5 instalado no computador da escola, as quatro entidades usam sequencias explicitas (`usuario_seq`, `jogador_seq`, `favorito_seq` e `avaliacao_seq`) em vez de colunas `GENERATED AS IDENTITY`, que nao existem nessa versao antiga.

## 4. Executar o projeto

Abra a pasta `enciclopedia-copa` no VS Code, IntelliJ ou Spring Tool Suite. Aguarde o Maven baixar as dependencias e execute a classe:

`src/main/java/br/com/etecfer/enciclopediacopa/EnciclopediaCopaApplication.java`

Ou, em um terminal na pasta do projeto:

```bash
mvn spring-boot:run
```

Depois acesse: <http://localhost:8080>

## 5. Primeiro acesso

Na primeira inicializacao, o sistema cria automaticamente:

- 10 jogadores de exemplo, caso a tabela de jogadores esteja vazia.
- Uma conta administrativa, caso o e-mail configurado ainda nao exista.

Conta administrativa padrao para ambiente local/escolar:

| Campo | Valor |
|---|---|
| E-mail | `admin@copa.com` |
| Senha | `admin123` |

A senha e armazenada com BCrypt, e nao em texto puro. Para trocar os dados antes da primeira inicializacao, edite `app.admin.email` e `app.admin.password` no `application.properties` ou defina `ADMIN_EMAIL` e `ADMIN_PASSWORD` no ambiente. Exemplo no PowerShell:

```powershell
$env:ADMIN_EMAIL="admin@copa.com"
$env:ADMIN_PASSWORD="uma-senha-local"
mvn spring-boot:run
```

Importante: o administrador inicial so e criado se o e-mail ainda nao estiver cadastrado. Alterar a variavel de senha depois da conta criada nao troca automaticamente a senha existente.

Usuarios comuns criam a propria conta pela opcao **Criar conta** na pagina inicial.

## 6. Jogadores de exemplo e fotos

Os registros iniciais sao dados demonstrativos para o projeto funcionar logo na primeira execucao. Eles nao representam uma convocacao oficial de uma edicao especifica da Copa. Os dez jogadores de exemplo recebem fotos padrao hospedadas no Wikimedia Commons. Se os jogadores ja existirem no banco, a inicializacao completa a foto somente quando o campo estiver vazio.

No painel administrativo, ao cadastrar ou editar um jogador, escolha um arquivo no campo **Enviar foto do computador** ou informe uma URL no campo seguinte. Sao aceitos JPG, PNG e WEBP de ate 5 MB. O arquivo enviado fica na pasta `uploads/jogadores` dentro da pasta do projeto, e o endereco local e salvo na coluna `imagem_url`. O formulario mostra uma previa antes de salvar; quando um arquivo e selecionado, ele tem prioridade sobre a URL.

Para enviar uma foto, o formulario usa `multipart/form-data`. A aplicacao disponibiliza os arquivos pela rota `/uploads/**`. A pasta `uploads` e ignorada pelo Git para nao incluir fotos pessoais no repositorio. Os creditos e licencas das fotos padrao estao em `docs/LICENCAS_IMAGENS.md`.

As fotos padrao precisam de acesso a internet para carregar. Na primeira inicializacao desta versao, as fotos sao preenchidas automaticamente nos dez jogadores de exemplo que ja estao no banco; nao apague nem recrie as tabelas. A aplicacao tambem corrige os enderecos antigos que usavam o dominio incorreto `thumb.wikimedia.org`. Se uma imagem externa falhar, a pagina mostra as iniciais do jogador como alternativa. Fotos enviadas pelo painel ficam locais e nao dependem de um servico externo.

## 7. Estrutura do projeto

```text
src/main/java/br/com/etecfer/enciclopediacopa/
├── config/        Configuracao de seguranca e arquivos enviados
├── controller/    Rotas e paginas da aplicacao
├── model/         Entidades Usuario, Jogador, Favorito e Avaliacao
├── repository/    Interfaces de acesso ao PostgreSQL
├── service/       Regras de cadastro, pesquisa, interacoes e fotos
└── EnciclopediaCopaApplication.java

src/main/resources/
├── static/css/    Estilos complementares do Bootstrap
├── templates/     Paginas Thymeleaf
└── application.properties
```

## 8. Regras importantes

- O cadastro publico sempre cria usuario com perfil `USER`.
- O perfil `ADMIN` e criado pela inicializacao do sistema; usuarios comuns nao podem escolher esse perfil no formulario.
- A nota de avaliacao deve ser um numero inteiro entre 1 e 5.
- Um usuario pode avaliar cada jogador uma unica vez; uma nova avaliacao atualiza a anterior.
- Um usuario nao pode favoritar o mesmo jogador mais de uma vez.
- Ao excluir um jogador pelo painel, as avaliacoes e os favoritos relacionados tambem sao excluidos.
- A lista inicial de jogadores e inserida somente quando a tabela estiver vazia; as fotos padrao sao preenchidas em registros de exemplo que ainda estejam sem foto.
- Uma foto local antiga e removida quando o administrador a substitui ou exclui o jogador.

## 9. Observacao sobre o Bootstrap

O Bootstrap 5.3.3 e carregado por CDN. A primeira visualizacao das paginas precisa de acesso a internet para carregar os arquivos CSS e JavaScript. O CSS proprio do projeto esta em `src/main/resources/static/css/estilo.css`.


## 10. Compatibilidade com PostgreSQL 9.5

O servidor PostgreSQL 9.5 nao oferece suporte a colunas `GENERATED BY DEFAULT AS IDENTITY`. Por isso, cada entidade usa `GenerationType.SEQUENCE` com uma sequencia explicita e `allocationSize=1`. O Hibernate 6.6 ainda pode exibir um aviso informando que o PostgreSQL 9.5 esta fora da faixa oficialmente suportada. A alteracao corrige a sintaxe de identidade do log, mas nao transforma o PostgreSQL 9.5 em uma versao oficialmente suportada pelo Hibernate 6.6. Consulte `docs/CORRECAO_POSTGRESQL_95.md` para os detalhes.
