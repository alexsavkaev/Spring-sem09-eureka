package ru.gb.sem05.taskManager.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.sem05.taskManager.model.Task;
import ru.gb.sem05.taskManager.model.TaskStatus;
import ru.gb.sem05.taskManager.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с задачами
 */

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * Получить все задачи
     * @return список задач
     */

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Получить задачу по id
     * @param id идентификатор задачи
     * @return Optional объект задачи
     */

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Сохранить задачу
     * @param task сохраняемая задача
     * @return сохраненная задача
     */

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Получить задачу по статусу
     * @param status искомый статус
     * @return список задач, имеющих искомый статус
     */

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Обновить задачу по id. Изменяет только поля name, description и status.
     * @param id идентификатор задачи
     * @param updatedTask новые данные к обновлению
     * @return обновленная задача
     */

    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task taskToBeUpdated = task.get();
            taskToBeUpdated.setName(updatedTask.getName());
            taskToBeUpdated.setDescription(updatedTask.getDescription());
            taskToBeUpdated.setStatus(updatedTask.getStatus());
            return taskRepository.save(taskToBeUpdated);
        } else {
            throw new IllegalArgumentException("Task not found");
        }
    }

    /**
     * Удаление задачи по id
     * @param id идентификатор задачи
     */

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}

