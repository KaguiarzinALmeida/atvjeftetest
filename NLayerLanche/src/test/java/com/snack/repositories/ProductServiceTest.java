package com.snack.repositories;

import com.snack.entities.Product;
import com.snack.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.*;
import java.io.IOException;
import java.util.Comparator;


public class ProductServiceTest {
    private ProductService productService;
    private Path tempPath;

    @BeforeEach
    public void setUp() throws IOException {
        tempPath = Files.createTempDirectory("productServiceTest");
        productService = new ProductService(){
            {

                this.filePath = tempPath.toString() + FileSystems.getDefault().getSeparator();
            }
        };
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(tempPath).sorted(Comparator.reverseOrder()).forEach(path -> path.toFile().delete());
    }

    @Test
    public void SalvarUmProdutoComImagemValida() throws IOException {
        Path tempImage = Files.createTempFile( "imagem", ".jpg");
        Product product = new Product();
        product.setId(56);
        product.setImage(tempImage.toString());

        boolean success = productService.save(product);

        Path destino = tempPath.resolve("56.jpg");
        Assertions.assertTrue(success);
        Assertions.assertTrue(Files.exists(destino));
        Assertions.assertEquals(destino.toString(), product.getImage());
    }

    @Test
    public void SalvarUmProdutoComImagemInexistente() throws IOException {
        Product product = new Product();
        product.setId(56);

        Path ImagemNaoExiste = Paths.get("ImagemNaoExiste.jpg");
        product.setImage(ImagemNaoExiste.toString());

        boolean success = productService.save(product);

        Assertions.assertFalse(success);
        Path destino = tempPath.resolve("56.jpg");
        Assertions.assertFalse(Files.exists(destino));
    }

    @Test
    public void AtualizarUmProdutoExistente() throws IOException {

        Product product = new Product();
        product.setId(103);

        Path originalImage = Files.createTempFile("original", ".png");
        product.setImage(originalImage.toString());
        Assertions.assertTrue(productService.save(product));

        Path updatedImage = Files.createTempFile("updated", ".png");
        Files.writeString(updatedImage, "nova imagem de teste");
        product.setImage(updatedImage.toString());

        productService.update(product);

        Path expectedPath = tempPath.resolve("103.png");

        Assertions.assertTrue(Files.exists(expectedPath));

        String content = Files.readString(expectedPath);
        Assertions.assertEquals("nova imagem de teste", content);
    }

    @Test
    public void RemoverUmProdutoExistente() throws IOException {
        int productId = 103;
        Path imagePath = tempPath.resolve("103.jpg");

        Files.createFile(imagePath);
        Assertions.assertTrue(Files.exists(imagePath));

        productService.remove(productId);

        Assertions.assertFalse(Files.exists(imagePath));
    }

    @Test
    public void ObterCaminhoDaImagemPorId() throws IOException {
        int productId = 103;
        Path imagePath = tempPath.resolve(productId + ".jpg");

        Files.createFile(imagePath);

        String returned = productService.getImagePathById(productId);
        Assertions.assertEquals(imagePath.toAbsolutePath().toString(), returned);
    }
}

