# Sistema de Agendamentos da Barbearia

Bem-vindo ao **Sistema de Agendamentos da Barbearia**, meu primeiro programa completo em Java! Este sistema foi desenvolvido para gerenciar clientes e agendamentos de uma barbearia de forma prática, com persistência de dados e uma interface simples no console. É um projeto que reflete meu aprendizado em programação orientada a objetos, manipulação de arquivos CSV e tratamento de exceções.

## 📌 O que o sistema faz?

O programa oferece as seguintes funcionalidades:

- **Cadastrar clientes**: Registra nome e telefone (10 ou 11 dígitos).
- **Listar clientes**: Exibe todos os clientes cadastrados.
- **Agendar horários**: Associa um cliente a uma data (`dd/mm/aaaa`), hora (`hh:mm`) e serviço.
- **Consultar agendamentos**: Mostra todos os agendamentos registrados.
- **Cancelar agendamentos**: Remove agendamentos com base no nome do cliente e data.
- **Consultar agenda do dia**: Filtra e exibe agendamentos do dia atual.
- **Remover clientes**: Exclui clientes que não possuem agendamentos ativos.
- **Persistência de dados**: Salva e carrega clientes e agendamentos em arquivos CSV (`clientes.csv` e `agendamentos.csv`).

### ✅ Validações incluídas

- Formato de telefone (ex.: `11987654321`).
- Formato de data e hora com mensagens de erro personalizadas.
- Verificação de horários ocupados.
- Impedimento de ações inválidas (ex.: remover cliente com agendamento ativo).

---

## 🚀 Como rodar o sistema?

### 🔧 Pré-requisitos

- **Java Development Kit (JDK)**: Versão 8 ou superior instalada.
- Um terminal ou IDE (como IntelliJ IDEA ou Eclipse).

### 📂 Passos para executar

1. **Baixe o projeto**
   - Clone o repositório (se estiver em um Git):
     ```bash
     git clone <URL_DO_REPOSITORIO>
     ```
   - Ou baixe os arquivos manualmente.

2. **Navegue até a raiz do projeto**
   - Abra o terminal e vá até a pasta raiz:
     ```bash
     cd caminho/para/BarbeariaProjeto
     ```

3. **Compile o código**
   - No terminal:
     ```bash
     javac -d . src/main/*.java src/models/*.java src/services/*.java src/utils/*.java
     ```
   - Ou use o recurso de build da sua IDE.

4. **Execute o programa**
   - No terminal:
     ```bash
     java main.Main
     ```
   - Ou clique em "Run" na IDE.

5. **Use o menu interativo**
   - Escolha opções de `0 a 7` para navegar pelas funcionalidades.
   - Digite `0` para sair e salvar os dados.

---

## 📂 Arquivos gerados

- **`clientes.csv`**: Lista de clientes cadastrados.
- **`agendamentos.csv`**: Lista de agendamentos.

Esses arquivos aparecem na raiz do projeto após a primeira execução com alterações.

---

## 🏗️ Estrutura do Código

**Pacotes:**
- `src/main`: Contém `Main.java` com o menu principal.
- `src/models`: Classes `Cliente` e `Agendamento` para os dados.
- `src/services`: Lógica em `GestorDeClientes`, `GestorDeAgendamentos` e `GestorDeRegras`.
- `src/utils`: `ArquivoUtils` para manipulação de CSV.

**Destaques:**
- Interface de console com layout claro e opção "Voltar ao menu".
- Tratamento de exceções para entradas inválidas.
- Persistência simples em arquivos CSV.

---

## 💡 Ideias Futuras

Quero evoluir o projeto com:

- **Interface Gráfica**: Usar JavaFX para uma experiência visual.
- **Horários Pré-definidos**: Limitar agendamentos a uma grade (ex.: 09:00 às 18:00).
- **Relatórios**: Mostrar estatísticas como "serviços mais pedidos".
- **Confirmação**: Perguntar "Tem certeza?" antes de ações críticas.
- **Banco de Dados**: Substituir CSV por SQLite para mais robustez.
- **Notificações**: Integrar lembretes por e-mail.

---

## 📢 Sobre o Projeto

Esse foi meu primeiro programa completo, e aprendi muito sobre Java, desde listas e streams até manipulação de arquivos. É simples, mas funcional, e estou orgulhoso do resultado. Se tiver sugestões, adoraria ouvir!
