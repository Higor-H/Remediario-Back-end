package br.edu.atitus.remediario.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(@NotBlank String email, @NotBlank String password) {
}