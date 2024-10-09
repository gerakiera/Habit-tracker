package org.example.service;

import org.example.model.Habit;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitService {
    private final Map<String, List<Habit>> userHabits = new HashMap<>();

    private String createHabit(String userEmail, String title, String description, String frequent) {
        Habit habit = new Habit(title, description, frequent);
        userHabits.putIfAbsent(userEmail, new ArrayList<>());
        userHabits.get(userEmail).add(habit);

        return "Привычка добавлена.";
    }

    private String updateHabit(String userEmail, String oldTitle, String newTitle, String newDescription,
                               String newFrequency) {
        List<Habit> habits = userHabits.get(userEmail);

        if(habits != null) {
            for(Habit habit : habits) {
                if(habit.getTitle().equals(oldTitle)) {
                    habit.setTitle(newTitle);
                    habit.setDescription(newDescription);
                    habit.setFrequent(newFrequency);

                    return "Привычка обновлена.";
                }
            }
        }
        return "Привычка не найдена.";
    }

    private String deleteHabit(String userEmail, String title) {
        List<Habit> habits = userHabits.get(userEmail);
        if(habits != null) {
            if(habits.removeIf(habit -> habit.getTitle().equals(title))) {
                return "Привычка удалена.";
            }
        }
        return "Привычка не найдена.";
    }

    public List<Habit> getHabits(String userEmail) {
        return userHabits.getOrDefault(userEmail, new ArrayList<>());
    }
}
