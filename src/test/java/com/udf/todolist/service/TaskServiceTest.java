package com.udf.todolist.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.udf.todolist.model.Task;
import com.udf.todolist.repository.TaskRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskServiceTest {

  private TaskRepository taskRepository;
  private TaskService taskService;

  @BeforeEach
  void setup() {
    taskRepository = Mockito.mock(TaskRepository.class);
    taskService = new TaskService(taskRepository);
  }

  @Test
  void shouldCreateTaskSuccessful() {
    Task task = new Task("Estudar Java", "Alta");
    when(taskRepository.save(task)).thenReturn(task);

    Task result = taskService.save(task);

    assertEquals("Estudar Java", result.getDescription());
    assertFalse(result.isCompleted());
  }

  @Test
  void shouldListAllTasksSuccessful() {
    Task t1 = new Task("Estudar FP", "Alta");
    Task t2 = new Task("Criar testes", "Média");
    when(taskRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

    List<Task> tasks = taskService.findAll();

    assertEquals(2, tasks.size());
  }

  @Test
  void shouldMarkTaskAsCompletedSuccessful() {
    Task task = new Task("Fazer relatório", "Alta");
    task.setId(1L);

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(taskRepository.save(task)).thenReturn(task);

    Task result = taskService.completeTask(1L);

    assertTrue(result.isCompleted());
  }

  @Test
  void ShouldFilterByKeywordSuccessful() {
    Task t1 = new Task("Estudar programação funcional", "Alta");
    Task t2 = new Task("Comprar pão", "Baixa");

    when(taskRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

    List<Task> results = taskService.filterTasks(taskService.searchByKeyword("programação"));

    assertEquals(1, results.size());
    assertEquals("Estudar programação funcional", results.getFirst().getDescription());
  }
}
