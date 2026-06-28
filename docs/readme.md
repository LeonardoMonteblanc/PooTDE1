### Orientações

* `/docs`: documentação do trabalho, diagramas UML e modelo relacional.
* `/src/banco`: classes de conexão (DAO).
* `/src/controle`: orquestração do sistema, regras de negócio, gerenciamento de sessão.
* `/src/modelo`: entidades do domínio, heranças e enums de status/acesso.
* `/src/exceptions`: exceções customizadas

<<<<<<< HEAD
CONSULTAS OK
CADASTROS OK
FAZER PEDIDO E REMESSA COM PROBLEMAS


Senha root: (wittgenstein + indepencia do haiti)
TratadoLogicoFilosofico1804@
=======
>>>>>>> 761df1cd2b2d97fb452df9acb4c574bc9add2d9d
---

### Login de teste

```text
# Perfil Administrador
login: admin
senha: 123

# Perfil Cliente
login: cliente
senha: 123

```

### Fluxo de negócio e Árvore do Projeto

```text
src/
├── banco/
│   ├── ConexaoBD.java           
│   ├── FornecedorDAO.java        
│   ├── ItemPedidoDAO.java
│   ├── PedidoDAO.java
│   ├── ProdutoDAO.java
│   ├── RemessaDAO.java
│   ├── TransportadoraDAO.java
│   └── UsuarioDAO.java
├── controle/
│   ├── io/
│   │   ├── ConsoleOutput.java 
│   │   └── Leitor.java
│   ├── ControleCadastro.java
│   ├── ControleExclusao.java
│   ├── ControleListagem.java
│   ├── Main.java
│   └── MenuPrincipal.java
├── exceptions/
│   ├── CodigoDuplicadoException.java
│   ├── EstoqueException.java
│   └── SistemaException.java
└── modelo/
    ├── enums/
    │   ├── NivelAcesso.java
    │   └── StatusPedido.java
    ├── Carrinho.java
    ├── Fornecedor.java
    ├── ItemPedido.java
    ├── Pedido.java
    ├── Pessoa.java (abstrata)
    ├── Produto.java
    ├── Remessa.java
    ├── Transportadora.java
    └── Usuario.java

```

![](https://github.com/LeonardoMonteblanc/PooTDE1/blob/master/docs/diagram.png)

