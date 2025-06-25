package hosung.batch.product.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class ProductCsvRow {
    private String boutique;
    private String brand;
    private String sku;
    private double price;
    private Long count;
    private String productSizes;
    private String productTokens;
}
