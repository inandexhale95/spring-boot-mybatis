package com.example.quizmybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    private Integer id;
    private String question;
    private Boolean answer;
    private String author;
}
