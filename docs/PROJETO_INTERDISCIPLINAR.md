# Proposta interdisciplinar — Enciclopedia da Copa

## Tema

Enciclopedia digital de jogadores de futebol, com perfis, selecoes, favoritos e avaliacoes feitas pelos usuarios.

## Problema e justificativa

Informacoes sobre jogadores costumam ficar espalhadas em diferentes paginas. A enciclopedia organiza dados basicos em um unico ambiente e permite que os visitantes expressem suas opinioes por meio de notas e comentarios.

## Objetivo geral

Desenvolver uma aplicacao web simples que apresente jogadores de futebol e permita a participacao dos usuarios por meio de favoritos e avaliacoes.

## Objetivos especificos

- Organizar informacoes de jogadores em um banco de dados relacional.
- Apresentar os dados em paginas web responsivas.
- Diferenciar o acesso de usuarios comuns e administradores.
- Calcular a media das avaliacoes recebidas por cada jogador.
- Relacionar paises, selecoes e posicoes do futebol.

## Disciplinas envolvidas

| Disciplina | Contribuicao para o projeto |
|---|---|
| Informatica | Desenvolvimento em Java, Spring Boot, HTML, Bootstrap, MVC, banco de dados e controle de acesso. |
| Matematica | Calculo da media das notas, quantidade de avaliacoes e comparacao dos resultados. |
| Geografia | Identificacao dos paises e das selecoes representadas pelos jogadores. |
| Lingua Portuguesa | Escrita, revisao e padronizacao das biografias e dos textos de apresentacao. |
| Educacao Fisica | Pesquisa sobre posicoes, funcoes dos jogadores e fundamentos do futebol. |
| Historia | Contextualizacao da Copa do Mundo e levantamento de fatos sobre a trajetoria esportiva dos jogadores. |

## Escopo funcional

1. Pagina inicial com busca e jogadores em destaque.
2. Catalogo de jogadores com pesquisa por nome ou selecao.
3. Pagina individual com dados e biografia.
4. Cadastro e login de usuarios.
5. Lista pessoal de favoritos.
6. Avaliacao de 1 a 5, com comentario opcional.
7. Painel administrativo para cadastrar, editar e excluir jogadores.

## Modelo de dados

- **Usuario:** nome, e-mail, senha criptografada e perfil (`USER` ou `ADMIN`).
- **Jogador:** nome, selecao, posicao, numero da camisa, clube, biografia e imagem opcional.
- **Favorito:** liga um usuario a um jogador escolhido.
- **Avaliacao:** liga um usuario a um jogador e registra nota, comentario e data de atualizacao.

Relacionamentos principais:

- Um usuario pode possuir varios favoritos.
- Um jogador pode aparecer nos favoritos de varios usuarios.
- Um usuario pode avaliar varios jogadores.
- Um jogador pode receber avaliacoes de varios usuarios.
- Cada combinacao usuario + jogador pode ter somente um favorito e uma avaliacao.

## Limites para manter o projeto simples

Nao fazem parte da primeira versao: pagamento, chat, rede social, estatisticas automaticas de partidas, integracao com APIs esportivas, tabela de jogos, transmissao ao vivo ou cadastro de clubes em uma entidade separada.

## Sugestao de demonstracao

1. Abrir a pagina inicial e pesquisar um jogador.
2. Entrar com uma conta comum e favoritar o jogador.
3. Enviar uma nota e mostrar como a media e apresentada no perfil.
4. Entrar com a conta de administrador e cadastrar um novo jogador.
5. Mostrar no pgAdmin as tabelas e os relacionamentos criados pelo Hibernate.

## Criterios para avaliar o resultado

- As paginas funcionam em computador e celular.
- O cadastro e o login funcionam.
- Um usuario comum nao consegue abrir o painel administrativo.
- Favoritos nao se repetem para o mesmo usuario e jogador.
- Uma nova nota do mesmo usuario atualiza a avaliacao anterior.
- O administrador consegue manter o catalogo de jogadores.
- Os dados continuam no PostgreSQL apos reiniciar a aplicacao.
