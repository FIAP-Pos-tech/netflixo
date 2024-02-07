# Netflixo - Readme

## Descrição

Uma aplicação web de streaming de vídeos desenvolvida utilizando as tecnologias Spring Boot, Spring WebFlux e Spring Data Mongodb Reactive.
Ao adotar o Spring WebFlux em nosso projeto, abraçamos a abordagem da programação reativa, que permite lidar com a concorrência e a eficiência de forma mais flexível e responsiva. Utilizamos ainda o Google Cloud Storage para fazer o upload dos vídeos. Assim, ao receber os vídeos, implementamos uma estratégia inovadora: quebramos o conteúdo em chunks, transformando o processo de download em uma experiência de streaming dinâmica. Essa abordagem não apenas otimiza a transferência de dados, mas também possibilita uma entrega contínua e assíncrona aos usuários finais.
A aplicação, por sua vez, permite o gerenciamento e a exibição de vídeos, com informações como categoria, título e descrição.

## Link do vídeo: 
https://drive.google.com/drive/folders/16PtczcfySJx7WQxzKooMSzqTZ6euJcZI?usp=sharing


## Requisitos Funcionais

1. **Criação e listagem .**
    - Endpoints:
        - `POST /videos` Cria baseado nos dados do vídeo.
        - `POST /videos/upload` Cria upload do arquivo.
        - `GET /videos/{category}` Lista todos os vídeos por categoria.

2. **Campos obrigatórios no corpo da requisição para `POST`**
   - categoria
   - título
   - descrição
  
3. **Listagem paginada.**
    - Endpoint: `GET /videos/{category}?page=0&size=5`
    - Parâmetros:
        - `page`: Número da página desejada.
        - `size`: Número de vídeos a serem listados por página.

4. **Filtros de busca por uuid.**
    - Endpoint: `/videos/{uuid}`
    - Parâmetros:
        - `id`: id do vídeo para busca.

5. **Sistema de recomendação utilizando o Apache Mahout baseado na nota fornecida pelo usuário.**
    - Endpoint: `/videos/recomenda`
    - Método: `GET`
    - Recomenda vídeos com base na nota do usuário.
