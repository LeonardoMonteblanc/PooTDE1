### Orientações

- `/docs`: documentação do trabalho
- `/code`: projeto Java
  - `/src`: código-fonte
  - `/modelo`: entidades do domínio
  - `/controle`: regras e fluxo do sistema

---
### Status do desenvolvimento

- [x] Ajustar listagem
- [x] Ajustar menu e separar responsabilidades
- [x] Implementar cadastro por input
- [x] Implementar pesquisa por código ou texto
- [x] Ajustar permissões de acesso
- [ ] Implementar exclusão por input
- [ ] Implementar alteração por input
- [ ] Implementar criação de remessa/pedido

---
### Login de teste

```
login: leo
senha: 123

login: vitor
senha: 123
```

---
### Fluxo de negócio
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
```
Usuário seleciona produto
-> sistema cria/adiciona ItemPedido
-> ItemPedido compõe Pedido
-> Pedido compõe Remessa
```

---
### Estrutura do domínio

#### modelo
- `Pessoa` (base de usuário, fornecedor, transportadora)
- `Usuario` (nome, login, senha, nível de acesso)
- `Fornecedor`
- `Transportadora`
- `Produto` (código, descrição, preço, fornecedores)
- `ItemPedido` (produto, quantidade)
- `Pedido` (lista de itens)
- `Remessa` (data, transportadora, cliente, pedidos)

#### controle
- `Dados`: carga inicial de dados em memória
- `SistemaControle`: orquestra login, sessão e menus
- `MenuControle`: ações por módulo (listar, consultar, cadastrar)
- `Listagem`: impressão formatada das entidades
- `Consulta`: busca por código e texto

#### ordem de chamadas (execução)

**1. Inicialização**
```text
App.main()
  -> Dados.carregarDados()
  -> new SistemaControle(dados)
```

**2. Autenticação**
```text
SistemaControle.validarLogin()
  -> MenuControle.inputLogin()
  -> Login.logar(login, senha)
```

**3. Entrada no sistema**
```text
SistemaControle.menu()
```

**4. Fluxos de produtos**
```text
Listar:
SistemaControle.menu()
  -> MenuControle.gerenciarProdutos()
  -> Listagem.listarProdutos()

Consultar por código:
SistemaControle.menu()
  -> MenuControle.gerenciarProdutos()
  -> Consulta.consultarProdutoPorCodigo(codigo)

Consultar por texto:
SistemaControle.menu()
  -> MenuControle.gerenciarProdutos()
  -> Consulta.consultarProdutosPorTexto(texto)

Cadastrar:
SistemaControle.menu()
  -> MenuControle.gerenciarProdutos()
  -> MenuControle.cadastrarProduto()
  -> new Produto(...)
  -> sistema.getProdutos().add(...)
```

**5. Fluxos de cadastro (demais módulos)**
```text
Fornecedor:
SistemaControle.menu()
  -> MenuControle.gerenciarFornecedores()
  -> MenuControle.cadastrarFornecedor()
  -> new Fornecedor(...)
  -> sistema.getFornecedores().add(...)

Usuário:
SistemaControle.menu()
  -> MenuControle.gerenciarUsuarios()
  -> MenuControle.cadastrarUsuario()
  -> new Usuario(...)
  -> sistema.getUsuarios().add(...)

Transportadora:
SistemaControle.menu()
  -> MenuControle.gerenciarTransportadoras()
  -> MenuControle.cadastrarTransportadora()
  -> new Transportadora(...)
  -> sistema.getTransportadora().add(...)
```

**6. Fluxo de remessas**
```text
SistemaControle.menu()
  -> MenuControle.gerenciarPedidos()
  -> Listagem.listarRemessas()
```
