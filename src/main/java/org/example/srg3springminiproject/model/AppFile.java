package org.example.srg3springminiproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppFile {
    private String fileName;
    private String fileType;
    private Long fileSize;
}
