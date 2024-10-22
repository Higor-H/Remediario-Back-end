package br.edu.atitus.remediario.dtos;

import com.google.firebase.database.annotations.NotNull;

import br.edu.atitus.remediario.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(@NotBlank String email, @NotBlank String password, @NotNull UserRole role) {
}