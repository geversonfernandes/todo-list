package com.udf.todolist.service;

import com.udf.todolist.model.Task;
import com.udf.todolist.repository.TaskRepository;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> filterTasks(final Predicate<Task> filter) {
    return taskRepository.findAll().stream().filter(filter).collect(Collectors.toList());
  }

  public Predicate<Task> searchByKeyword(final String keyword) {
    return task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase());
  }

  public List<Task> findAll() {
    return taskRepository.findAll().stream()
        .map(
            task -> {
              String desc = task.getDescription().toUpperCase();
              task.setDescription(desc);
              return task;
            })
        .collect(Collectors.toList());
  }

  public Task save(final Task task) {
    return taskRepository.save(task);
  }

  public void delete(final Long id) {
    taskRepository.deleteById(id);
  }

  public Task completeTask(final Long id) {
    return taskRepository
        .findById(id)
        .map(
            task -> {
              task.setCompleted(true);
              return taskRepository.save(task);
            })
        .orElseThrow(() -> new RuntimeException("Task not found"));
  }
}
