package hosung.batch.product.io;

import hosung.database.entity.Product;
import hosung.database.repository.ProductRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class ProductItemWriter implements ItemWriter<Product> {

    private final ProductRepository productRepository;  // JPA Repository 주입


    public ProductItemWriter(EntityManagerFactory entityManagerFactory, ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void write(Chunk<? extends Product> chunk) throws Exception {
        log.info("Writing {} products", chunk.size());
        if (chunk.isEmpty()) {
            log.warn("No products to write");
            return;
        }

        chunk.forEach(product -> {
            log.info("Saving product: {}", product.getName());
            // 여기서 Product 엔티티를 저장하는 로직을 추가
            productRepository.save(product);  // JPA Repository를 통해 저장
        });
        // 실제 데이터베이스에 저장
    }
}
