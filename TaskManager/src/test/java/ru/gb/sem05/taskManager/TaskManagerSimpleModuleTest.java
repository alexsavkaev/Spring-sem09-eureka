package ru.gb.sem05.taskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.gb.sem05.taskManager.model.Task;
import ru.gb.sem05.taskManager.model.TaskStatus;
import ru.gb.sem05.taskManager.repository.TaskRepository;
import ru.gb.sem05.taskManager.services.TaskService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskManagerSimpleModuleTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskService taskService;
	private Task task;
	private Task updatedTask;
	private List<Task> tasks;
	private TaskStatus status;

	@BeforeEach
	void setup() {
		task = new Task();
		updatedTask = new Task();
		tasks = Arrays.asList(new Task(), new Task());
		status = TaskStatus.IN_PROGRESS;
	}

	@Test
	void getAllTasks() {
		// given
		given(taskRepository.findAll()).willReturn(tasks);

		// when
		List<Task> result = taskService.getAllTasks();

		// then
		assertEquals(tasks, result);
	}

	@Test
	void getTaskById() {
		// setup
		Long id = 1L;
		given(taskRepository.findById(id)).willReturn(Optional.of(task));

		// when
		Optional<Task> result = taskService.getTaskById(id);

		// then
		assertTrue(result.isPresent());
		assertEquals(task, result.get());
	}

	@Test
	void saveTask() {
		// given
		given(taskRepository.save(any(Task.class))).willAnswer(invocation -> invocation.getArgument(0));

		// when
		Task result = taskService.saveTask(task);

		// then
		verify(taskRepository).save(task);
		assertEquals(task, result);
	}

	@Test
	void getTasksByStatus() {
		// given
		given(taskRepository.findByStatus(status)).willReturn(tasks);

		// when
		List<Task> result = taskService.getTasksByStatus(status);

		// then
		assertEquals(tasks, result);
	}

	@Test
	void updateTask() {
		// given
		Long id = 1L;
		given(taskRepository.findById(id)).willReturn(Optional.of(task));
		given(taskRepository.save(any(Task.class))).willAnswer(invocation -> invocation.getArgument(0));

		// when
		Task result = taskService.updateTask(id, updatedTask);

		// then
		verify(taskRepository).findById(id);
		verify(taskRepository).save(updatedTask);
		assertEquals(updatedTask, result);
	}

	@Test
	void updateTaskNotFound() {
		// given
		Long id = 1L;
		given(taskRepository.findById(id)).willReturn(Optional.empty());

		// then
		assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(id, updatedTask));
	}

	@Test
	void deleteTask() {
		// given
		Long id = 1L;

		// when
		taskService.deleteTask(id);

		// then
		verify(taskRepository).deleteById(id);
	}
}