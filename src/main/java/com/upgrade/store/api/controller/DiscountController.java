package com.upgrade.store.api.controller;

import com.upgrade.store.api.dto.request.DiscountProductsUpdateRequest;
import com.upgrade.store.api.dto.request.DiscountRequest;
import com.upgrade.store.api.dto.response.DiscountResponse;
import com.upgrade.store.api.dto.response.ProductResponse;
import com.upgrade.store.application.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("${app.api.uriPrefix}/discount")
@Tag(name = "Discount Controller", description = "API for working with discounts")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save discount")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Discount successfully saved",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiscountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
    })
    public ResponseEntity<DiscountResponse> saveDiscount(
            @Parameter(description = "DiscountRequest", required = true)
            @Valid @RequestBody DiscountRequest discountRequest
    ) {
        log.info("[API] Input data for creating discount with percent discount: [{}]", discountRequest.discountPercent());

        DiscountResponse discountResponse = discountService.saveDiscount(discountRequest);
        return new ResponseEntity<>(discountResponse, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/{discountId}",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get discount by ID", description = "Returns information about discount")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiscountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid discount ID"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseEntity<DiscountResponse> getDiscountById(
            @Parameter(description = "Discount ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long discountId
    ) {
        log.info("[API] Fetching discount by ID [{}]", discountId);

        DiscountResponse discountResponse = discountService.getDiscountById(discountId);
        return new ResponseEntity<>(discountResponse, HttpStatus.OK);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all discounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All discounts received",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class))))
    })
    public ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        log.info("[API] Fetching all discounts");

        List<DiscountResponse> discountResponses = discountService.getAllDiscounts();
        return new ResponseEntity<>(discountResponses, HttpStatus.OK);
    }

    @PutMapping(
            value = "/{discountId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update discount", description = "Update discount with new data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Discount data updated successfully",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiscountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Discount not found ")
    })
    public ResponseEntity<DiscountResponse> updateDiscount(
            @Parameter(description = "Discount ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long discountId,
            @Parameter(description = "Data to update", required = true)
            @Valid @RequestBody DiscountRequest discountRequest
    ) {
        log.info("[API] Updating discount with ID [{}]", discountId);

        DiscountResponse discountResponse = discountService.updateDiscount(discountId, discountRequest);
        return new ResponseEntity<>(discountResponse, HttpStatus.OK);
    }

    @PatchMapping(
            value = "/{discountId}/products",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update products assigned to a discount",
            description = """
                    Adds or removes products from an existing discount.
                    This endpoint allows partial updates of product associations
                    without modifying other discount details.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Discount products updated successfully",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiscountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Discount not found ")
    })
    public ResponseEntity<DiscountResponse> updateDiscountProducts(
            @Parameter(description = "Discount ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long discountId,
            @Parameter(description = "Set of product IDs to add or remove from the discount. " +
                    "Both fields are optional but at least one must be provided.",
                    required = true)
            @Valid @RequestBody DiscountProductsUpdateRequest productsUpdateRequest
    ) {
        log.info("[API] Updating products associated with discount ID [{}]", discountId);

        DiscountResponse discountResponse = discountService.updateDiscountProducts(discountId, productsUpdateRequest);
        return new ResponseEntity<>(discountResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{discountId}")
    @Operation(summary = "Delete discount by ID", description = "Deletes a discount by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Discount deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid discount ID"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseEntity<Void> deleteDiscount(
            @Parameter(description = "Discount ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long discountId
    ) {
        log.info("[API] Deleting discount by ID: {}", discountId);

        discountService.deleteDiscount(discountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
