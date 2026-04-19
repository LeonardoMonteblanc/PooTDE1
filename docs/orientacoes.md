#### 1. Escopo do Sistema

Sistema de console com autenticação e dois níveis de acesso: **ADMINISTRADOR** e **OPERADOR**. 

**Funcionalidades por Perfil:**

- **ADMINISTRADOR:** Acesso completo a todos os cadastros e operações.
- **OPERADOR:** Acesso limitado a consultas em todos os cadastros e à gestão de Remessas.

**Operações por Entidade (conforme permissão):**
- Inclusão
- Alteração (por código)
- Exclusão (por código)
- Consulta por código
- Consulta por nome (parcial)

**Relacionamentos:**

- **Fornecedor ↔ Produto:** Associação N:N.
  - Classe Associativa: `Fornecimento`.

- **Remessa:**
  - Associada a uma única `Transportadora`.
  - Composta por uma lista de `ItemRemessa`.
  - Cada `ItemRemessa` referencia um `Produto` e uma `quantidade`.
  - Uma remessa pode conter produtos de diferentes fornecedores (via produto).

#### 2. Modelo de Classes (Conceitual)

**Hierarquia de Herança:**

- **`Pessoa`** (abstrata)
  - Atributos: `codigo` (int), `nome` (String)
- **`Usuario`** herda de `Pessoa`
  - Atributos adicionais: `login` (String), `senha` (String), `nivelAcesso` (String)
- **`Fornecedor`** herda de `Pessoa`
  - Atributo adicional: `cnpj` (String)
- **`Transportadora`** herda de `Pessoa`

**Classes Independentes:**
- **`Produto`**
  - Atributos: `codigo` (int), `descricao` (String), `precoSugerido` (double)
- **`Remessa`**
  - Atributos: `codigo` (int), `dataCriacao` (LocalDate), `transportadora` (Transportadora), `itens` (List&lt;ItemRemessa&gt;)
- **`ItemRemessa`**
  - Atributos: `produto` (Produto), `quantidade` (int)
- **`Fornecimento`** (classe associativa)
  - Atributos: `fornecedor` (Fornecedor), `produto` (Produto)

**Visibilidade e Encapsulamento:**

- Todos os atributos são privados.
- Métodos públicos de acesso (getters/setters) para todos os atributos.

#### 3. Dados Iniciais para Demonstração

Pré-carga obrigatória ao iniciar o sistema:

- **Usuários:**
  1. Login `admin` / Senha `123` / Nível `ADMIN`
  2. Login `oper` / Senha `123` / Nível `OPERADOR`
- **Fornecedores:** 3
- **Produtos:** 7 por fornecedor (total 21), com registros correspondentes em `Fornecimento`
- **Transportadoras:** 3
- **Remessas:** 4, com itens variados e associadas a transportadoras distintas.


---

```
> Documento gerado por IA a partir da orientação do trabalho e decisões dos estudantes para organização do escopo do que será executado
```
