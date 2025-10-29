package com.claudiojccoimbra.catalogo.controller.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AvaliacaoRequest(
        @NotNull Long filmeId,
        @Email @NotBlank String autorEmail,
        @Min(0) @Max(10) @NotNull Integer nota,
        @Size(max = 500) String comentario,
        @PastOrPresent @NotNull LocalDate data
) { }
