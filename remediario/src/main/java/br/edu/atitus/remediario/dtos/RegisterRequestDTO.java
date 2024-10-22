package br.edu.atitus.remediario.dtos;


import br.edu.atitus.remediario.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(@NotBlank String email, @NotBlank String password, @NotNull UserRole role) {
}