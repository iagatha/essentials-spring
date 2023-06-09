package com.springboot.essentials.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnimePostRequestBody {

    @NotEmpty(message = "The name cannot be empty")
    private String name;
}
