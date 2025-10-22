package com.upgrade.store.api.controller;

import com.upgrade.store.api.dto.request.ProductRequest;
import com.upgrade.store.api.dto.response.ProductResponse;
import com.upgrade.store.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("${app.api.uriPrefix}/product")
@Tag(name = "Product Controller", description = "API for working with products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<ProductResponse> saveProduct(
            @Parameter(description = "ProductRequest", required = true)
            @Valid @RequestBody ProductRequest request) {
        log.info("[API] Input data for creating product with name: [{}]", request.name());

        ProductResponse productResponse = productService.saveProduct(request);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/{productId}",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get product",
            description = "Get product by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found by SKU",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product ID"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Product ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long productId
    ) {
        log.info("[API] Fetching product by ID [{}]", productId);

        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping(
            value = "/bySku/{sku}",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get product", description = "Get product by SKU")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Product found by SKU",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid sku"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductBySku(
            @Parameter(description = "Product SKU", required = true)
            @PathVariable @NotBlank(message = "Required field") String sku
    ) {
        log.info("[API] Fetching product by SKU [{}]", sku);

        ProductResponse productResponse = productService.getProductBySku(sku);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping(
            value = "/byCategoryId/{categoryId}",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get products", description = "Get all products from a specific category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All products from the category have been received",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid category ID")
    })
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @Parameter(description = "Category ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long categoryId
    ) {
        log.info("[API] Fetching products for category ID [{}]", categoryId);

        List<ProductResponse> productResponses = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All goods received",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class))))
    })
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("[API] Fetching all products");

        List<ProductResponse> allProducts = productService.getAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @PutMapping(
            value = "/{productId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update product", description = "Update the product with new data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product data updated successfully",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No product found for update"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long productId,
            @Parameter()
            @Valid @RequestBody ProductRequest request
    ) {
        log.info("[API] Updating product [{}] with new name [{}]", productId, request.name());

        ProductResponse productResponse = productService.updateProduct(productId, request);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product", description = "Delete product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long productId
    ) {
        log.info("[API] Attempting to delete product with ID [{}]", productId);

        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
