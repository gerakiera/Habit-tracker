package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Habit {
    private String title;
    private String description;
    private String frequent;
    LocalDate createdAt;

    public Habit(String title, String description, String frequent) {
        this.title = title;
        this.description = description;
        this.frequent = frequent;
        this.createdAt = LocalDate.now();
    }
}
