package com.upgrade.store.api.controller;

import com.upgrade.store.application.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            summary = "Upload images for a product",
            description = "Uploads one or more images and associates them with a given product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Images uploaded successfully",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<List<String>> saveImages(
            @Parameter(description = "Product ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long productId,

            @Parameter(description = "List of image files (max 25)", required = true)
            @NotEmpty(message = "The collection must not be empty")
            @RequestPart("files") List<MultipartFile> files) {
        log.debug("Uploading {} image(s) for product ID {}", files.size(), productId);
        return new ResponseEntity<>(imageService.saveImages(productId, files), HttpStatus.CREATED);
    }
}
