package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HabitLog {
    private Habit habit;
    private LocalDate date;
    private boolean completed;

    public HabitLog(Habit habit, LocalDate  date, boolean completed) {
        this.habit = habit;
        this.date = date;
        this.completed = completed;
    }
}
