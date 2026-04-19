
### Escopo revisado do projeto (Protótipo Console em Java)

#### 1. Requisitos Funcionais

**1.1. Autenticação e Controle de Acesso**
- O sistema possui **dois perfis de usuário** (definidos pelo campo `nivelAcesso`): `ADMINISTRADOR` e `OPERADOR`.
- Ao iniciar, exibe-se uma tela de **Login** (usuário e senha, sem criptografia para o protótipo).
- Após autenticação, o sistema exibe um **menu específico conforme o nível de acesso**:
  - **ADMINISTRADOR:** Acesso a todas as funcionalidades de CRUD (Usuários, Fornecedores, Produtos, Transportadoras, Cargas).
  - **OPERADOR:** Acesso apenas a **Consultas** e **Cadastro/Consulta de Cargas**. Não pode alterar cadastros base (Fornecedores, Produtos, Transportadoras, Usuários).

**1.2. Cadastros e Operações (CRUD)**
Para as entidades **Usuário, Fornecedor, Transportadora, Produto**, as seguintes operações estão disponíveis (conforme permissão):
- **Inclusão**
- **Alteração** (busca por código)
- **Exclusão** (busca por código)
- **Consulta por código** (exata)
- **Consulta por nome** (parcial, tipo "contém")

**1.3. Relacionamentos Específicos**
- **Fornecedor ↔ Produto:** Relação **N:N**.
  - Um produto pode ser fornecido por vários fornecedores.
  - Um fornecedor pode prover vários produtos.
  - **Classe Associativa:** `Fornecimento` (sem atributos adicionais para o protótipo).
- **Carga (Remessa):** Representa uma lista de produtos a serem transportados por uma única transportadora.
  - **Atributos:** `codigo`, `dataCriacao` (preenchida automaticamente), `transportadora` (associação 1:N).
  - **Itens da Carga:** Lista de objetos contendo `produto` e `quantidade`.
  - Uma carga pode conter produtos de **diferentes fornecedores** (a associação é feita pelo produto, não diretamente pelo fornecedor).

#### 2. Estrutura de Dados (Modelo de Classes Simplificado)

| Classe         | Atributos Principais                               |
| :------------- | :------------------------------------------------- |
| **Usuario**    | `codigo`, `nome`, `login`, `senha`, `nivelAcesso` (Enum) |
| **Fornecedor** | `codigo`, `nome`, `cnpj` (ou documento)            |
| **Transportadora** | `codigo`, `nome`, `modal` (opcional)            |
| **Produto**    | `codigo`, `descricao`, `precoSugerido`             |
| **Fornecimento** | (Classe Associativa) `fornecedor`, `produto`       |
| **Carga**      | `codigo`, `dataCriacao`, `transportadora`, `itens` (List<ItemCarga>) |
| **ItemCarga**  | `produto`, `quantidade`                            |

#### 3. Massa de Dados para Demonstração (Pré-carregada em Memória)
Ao iniciar o sistema pela primeira vez (ou em modo de demonstração), a estrutura deve ser populada com:
- **2 Usuários:**
  - Login: `admin` / Senha: `123` (Nível: ADMINISTRADOR)
  - Login: `oper` / Senha: `123` (Nível: OPERADOR)
- **3 Fornecedores**
- **7 Produtos por Fornecedor** (total 21 produtos, respeitando a relação N:N estabelecida no item 1.3).
- **3 Transportadoras**
- **4 Cargas** previamente cadastradas, cada uma contendo:
  - Uma transportadora selecionada.
  - Itens com produtos e quantidades variadas.

#### 4. Restrições Técnicas (Protótipo)
- **Persistência:** Apenas em **memória RAM** (uso de `ArrayList` ou `HashMap`). Nada de banco de dados ou arquivos.
- **Interface:** Exclusivamente **Console** (System.in / System.out).
- **Arquitetura:** Foco na criação do **Diagrama de Classes UML** que servirá de guia para codificação posterior.

---

```
> Doc gerado pelo DeepSeek com requisitos e orientações mais claras
```