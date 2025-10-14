package com.upgrade.store.api.controller;

import com.upgrade.store.api.dto.request.ProductRequest;
import com.upgrade.store.api.dto.response.ProductResponse;
import com.upgrade.store.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
                            schema = @Schema(implementation = ProductResponse.class)))
    })
    public ResponseEntity<ProductResponse> saveProduct(@Valid @RequestBody ProductRequest request) {
        log.debug("Input data for creating product: {}", request);
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
            @ApiResponse(responseCode = "200", description = "GET",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Product ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long productId
    ) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }
}
