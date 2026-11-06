# Correcao do cadastro, edicao e fotos

## Erro Whitelabel

O log enviado apontava para `templates/admin/jogadores/formulario.html`, linha 34: o Thymeleaf exige que cada `<option>` tenha um atributo `value`. Todas as opcoes do campo Posicao agora possuem `value` explicito.

## Fotos dos dez jogadores iniciais

- Os dez jogadores de exemplo recebem fotos padrao do Wikimedia Commons.
- Os enderecos antigos com `thumb.wikimedia.org` sao migrados para `upload.wikimedia.org`.
- A aplicacao preenche as fotos uma vez nos registros de exemplo que ja existiam no banco, sem excluir os dados.
- Os creditos e licencas estao em `LICENCAS_IMAGENS.md`.
- As fotos padrao precisam de internet.

## Fotos no cadastro e na edicao

O formulario administrativo aceita JPG, PNG e WEBP de ate 5 MB, com previa antes de salvar. Tambem aceita uma URL de imagem. O upload local tem prioridade quando os dois campos sao usados. As fotos locais ficam em `uploads/jogadores` e sao servidas pela rota `/uploads/**`.

## Como aplicar

1. Feche a versao antiga da aplicacao.
2. Extraia este ZIP em uma pasta nova.
3. Mantenha o banco `Enciclopedia_db`; nao apague as tabelas.
4. Abra o projeto atualizado e execute `EnciclopediaCopaApplication`.
5. Acesse o painel administrativo e teste cadastrar e editar um jogador com uma foto JPG ou PNG.

A inicializacao atualiza a coluna `imagem_url` pelo Hibernate com `ddl-auto=update`; nao e necessario executar SQL manual para as fotos.
