package com.upgrade.store.api.controller;

import com.upgrade.store.api.dto.response.ImageResponse;
import com.upgrade.store.application.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("${app.api.uriPrefix}/image")
@Tag(name = "Image Controller", description = "API for working with images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(
            value = "/{productId}",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Upload product images",
            description = "Uploads one or more images and associates them with a given product ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Images uploaded successfully",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ImageResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<List<ImageResponse>> saveImages(
            @Parameter(description = "Product ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long productId,

            @Parameter(description = "List of image files (max 25)", required = true)
            @NotEmpty(message = "The collection must not be empty")
            @RequestPart("files") List<MultipartFile> files
    ) {
        log.debug("Uploading {} image(s) for product ID {}", files.size(), productId);
        return new ResponseEntity<>(imageService.saveImages(productId, files), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{productId}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get images for a product",
            description = "Retrieves all images associated with a given product ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Images retrieved successfully",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ImageResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<List<ImageResponse>> getImagesByProductId(
            @Parameter(description = "Product ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long productId
    ) {
        return new ResponseEntity<>(imageService.getImagesByProductId(productId), HttpStatus.OK);
    }

    @DeleteMapping("/{imageId}")
    @Operation(summary = "Delete image by ID", description = "Deletes an image by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Image deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid image ID"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    public ResponseEntity<Void> deleteImage(
            @Parameter(description = "Image ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long imageId
    ) {
        log.info("Deleting photo by ID: {}", imageId);
        imageService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
