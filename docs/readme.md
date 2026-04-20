### Orientações

- `/docs`: documentação do projeto: Diagrama e orientações do trabalho
- `/code`: projeto java
  - `/scr`: source do projeto e Main.java

  **Packages:**
    - `/modelo`: classes do projeto - getters/setters 
    - `/controle`: logica do projeto


<br>

---
### O que falta fazer ou pode ser melhorado: 

- [ ] Ajustar funcao Listar() 
- [ ] Ajustar funcao mostrarMenu()
  - [ ] Separar a funções do menu para diminuição do código de controle
  - [ ] Ter em mente que o ajuste disso também implica no ajuste da validacao do usuarioLogado (cliente nao pode acessar informações que não dizem respeito a ele)
- [ ] Implementar cadastro, exclusao, alteracao = por input do usuario
- [ ] Implementar pesquisa por codigo ou nome 



### Para realizar login no sistema:
```
login: leo
senha: 123

login: vitor
senha: 123
```
---

### Documentação:
```
src/
├── controle/
│   ├── Dados.java
│   └── SistemaControle.java
└── modelo/
    ├── Pessoa.java (abstrata)
    ├── Usuario.java
    ├── Fornecedor.java
    ├── Transportadora.java
    ├── Produto.java
    ├── ItemPedido.java
    ├── Pedido.java
    └── Remessa.java
```

### Fluxo: 
```
  - Usuario seleciona um produto
    - Um pedido é criado
    - Pedido possui uma lista de produtos e quantidades (ItemPedido)
    - Após conclusao do pedido cria-se uma remessa
    - Remessa possui uma lista de pedidos 
```

## Package `modelo`

### `Pessoa` (abstrata)

### `Usuario extends Pessoa`
- `nivelAcesso` (`"ADMIN"` ou `"CLIENTE"`)

### `Fornecedor extends Pessoa`
### `Transportadora extends Pessoa`
- herda código e nome.

### `Produto`
- **Atributos privados:** `int codigo`, `String descricao`, `double preco`, `List<Fornecedor> fornecedores`
- **Construtores:**
  - `Produto(int codigo, String descricao, double preco, Fornecedor... fornecedores)`
- **Métodos:** `adicionarFornecedor(Fornecedor f)`, getters/setters.
- **Associação:** o produto conhece seus fornecedores

### `ItemPedido`
- **Atributos privados:** `Produto produto`, `int quantidade`

### `Pedido`
- **Atributo privado:** `ArrayList<ItemPedido> itens`
- **Métodos:**
  - `adicionarItem(Produto p, int qtd)`: se o produto já estiver na lista, soma a quantidade; senão, adiciona novo `ItemPedido`.
  - `removerItem(Produto p)`: remove a primeira ocorrência do produto

### `Remessa`
- **Atributos privados:**
  - `int codigo`
  - `LocalDate data` (inicializado com `LocalDate.now()`)
  - `Transportadora transportadora`
  - `Usuario cliente`
  - `List<Pedido> pedidos`
- **Métodos:** `adicionarPedido(Pedido p)`, `removerPedido(Pedido p)`
---

## Package `controle`

### `Dados`
- **Atributos públicos:**
  - `List<Usuario> usuarios`
  - `List<Fornecedor> fornecedores`
  - `List<Produto> produtos`
  - `List<Transportadora> transportadoras`
  - `List<Remessa> remessas`
- **Método `carregar()`:** popula todas as listas com dados fictícios (2 usuários, 3 fornecedores, 21 produtos, 3 transportadoras, 4 remessas completas com pedidos e itens).

### `SistemaControle`
- **Atributos privados:** as cinco listas de dados + `Usuario usuarioLogado`
- **Construtor:** `SistemaControle(Dados d)` – compartilha as referências das listas.
- **Métodos:**
  - `boolean logar(String login, String senha)`: autentica o usuário; se encontrado, define `usuarioLogado` e retorna `true`.
  - `void listar(String tipo)`: exibe os dados conforme o parâmetro (`"usuarios"`, `"fornecedores"`, `"produtos"`, `"transportadoras"`, `"remessas"`, `"todos"`).
  - `void exibirMenu()`: loop principal com opções de listagem, respeitando o perfil do usuário logado (em desenvolvimento).
