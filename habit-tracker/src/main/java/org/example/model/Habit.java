package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Habit {
    private String title;
    private String description;
    private String frequent;
    LocalDateTime createdAt;

    public Habit(String title, String description, String frequent) {
        this.title = title;
        this.description = description;
        this.frequent = frequent;
        this.createdAt = LocalDateTime.now();
    }
}
