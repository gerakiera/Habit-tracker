package org.example.service;

import org.example.model.Habit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitService {
    private final Map<String, List<Habit>> userHabits = new HashMap<>();

    public String createHabit(String userEmail, String title, String description, String frequent) {
        Habit habit = new Habit(title, description, frequent);
        userHabits.putIfAbsent(userEmail, new ArrayList<>());
        userHabits.get(userEmail).add(habit);

        return "Habit added.";
    }

    public String updateHabit(String userEmail, String oldTitle, String newTitle, String newDescription,
                               String newFrequency) {
        List<Habit> habits = userHabits.get(userEmail);

        if(habits != null) {
            for(Habit habit : habits) {
                if(habit.getTitle().equals(oldTitle)) {
                    habit.setTitle(newTitle);
                    habit.setDescription(newDescription);
                    habit.setFrequent(newFrequency);

                    return "Habit renewed.";
                }
            }
        }
        return "Habit is not found.";
    }

    public String deleteHabit(String userEmail, String title) {
        List<Habit> habits = userHabits.get(userEmail);
        if(habits != null) {
            if(habits.removeIf(habit -> habit.getTitle().equals(title))) {
                return "Habit removed.";
            }
        }
        return "Habit is not found.";
    }

    public List<Habit> getHabits(String userEmail) {
        return userHabits.getOrDefault(userEmail, new ArrayList<>());
    }

    public List<Habit> filterHabitsByFrequency(String userEmail, String frequent) {
        List<Habit> filteredHabits = new ArrayList<>();
        List<Habit> habits = userHabits.get(userEmail);
        if (habits != null) {
            for (Habit habit : habits) {
                if (habit.getFrequent().equals(frequent)) {
                    filteredHabits.add(habit);
                }
            }
        }
        return filteredHabits;
    }

    public List<Habit> filterHabitsByCreationDate(String userEmail, LocalDate startDate, LocalDate endDate) {
        List<Habit> filteredHabits = new ArrayList<>();
        List<Habit> habits = userHabits.get(userEmail);
        if (habits != null) {
            for (Habit habit : habits) {
                LocalDate createdAt = habit.getCreatedAt();
                if (createdAt.isAfter(startDate) && createdAt.isBefore(endDate)) {
                    filteredHabits.add(habit);
                }
            }
        }
        return filteredHabits;
    }
}
