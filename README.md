# ~~Anime Tracker App~~ Pokemon Card Tracker ![Pokemon Database Tracker](web_hi_res_512.png "Pokemon Card Database")
## Android Studio App for Pokemon Card Tracking for School Assigment

Mais que 1 Activity
Base de Dados(SQLite)
Acesso á Internet

Esta aplicação tem o objetivo de ir buscar todas as informações de cartas de Pokemon a uma API Rest onde vai buscar muita informação, da qual eu filtro posteriormente.
A aplicação permite ao utilizador pesquisar uma carta pelo seu nome ou pelo seu id unico e adicionar ao seu seu deck caso a possua.

Para além disso, a aplicação permite ter um acesso rápido ao deck bem como acesso ao perfil completo da carta num unico toque.
No deck também existe a opção de retirar uma carta do deck caso o utilizador já não a possua.

Esta aplicação permite resultados duplicados visto que podemos ter mais do que 1 carta do mesmo tipo

Um problema que encontrei e então decidi não implementar foi, ao inserir o URL da image na base de dados ele está a inseri-la como NULL.

No deck iria ter acesso á imagem da carta bem como o seu nome e ID, mas com esse problema acabei por retirar a imagem dessa actividade.

Nota: Nem todas as entradas na API têm todos os campos preenchidos. Por isso pode por vezes não aparecer o resultado
Ao apagar a carta, em vez de apagar 1 carta, ela apaga todas do mesmo tipo