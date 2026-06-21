-- 1. Usuários
INSERT INTO usuario (login, senha, nivel_acesso, nome) VALUES 
('admin', '123', 'ADMIN', 'Administrador Sistema'),
('cliente1', '123', 'CLIENTE', 'João da Silva');

-- 2. Transportadoras
INSERT INTO transportadora (nome, taxa_fixa) VALUES 
('Correios Expresso', 15.00),
('JadLog Logística', 20.00),
('Azul Cargo', 35.00);

-- 3. Fornecedores
INSERT INTO fornecedor (nome, cnpj) VALUES 
('Tech Eletro', '11.111.111/0001-11'),
('Casa Lar', '22.222.222/0001-22'),
('Móveis Design', '33.333.333/0001-33');

-- 4. Produtos (7 por fornecedor = 21 produtos)
-- Fornecedor 1 (Tech Eletro) -> IDs 1-7
-- Fornecedor 2 (Casa Lar) -> IDs 8-14
-- Fornecedor 3 (Móveis Design) -> IDs 15-21
INSERT INTO produto (descricao, preco, estoque) VALUES 
('Smartphone X', 2000.00, 10), ('Notebook Y', 4000.00, 5), ('Tablet Z', 1200.00, 20), ('Fone Bluetooth', 300.00, 50), ('Carregador', 80.00, 100), ('Monitor 24', 900.00, 15), ('Teclado Mecânico', 250.00, 30),
('Liquidificador', 150.00, 40), ('Batedeira', 250.00, 25), ('Cafeteira', 200.00, 35), ('Microondas', 500.00, 10), ('Ferro de Passar', 100.00, 45), ('Aspirador', 400.00, 15), ('Ventilador', 120.00, 60),
('Cadeira Office', 600.00, 12), ('Mesa Computador', 550.00, 8), ('Sofá 3 lugares', 1500.00, 5), ('Estante Livros', 300.00, 20), ('Rack TV', 450.00, 15), ('Puff Couro', 150.00, 40), ('Abajur', 80.00, 50);

-- 5. Associando Produtos aos Fornecedores (produto_fornecedor)
INSERT INTO produto_fornecedor (produto_id, fornecedor_id) VALUES 
(1,1), (2,1), (3,1), (4,1), (5,1), (6,1), (7,1),
(8,2), (9,2), (10,2), (11,2), (12,2), (13,2), (14,2),
(15,3), (16,3), (17,3), (18,3), (19,3), (20,3), (21,3);

-- 6. Remessas (4 remessas)
INSERT INTO remessa (transportadora_id) VALUES (1), (2), (3), (1);

-- 7. Pedidos (4 pedidos)
-- Pedido 1 e 2 vinculados ao cliente 2, Remessas 1 e 2
INSERT INTO pedido (usuario_id, remessa_id, status_pedido) VALUES 
(2, 1, 'PENDENTE'), 
(2, 2, 'ENVIADO'),
(2, 3, 'PENDENTE'),
(2, 4, 'CANCELADO');

-- 8. Itens dos Pedidos
INSERT INTO item_pedido (pedido_id, produto_id, quantidade) VALUES 
(1, 1, 1), (1, 5, 2), -- Pedido 1: Smartphone + 2 Carregadores
(2, 8, 1), (2, 12, 1), -- Pedido 2: Liquidificador + Ferro
(3, 15, 1), -- Pedido 3: Cadeira Office
(4, 21, 1); -- Pedido 4: Abajur