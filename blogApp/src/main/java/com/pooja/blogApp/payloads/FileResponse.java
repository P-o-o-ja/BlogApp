package com.pooja.blogApp.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FileResponse {
    private String fileName;
    private String message;
}
