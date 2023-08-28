//package com.example.dailytask.Service.Task;
//import com.example.dailytask.Domain.Enumration.TaskStatus;
//import com.example.dailytask.Domain.Enumration.TaskType;
//import com.example.dailytask.Domain.Task;
//import com.example.dailytask.Domain.TaskHistory;
//import com.example.dailytask.Repository.TaskHistoryRepository;
//import com.example.dailytask.Repository.TaskRepository;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class TaskRenewalService {
//
//    private final TaskRepository taskRepository;
//    private final TaskHistoryRepository taskHistoryRepository;
//
//    @Scheduled(cron = "0 24 8 * * *")
//    @Transactional
//    public void renewTasks() {
//        System.out.println("Renewal task started.");
//        List<Task> tasksToRenew = taskRepository.findByRenewalDate(LocalDate.now()); // Adjust method name
//
//        List<TaskHistory> renewedTasks = tasksToRenew.stream()
//                .map(this::createTaskHistoryFromTask)
//                .map(taskHistoryRepository::save)
//                .toList();
//        for (Task task : tasksToRenew) {
//            task.setRenewalDate(LocalDate.now().plusDays(1));
//        }
//        taskRepository.saveAll(tasksToRenew);
//    }
//
//    private TaskHistory createTaskHistoryFromTask(Task task) {
//        LocalDate currentDate = LocalDate.now();
//        return TaskHistory.builder()
//                .title(task.getTitle())
//                .description(task.getDescription())
//                .start(currentDate.atTime(task.getStart()))
//                .end(currentDate.atTime(task.getEnd()))
//                .status(TaskStatus.TODO)
//                .type(TaskType.DAILY)
//                .task(task)
//                .build();
//    }
//}