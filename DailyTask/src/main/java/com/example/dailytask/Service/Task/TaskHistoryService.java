package com.example.dailytask.Service.Task;
import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.Enumration.TaskType;
import com.example.dailytask.Domain.Task;
import com.example.dailytask.Domain.TaskHistory;
import com.example.dailytask.Exception.ResourceNotFoundException;
import com.example.dailytask.Repository.TaskHistoryRepository;
import com.example.dailytask.Repository.TaskRepository;
import com.example.dailytask.Service.Task.Request.TaskEditRequest;
import com.example.dailytask.Service.Task.Response.TaskListResponse;
import com.example.dailytask.Util.AppMessage;
import com.example.dailytask.Util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskHistoryService {

    private TaskRepository taskRepository;
    private TaskHistoryRepository taskHistoryRepository;

    public List<TaskListResponse> getHistoryTasks() {
        return taskHistoryRepository.findAllTaskToday()
                .stream()
                .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
                .collect(Collectors.toList());
    }

    public void changeStatus(Long id, TaskStatus status){
        var task = getTaskHistoryById(id);
        task.setStatus(status);
        taskHistoryRepository.save(task);
    }

    public TaskHistory getTaskHistoryById(Long id) {
        return taskHistoryRepository.findById(id).orElse(null);
    }
    public void save(TaskHistory taskHistory) {
        taskHistoryRepository.save(taskHistory);
    }

    public void deleteByID(Long id){
        TaskHistory taskHistory = getTaskHistoryById(id);
        taskHistoryRepository.delete(taskHistory);
    }

    public TaskEditRequest showEditById(Long id){
        TaskHistory taskHistory = getTaskHistoryById(id);
        return taskToTaskEditRequest(taskHistory);
    }
    public void edit(TaskEditRequest request, Long id) throws Exception{
        var taskInDb = getTaskHistoryById(id);

        taskInDb.setStart(AppUtil.mapper.map(request.getStart(), LocalDateTime.class));
        taskInDb.setEnd(AppUtil.mapper.map(request.getEnd(), LocalDateTime.class));
        taskInDb.setType(TaskType.valueOf(request.getType()));
        taskInDb.setTitle(request.getTitle());
        taskInDb.setDescription(request.getDescription());
        request.setId(id.toString());
        taskHistoryRepository.save(taskInDb);
    }

    private TaskEditRequest taskToTaskEditRequest(TaskHistory taskHistory){
        var result = new TaskEditRequest();
        result.setTitle(String.valueOf(taskHistory.getTitle()));
        result.setDescription(String.valueOf(taskHistory.getDescription()));
        result.setStart(String.valueOf(taskHistory.getStart()));
        result.setEnd(String.valueOf(taskHistory.getEnd()));
        result.setType(String.valueOf(taskHistory.getType()));
        result.setId(String.valueOf(taskHistory.getId()));
        return result;
    }


    public List<TaskListResponse> findByStartBetween(LocalDateTime startDate, LocalDateTime endDate) {
      return  taskHistoryRepository.findByStartBetween(startDate, endDate)
              .stream()
              .map(e -> AppUtil.mapper.map(e, TaskListResponse.class))
              .collect(Collectors.toList());

    }

    public Map<TaskStatus, Long> countTasksByStatus() {
        Map<TaskStatus, Long> taskCounts = new HashMap<>();

        for (TaskStatus status : TaskStatus.values()) {
            List<TaskHistory> taskHistories = taskHistoryRepository.findByStatus(status);
            long count = taskHistories.size();
            taskCounts.put(status, count);
        }

        return taskCounts;
    }

    public Map<String, Long> countTasksByWeek() {
        Map<String, Long> taskCountsByWeek = new HashMap<>();

        // Lấy danh sách tất cả task
        List<TaskHistory> taskHistories = taskHistoryRepository.findAll();

        // Vòng lặp qua từng task và đếm theo tuần
        for (TaskHistory taskHistory : taskHistories) {
            LocalDateTime start = taskHistory.getStart();
            if (start != null) {
                String week = start.format(DateTimeFormatter.ofPattern("ww-yyyy"));

                // Kiểm tra xem tuần đã tồn tại trong map chưa, nếu chưa thì thêm vào
                taskCountsByWeek.putIfAbsent(week, 0L);

                // Tăng giá trị đếm của tuần đó lên 1
                taskCountsByWeek.put(week, taskCountsByWeek.get(week) + 1);
            }
        }

        return taskCountsByWeek;
    }


    public Map<String, Long> countTasksByMonth() {
        Map<String, Long> taskCountsByMonth = new HashMap<>();

        // Lấy danh sách tất cả task
        List<TaskHistory> taskHistories = taskHistoryRepository.findAll();

        // Vòng lặp qua từng task và đếm theo tháng
        for (TaskHistory taskHistory : taskHistories) {
            LocalDateTime start = taskHistory.getStart();
            if (start != null) {
                String month = start.format(DateTimeFormatter.ofPattern("MM-yyyy"));

                // Kiểm tra xem tháng đã tồn tại trong map chưa, nếu chưa thì thêm vào
                taskCountsByMonth.putIfAbsent(month, 0L);

                // Tăng giá trị đếm của tháng đó lên 1
                taskCountsByMonth.put(month, taskCountsByMonth.get(month) + 1);
            }
        }

        return taskCountsByMonth;
    }

}
