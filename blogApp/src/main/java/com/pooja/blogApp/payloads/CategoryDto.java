package com.pooja.blogApp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private Integer categoryId;

    @NotBlank
    @Size(min=4, message="The title must be of minimum 4 characters")
    private String categoryTitle;

    @NotBlank
    @Size(min=10, max=100, message="The description must have more than 10 characters and less than 100")
    private String CategoryDescription;
}
