package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoTest {
    @Test
    public void CriarProdutoValorValido() {
        Produto produto = new Produto("Casaco",40.00, 60);
        assertEquals("Casaco", produto.getNome());
        assertEquals(40.00, produto.getPreco());
        assertEquals(60, produto.getEstoque());
    }

    @Test
    public void CriarProdutoValorNegativo() {
        Produto produto = new Produto("Casaco",40.00, 60);
        assertEquals("Casaco", produto.getNome());
        assertNotEquals(-40.00, produto.getPreco());
        assertEquals(60, produto.getEstoque());
    }

    @Test
    public void CriarProdutoEstoqueNegativo() {
        Produto produto = new Produto("Casaco",40.00, 60);
        assertEquals("Casaco", produto.getNome());
        assertEquals(40.00, produto.getPreco());
        assertNotEquals(-60, produto.getEstoque());
    }

    @Test
    public void AlterarNomeProdutoValorValido() {
        Produto produto = new Produto("Casaco",40.00, 60);
        produto.setNome("Bermuda");
        assertEquals("Bermuda", produto.getNome());

    }

    @Test
    public void AlterarPrecoProdutoValorValido() {
        Produto produto = new Produto("Casaco",40.00, 60);
        produto.setPreco(60.00);
        assertEquals(60.00, produto.getPreco());
    }
    @Test
    public void AlterarEstoqueProdutoValorPositivo() {
        Produto produto = new Produto("Casaco",40.00, 60);
        produto.setEstoque(80);
        assertEquals(80, produto.getEstoque());
    }
    @Test
    public void AlterarEstoqueProdutoValorNegativo() {
        Produto produto = new Produto("Casaco",40.00, 60);
        assertThrows(IllegalArgumentException.class, () -> {
            produto.setPreco(-40.00);
        });
    }
}



