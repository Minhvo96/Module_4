package com.example.dailytask.Service.Task;
import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.TaskHistory;
import com.example.dailytask.Exception.ResourceNotFoundException;
import com.example.dailytask.Repository.TaskHistoryRepository;
import com.example.dailytask.Repository.TaskRepository;
import com.example.dailytask.Service.Task.Response.TaskListResponse;
import com.example.dailytask.Util.AppMessage;
import com.example.dailytask.Util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskHistoryService {

    private TaskRepository taskRepository;
    private TaskHistoryRepository taskHistoryRepository;

    public List<TaskListResponse> getHistoryTasks() {
        return taskHistoryRepository.findAll()
                .stream()
                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
                .collect(Collectors.toList());
    }

    public void changeStatus(Long id, TaskStatus status){
        var task = getTaskHistoryById(id);
        task.setStatus(status);
        taskHistoryRepository.save(task);
    }

//    public TaskHistory findById(Long id){
//        return taskHistoryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        String.format(AppMessage.ID_NOT_FOUND, "Task", id)));
//    }

    public TaskHistory getTaskHistoryById(Long id) {
        return taskHistoryRepository.findById(id).orElse(null);
    }
    public void save(TaskHistory taskHistory) {
        taskHistoryRepository.save(taskHistory);
    }
}
