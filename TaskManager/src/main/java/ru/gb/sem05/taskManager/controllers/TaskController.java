package ru.gb.sem05.taskManager.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.sem05.taskManager.exceptions.TaskNotFoundException;
import ru.gb.sem05.taskManager.model.Task;
import ru.gb.sem05.taskManager.model.TaskStatus;
import ru.gb.sem05.taskManager.services.TaskService;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с задачами
 */

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Получение списка задач
     * @return список задач
     */
    @GetMapping()
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Получение задачи по id
     * @param id идентификатор задачи
     * @return задача
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        return optionalTask.orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    /**
     * Просмотр задач по статусам
     * @param status искомый статус
     * @return список задач
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    /**
     * Обновить статус задачи
     * @param id id изменяемой задачи
     * @param status новый статус в параметрах запроса
     * @return измененная задача
     */
    @PutMapping("/update/status/{id}")
    public Task updateTask(@PathVariable Long id, @RequestParam TaskStatus status) {
        Task updatedTask = taskService.getTaskById(id).get();
        updatedTask.setStatus(status);
        return taskService.updateTask(id, updatedTask);
    }

    /**
     * Обновить описание задачи
     * @param id id изменяемой задачи
     * @param description новое описание в параметрах запроса
     * @return обновленная задача
     */
    @PutMapping("/update/description/{id}")
    public Task updateTask(@PathVariable Long id, @RequestParam String description) {
        Task updatedTask = taskService.getTaskById(id).get();
        updatedTask.setDescription(description);
        return taskService.updateTask(id, updatedTask);
    }

    /**
     * Удалить задачу по id
     * @param id идентификатор задачи
     */
    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    /**
     * Добавить новую задачу
     * @param task Задача в теле запроса
     * @return Добавленная задача
     */
    @PostMapping("/add")
    public Task addTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }
}
