package br.edu.atitus.remediario.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(@NotBlank String email, @NotBlank String password, String name) {
}