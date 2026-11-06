# Roteiro manual de testes

Execute os testes na ordem abaixo depois de confirmar o banco `Enciclopedia_db`, usuario `postgres` e senha `postdba` no PostgreSQL. A aplicacao usa sequencias para manter compatibilidade com o servidor 9.5.

| # | Acao | Resultado esperado |
|---|---|---|
| 1 | Abrir `http://localhost:8080` | A pagina inicial aparece com jogadores de exemplo. |
| 2 | Reiniciar a aplicacao | Os jogadores nao sao duplicados. |
| 3 | Pesquisar por `Brasil` | A lista mostra jogadores cuja selecao corresponde a busca. |
| 4 | Criar uma conta comum | O sistema confirma o cadastro. |
| 5 | Entrar com a conta comum | O usuario consegue acessar a pagina de favoritos. |
| 6 | Abrir um jogador e adiciona-lo aos favoritos | O jogador aparece em `/favoritos`. |
| 7 | Remover o jogador dos favoritos | Ele deixa de aparecer na lista. |
| 8 | Avaliar um jogador com nota 4 | O perfil apresenta a media e a quantidade de avaliacoes. |
| 9 | Avaliar o mesmo jogador com nota 5 | A nota anterior e atualizada; a quantidade continua sendo uma avaliacao daquele usuario. |
| 10 | Tentar abrir `/admin/jogadores` com usuario comum | O acesso e negado. |
| 11 | Sair e entrar como administrador | O painel administrativo fica disponivel. |
| 12 | Cadastrar, editar e excluir um jogador | As alteracoes aparecem no catalogo e no banco. |

Conta administrativa padrao de desenvolvimento: `admin@copa.com` / `admin123`.


## Testes de fotos

1. Abra o painel administrativo e cadastre um jogador com uma imagem JPG, PNG ou WEBP de ate 5 MB.
2. Salve e confirme que a foto aparece no catalogo e no perfil do jogador.
3. Edite o jogador e confirme que a foto atual continua aparecendo sem precisar envia-la novamente.
4. Troque a foto por outra e confirme que a nova imagem aparece.
5. Teste uma URL externa de imagem.
6. Tente enviar um arquivo de texto ou uma imagem acima de 5 MB; o sistema deve informar que o arquivo nao e aceito.
7. Confirme que as dez fotos padrao aparecem nos registros de exemplo quando o computador tem acesso a internet.
