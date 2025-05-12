package com.snack.repositories;

import com.snack.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProductRepositoryTest {

    private ProductRepository repository;
    private Product productPadrao;
    private Product productPadrao2;
    private Product productPadrao3;

    @BeforeEach
    public void SetUp() {
        repository = new ProductRepository();
        productPadrao = new Product(1, "Ps5", 3000, "https://l1nk.dev/wtchg");
        productPadrao2 = new Product(2,"controle", 250,"https://l1nk.dev/kNnMP");
        productPadrao3 = new Product(3,"fone", 150,"https://acesse.one/NsliM");

    }

    @Test
    public void VerificarSeProdutoAdicionadoCorretamenteAoRepositório() {
        repository.append(productPadrao);
        Assertions.assertTrue(repository.exists(1));

    }

    @Test
    public void VerificarsePossívelRecuperarProdutoUsandoSeuId() {
        repository.append(productPadrao);

        Product encontrado = repository.getById(1);

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Ps5", encontrado.getDescription());
        Assertions.assertEquals(3000, encontrado.getPrice());
        Assertions.assertEquals("https://l1nk.dev/wtchg", encontrado.getImage());
    }

    @Test
    public void ConfirmarSeUmProdutoExisteNoRepositório(){
        repository.append(productPadrao);

        boolean product = repository.exists(1);

        Assertions.assertTrue(product);
    }

    @Test
    public void TestarSeUmProdutoRemovidoCorretamenteDoRepositório(){
        repository.append(productPadrao);
        repository.remove(1);

        Assertions.assertFalse(repository.exists(1));
    }

    @Test
    public void VerificarSeUmProdutoEAtualizadoCorretamente(){
        repository.append(productPadrao);


        Product novoProduto = new Product(1, "Ps4", 1500, "https://acesse.one/cXRXQ");
        repository.update(1, novoProduto);

        Product atualizado = repository.getById(1);
        Assertions.assertEquals("Ps4", atualizado.getDescription());
        Assertions.assertEquals(1500, atualizado.getPrice());
        Assertions.assertEquals("https://acesse.one/cXRXQ", atualizado.getImage());
    }

    @Test
    public void TestarSetodosOsProdutosArmazenadosSaoRecuperadosCorretamente(){
        repository.append(productPadrao);
        repository.append(productPadrao2);
        repository.append(productPadrao3);

        List<Product> todosProdutos = repository.getAll();

        Assertions.assertEquals(3, todosProdutos.size());
        Assertions.assertTrue(todosProdutos.contains(productPadrao));
        Assertions.assertTrue(todosProdutos.contains(productPadrao2));
        Assertions.assertTrue(todosProdutos.contains(productPadrao3));
    }

    @Test
    public void VerificarOComportamentoAoTentarRemoverumProdutoQueNaoExiste(){
        repository.append(productPadrao);
        repository.append(productPadrao2);

        repository.remove(333);
        Assertions.assertFalse(repository.exists(333));
    }
    @Test
    public void TestarOQueAconteceAoTentarAtualizarUmProdutoQueNaoEstanoRepositório(){
        repository.append(productPadrao);

        Exception excecao = Assertions.assertThrows(Exception.class, () -> {
           repository.update(333, productPadrao2);
        });
        Assertions.assertFalse(excecao.getMessage().contains("Produto não existe"));
    }

    @Test
    public void VerificarSeORepositórioAceitaAAdiçãoDeProdutosComIdsDuplicados(){

        Product product1 = new Product(1, "Ps5", 3000, "https://l1nk.dev/wtchg");
        Product product = new Product(1, "copia Ps5", 4000, "https://l1nk.dev/wtchg");

        repository.append(product1);
        repository.append(product);

        //   if (!exists(product.getId())) { So funciona certo se por isso no append do ProductRepository
       //  products.add(product);}

        List<Product> produtos = repository.getAll();
        Assertions.assertEquals(1, produtos.size());
        Assertions.assertEquals("Ps5", produtos.get(0).getDescription());
    }

    @Test
    public void ConfirmarQueRepositórioRetornaUmaListaVaziaAoSerInicializado(){
        List<Product> produtos = repository.getAll();
        Assertions.assertTrue(produtos.isEmpty());
    }

}
