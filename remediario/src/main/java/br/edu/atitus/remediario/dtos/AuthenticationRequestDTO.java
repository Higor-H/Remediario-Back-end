package br.edu.atitus.remediario.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(@NotBlank String email, @NotBlank String password) {
}