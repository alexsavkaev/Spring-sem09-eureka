package ru.gb.sem05.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.sem05.taskManager.model.Task;
import ru.gb.sem05.taskManager.model.TaskStatus;

import java.util.List;

/**
 * Репозиторий задач
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * Найти задачи по статусу
     * @param status искомый статус
     * @return список задач
     */

    List<Task> findByStatus(TaskStatus status);

}
