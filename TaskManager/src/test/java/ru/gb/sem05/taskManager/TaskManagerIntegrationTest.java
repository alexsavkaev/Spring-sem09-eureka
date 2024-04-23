package ru.gb.sem05.taskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.sem05.taskManager.model.Task;
import ru.gb.sem05.taskManager.model.TaskStatus;
import ru.gb.sem05.taskManager.repository.TaskRepository;
import ru.gb.sem05.taskManager.services.TaskService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class TaskManagerIntegrationTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setup() {
        createTasks();
        taskRepository.deleteAll();
    }

    private void createTasks() {
        task1 = new Task();
        task1.setName("Task 1");
        task1.setDescription("Description 1");

        task2 = new Task();
        task2.setName("Task 2");
        task2.setDescription("Description 2");
    }

    @Test
    public void saveTask_shouldSaveTask() {
        // given
        given(taskRepository.save(task1)).willReturn(task1); // Настройка мока taskRepository

        // when
        Task savedTask = taskService.saveTask(task1);

        // then
        assertThat(savedTask).isEqualTo(task1);
    }


    @Test
    public void getAllTasks_shouldReturnAllTasks() {
        // given
        given(taskRepository.findAll()).willReturn(Arrays.asList(task1, task2)); // Настройка мока taskRepository

        // when
        List<Task> tasks = taskService.getAllTasks();

        // then
        assertThat(tasks).hasSize(2);
        assertThat(tasks).contains(task1, task2);
    }

    @Test
    public void getTasksByStatus_shouldReturnTasksByStatus() {
        // given
        given(taskRepository.findByStatus(TaskStatus.IN_PROGRESS)).willReturn(Arrays.asList(task1, task2)); // Настройка мока taskRepository

        // when
        List<Task> tasks = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS);

        // then
        assertThat(tasks).hasSize(2);
        assertThat(tasks).contains(task1, task2);
    }

    @Test
    public void getTaskById_shouldReturnTaskById() {
        // given
        given(taskRepository.findById(1L)).willReturn(Optional.of(task1)); // Настройка мока taskRepository

        // when
        Optional<Task> task = taskService.getTaskById(1L);

        // then
        assertThat(task).isPresent();
        assertThat(task.get()).isEqualTo(task1);
    }
}