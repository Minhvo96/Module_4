package com.example.dailytask.Service.Task;
import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.Enumration.TaskType;
import com.example.dailytask.Domain.Task;
import com.example.dailytask.Domain.TaskHistory;
import com.example.dailytask.Exception.ResourceNotFoundException;
import com.example.dailytask.Repository.TaskHistoryRepository;
import com.example.dailytask.Repository.TaskRepository;
import com.example.dailytask.Service.Task.Request.TaskSaveRequest;
import com.example.dailytask.Service.Task.Response.TaskListResponse;
import com.example.dailytask.Util.AppMessage;
import com.example.dailytask.Util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskHistoryService taskHistoryService;
    private final TaskHistoryRepository taskHistoryRepository;

    public List<TaskListResponse> getTasks() {
        return taskHistoryRepository.findAllTaskToDay()
                .stream()
                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
                .collect(Collectors.toList());
    }

//    public void create(TaskSaveRequest request){
//        var task = AppUtil.mapper.map(request, Task.class);
//        taskRepository.save(task);
//    }

    public void create(TaskSaveRequest request){
        var taskHistory = AppUtil.mapper.map(request, TaskHistory.class);
        if (Objects.equals(request.getType(), TaskType.DAILY.toString())){
            var task = AppUtil.mapper.map(request, Task.class);
            task = taskRepository.save(task);

            LocalDate now = LocalDateTime.now().toLocalDate();
            taskHistory.setTask(task);
            taskHistory.setStart(LocalDateTime.of(now, task.getStart()));
            taskHistory.setEnd(LocalDateTime.of(now, task.getEnd()));
        }
        taskHistoryRepository.save(taskHistory);
    }

    public void changeStatus(Long id, TaskStatus status){
        var task = findById(id);
        task.setStatus(status);
        taskHistoryRepository.save(task);
    }

    public TaskHistory findById(Long id){
        return taskHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AppMessage.ID_NOT_FOUND, "Task", id)));
    }

    public void updateTaskStatus(Long id, TaskStatus status) {
        TaskHistory taskHistory = taskHistoryService.getTaskHistoryById(id);
        if (taskHistory != null) {
            taskHistory.setStatus(status);
            taskHistoryService.save(taskHistory);

        }
    }
    public void createTaskHistory(Task task, TaskStatus oldStatus, TaskStatus newStatus) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(task);
        taskHistory.setStatus(oldStatus);
        taskHistory.setStatus(newStatus);
        taskHistory.setType(TaskType.DAILY);
        taskHistoryService.save(taskHistory);
    }

    public void deleteById(Long id){
        Task task = findByID(id);
        taskRepository.delete(task);
    }

//    public void delete(Long taskId) {
//        taskRepository.deleteById(taskId);
//    }

//    public Task findByID(Long id) {
//        return taskRepository.findById(id).orElse(null);
//    }

    public Task findByID(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "Task", id)));
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            Task existingTask = taskOptional.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStart(updatedTask.getStart());
            existingTask.setEnd(updatedTask.getEnd());

            return taskRepository.save(existingTask);

        } else {
            return null;
        }
    }
}
