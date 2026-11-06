# ExpoGlass Effect

O efeito visual foi aplicado exclusivamente aos elementos de barra e aos botões:

- Barra de navegação (`.navbar-copa`): transparência, desfoque de fundo, saturação e borda iluminada.
- Faixa de chamada (`.cta-faixa`) e rodapé (`.footer-copa`): acabamento translúcido sutil.
- Botões Bootstrap (`.btn`): reflexo superior, transparência, desfoque, sombra e pequenas transições de interação.
- Botão principal (`.btn-copa`): vidro dourado para manter a identidade visual da Copa.

Cartões de jogadores, formulários, painéis, campos de entrada e áreas de conteúdo não receberam o efeito.

O efeito usa `backdrop-filter`; navegadores sem suporte continuam exibindo as cores de fundo de fallback. A preferência `prefers-reduced-motion` é respeitada.
