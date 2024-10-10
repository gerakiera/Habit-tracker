package org.example;

import org.example.model.Habit;
import org.example.service.HabitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class HabitServiceTest {

    private HabitService habitService;

    @BeforeEach
    public void setUp() {
        habitService = new HabitService();
    }

    @Test
    public void testCreateHabit() {
        String result = habitService.createHabit("kris@example.com", "sport",
                "goal: to exercise regularly", "daily");
        assertThat(result).isEqualTo("Habit added.");
        List<Habit> habits = habitService.getHabits("kris@example.com");
        assertThat(habits).hasSize(1);
        assertThat(habits.get(0).getTitle()).isEqualTo("sport");
    }

    @Test
    public void testGetHabits() {
        habitService.createHabit("kris@example.com", "sport",
                "goal: to exercise regularly", "daily");
        habitService.createHabit("kris@example.com", "read", "read a book", "daily");
        List<Habit> habits = habitService.getHabits("kris@example.com");
        assertThat(habits).hasSize(2);
    }

    @Test
    public void testUpdateHabit() {
        habitService.createHabit("kris@example.com", "sport",
                "goal: to exercise regularly", "daily");
        String result = habitService.updateHabit("kris@example.com", "sport" ,"read",
                "read a book", "daily");
        assertThat(result).isEqualTo("Habit renewed.");
        List<Habit> habits = habitService.getHabits("kris@example.com");
        assertThat(habits).hasSize(1);
        assertThat(habits.get(0).getTitle()).isEqualTo("read");
    }

    @Test
    public void testDeleteHabit() {
        habitService.createHabit("kris@example.com", "sport",
                "goal: to exercise regularly", "daily");
        String result = habitService.deleteHabit("kris@example.com", "sport");
        assertThat(result).isEqualTo("Habit removed.");
        List<Habit> habits = habitService.getHabits("kris@example.com");
        assertThat(habits).isEmpty();
    }
}
