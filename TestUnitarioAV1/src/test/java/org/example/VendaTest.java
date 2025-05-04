package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class VendaTest {
    @Test
    public void TestarVendaQuantidadeMenorQueEstoqueDisponível() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Venda venda = new Venda(produto, 3);
        boolean resultado = venda.realizarVenda();

        assertEquals(4, produto.getEstoque());
    }

    @Test
    public void TestarVendaQuantidadeIgualQueEstoqueDisponível() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Venda venda = new Venda(produto, 7);
        boolean resultado = venda.realizarVenda();

        assertEquals(0, produto.getEstoque());
    }

    @Test
    public void TestarVendaQuantidadeMaiorQueEstoqueDisponível() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Venda venda = new Venda(produto, 10);
        boolean resultado = venda.realizarVenda();

        assertFalse(resultado);
        assertEquals(7, produto.getEstoque());
    }

    @Test
    public void TestarCalculoTotalVendaCorretamente() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Venda venda = new Venda(produto, 4);
        boolean resultado = venda.realizarVenda();

        assertTrue(resultado);
        assertEquals(6000.00, venda.getTotalVenda());
    }

    @Test
    public void TestarDiminuicaoEstoqueAposVenda() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Venda venda = new Venda(produto, 4);
        boolean resultado = venda.realizarVenda();

        assertTrue(resultado);
        assertEquals(3, produto.getEstoque());

        produto.aumentarEstoque(10);
        assertEquals(13, produto.getEstoque());
    }

    @Test
    public void TestarAumentoEstoqueAposVenda() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Venda venda = new Venda(produto, 4);
        boolean resultado = venda.realizarVenda();

        assertTrue(resultado);
        assertEquals(3, produto.getEstoque());
    }

    @Test
    public void TestarRealizarVendaProdutoQueNaoExiste() {
        Venda venda = new Venda(null, 4);
        boolean resultado = venda.realizarVenda();

        assertFalse(resultado);
    }

    @Test
    public void TestarCriacaoVendaQuantidadeNegativa() {
       Produto produto = new Produto("Ps4", 1500.00, 7);

        assertThrows(IllegalArgumentException.class, () -> {new Venda(produto, -1);
        });
    }

    @Test
    public void TestarAlteracaoEstoqueAposTentativaVendaComEstoqueInsuficiente() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Venda venda = new Venda(produto, 10);
        boolean resultado = venda.realizarVenda();

        assertFalse(resultado);
        assertEquals(7, produto.getEstoque());
    }

    @Test
    public void TestarCriacaoVariosProdutosRealizarVendasComEstoqueCompartilhado() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        Produto produto2 = new Produto("PsVita", 800.00, 8);

        Venda venda = new Venda(produto, 2);
        Venda venda2 = new Venda(produto2, 3);

        boolean resultado = venda.realizarVenda();
        boolean resultado2 = venda2.realizarVenda();

        assertTrue(resultado);
        assertTrue(resultado2);

        assertEquals(5, produto.getEstoque());
        assertEquals(5, produto2.getEstoque());
    }

    @Test
    public void TestarCalcularTotalVendaQuandoPrecoProdutoAlteradoAntesDaVenda() {
        Produto produto = new Produto("Ps4", 1500.00, 7);
        produto.setPreco(1369.00);

        Venda venda = new Venda(produto, 3);
        boolean resultado = venda.realizarVenda();

        assertTrue(resultado);
        assertEquals(4107.00, venda.getTotalVenda());
    }

    @Test
    public void TestarComportamentoVendaQuandoEstoqueInicialZero() {
        Produto produto = new Produto("Ps4", 1500.00, 0);

        Venda venda = new Venda(produto, 3);
        boolean resultado = venda.realizarVenda();

        assertFalse(resultado);
        assertEquals(0, produto.getEstoque());
        assertEquals(0.0, venda.getTotalVenda());
    }

    @Test
    public void TestarAumentoEstoqueAposReposicaoVerificarVendaBemSucedidaPosteriormente() {
        Produto produto = new Produto("Ps4", 1500.00, 0);

        Venda venda = new Venda(produto, 3);
        boolean resultado = venda.realizarVenda();

        assertFalse(resultado);
        assertEquals(0, produto.getEstoque());

        produto.aumentarEstoque(6);
        Venda venda2 = new Venda(produto, 3);
        boolean resultado2 = venda2.realizarVenda();

        assertTrue(resultado2);
        assertEquals(3, produto.getEstoque());
        assertEquals(4500.00, venda2.getTotalVenda());
    }

}
