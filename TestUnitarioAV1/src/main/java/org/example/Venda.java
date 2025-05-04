package org.example;

public class Venda {
    private Produto produto;
    private int quantidadeVendida;
    private double totalVenda;

    public Venda(Produto produto, int quantidadeVendida) {
        if (quantidadeVendida < 0) {
        throw new IllegalArgumentException("Quantidade vendida não pode ser negativa");
        }

        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public boolean realizarVenda() {
        if (produto == null) {
            return false;
        }

        if (produto.diminuirEstoque(quantidadeVendida)) {
            totalVenda = produto.getPreco() * quantidadeVendida;
            return true;
        }
        return false;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }
}
