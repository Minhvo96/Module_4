package com.example.dailytask.Controller;
import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.Enumration.TaskType;
import com.example.dailytask.Service.Task.Request.TaskSaveRequest;
import com.example.dailytask.Service.Task.TaskHistoryService;
import com.example.dailytask.Service.Task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/history")
@AllArgsConstructor
public class TaskHistoryController {
    private final TaskHistoryService taskHistoryService;
    private final TaskService taskService;

    @GetMapping
    public ModelAndView showHistoryTasks(@RequestParam(required = false) String message) {
        ModelAndView view = new ModelAndView("task/history");
        view.addObject("historytasks", taskHistoryService.getHistoryTasks());
        view.addObject("statuses", TaskStatus.values());
        view.addObject("message", message);
        return view;
    }
    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView view = new ModelAndView("task/create");
        view.addObject("task", new TaskSaveRequest());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }

    @PostMapping("/create")
    public ModelAndView showCreate(@ModelAttribute TaskSaveRequest task) {
        ModelAndView view = new ModelAndView("task/create");
        taskService.create(task);
        view.addObject("message", "Created");
        view.addObject("task", new TaskSaveRequest());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }
    @GetMapping("/{id}/{status}")
    public String changeStatus(@PathVariable Long id, @PathVariable TaskStatus status){
        taskHistoryService.changeStatus(id, status);
        return "redirect:/history?message=Change Success";
    }


}
