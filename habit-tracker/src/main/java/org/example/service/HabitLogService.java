package org.example.service;

import org.example.model.Habit;
import org.example.model.HabitLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitLogService {
    private final Map<Habit, List<HabitLog>> habitLogs = new HashMap<>();

    public String logHabit(Habit habit, LocalDate date, boolean completed) {
        HabitLog log = new HabitLog(habit, date, completed);
        habitLogs.putIfAbsent(habit, new ArrayList<>());
        habitLogs.get(habit).add(log);
        return "Log for habit " + habit.getTitle() + " added";
    }


}
