package com.snack.repositories;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

public class ProductApplicationTest {

    private ProductRepository repository;
    private ProductService service;
    private ProductApplication app;

    @BeforeEach
    void setUp() {
        repository = new ProductRepository() {

            private final List<Product> products = new java.util.ArrayList<>();

            @Override
            public List<Product> getAll() {
                return products;
            }

            @Override
            public Product getById(int id) {
                return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
            }

            @Override
            public boolean exists(int id) {
                return products.stream().anyMatch(p -> p.getId() == id);
            }

            @Override
            public void append(Product product) {
                products.add(product);
            }

            @Override
            public void remove(int id) {
                products.removeIf(p -> p.getId() == id);
            }

            @Override
            public void update(int id, Product product) {
                remove(id);
                append(product);
            }
        };

        service = new ProductService() {
            {
                this.filePath = System.getProperty("java.io.tmpdir") + "/";
            }

            @Override
            public boolean save(Product product) {
                return true; // Stub de sucesso
            }
        };

        app = new ProductApplication(repository, service);
    }

    @Test
    public  void testGetAllProducts() {

        Product product1 = new Product();
        product1.setId(1);
        product1.setDescription("Refrigerante");

        Product product2 = new Product();
        product2.setId(2);
        product2.setDescription("Salgadinho");

        repository.append(product1);
        repository.append(product2);

        List<Product> result = app.getAll();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getDescription().equals("Refrigerante")));
        assertTrue(result.stream().anyMatch(p -> p.getDescription().equals("Salgadinho")));
    }

    @Test
    public  void ObterUmProdutoPorIdValido() {

        Product product = new Product();
        product.setId(10);
        product.setDescription("Suco de Laranja");

        repository.append(product);

        Product result = app.getById(10);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals("Suco de Laranja", result.getDescription());
    }

    @Test
    public  void RetornarNuloOuErroAoTentarObterProdutoPorIdInvalido() {
        Product result = app.getById(999);

        assertNull(result);
    }

    @Test
    public void VerificarseUmProdutoExistePorIdValido(){
        Product product = new Product();
        product.setId(20);
        product.setDescription("Chocolate");

        repository.append(product);

        boolean exists = app.exists(20);

        assertTrue(exists);
    }

    @Test
    public void RetornarFalsoAoVerificarAExistenciaDeUmProdutoComIdInvalido(){
        assertFalse(app.exists(-1));
        assertFalse(app.exists(0));
        assertFalse(app.exists(999));
    }



}
