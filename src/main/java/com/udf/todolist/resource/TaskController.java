package com.udf.todolist.resource;

import com.udf.todolist.model.Task;
import com.udf.todolist.service.TaskService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public List<Task> getAllTasks() {
    return taskService.findAll();
  }

  @PostMapping
  public Task createTask(@RequestBody final Task task) {
    return taskService.save(task);
  }

  @PutMapping("/{id}/complete")
  public Task completeTask(@PathVariable final Long id) {
    return taskService.completeTask(id);
  }

  @DeleteMapping("/{id}")
  public void deleteTask(@PathVariable final Long id) {
    taskService.delete(id);
  }

  @GetMapping("/search")
  public List<Task> searchTasks(@RequestParam final String keyword) {
    return taskService.filterTasks(taskService.searchByKeyword(keyword));
  }

  @GetMapping("/completed")
  public List<Task> getCompletedTasks() {
    return taskService.filterTasks(Task::isCompleted);
  }
}
