package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HabitLog {
    private Habit habit;
    private LocalDateTime date;
    private boolean completed;

    public HabitLog(Habit habit, LocalDateTime  date, boolean completed) {
        this.habit = habit;
        this.date = date;
        this.completed = completed;
    }
}
