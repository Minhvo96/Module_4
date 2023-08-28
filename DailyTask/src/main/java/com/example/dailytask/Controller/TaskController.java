package com.example.dailytask.Controller;
import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.Enumration.TaskType;
import com.example.dailytask.Domain.Task;
import com.example.dailytask.Service.Task.Request.TaskSaveRequest;
import com.example.dailytask.Service.Task.TaskHistoryService;
import com.example.dailytask.Service.Task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ModelAndView showListTasks() {
        ModelAndView view = new ModelAndView("task/index");
        view.addObject("tasks", taskService.getTasks());
        return view;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView view = new ModelAndView("task/create");
        view.addObject("task", new Task());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }

//    @PostMapping("/create")
//    public ModelAndView create (@ModelAttribute Task task, Model model) {
//        ModelAndView view = new ModelAndView("redirect:/task");
//        taskService.save(task);
//        return view;
//    }
    @PostMapping("/create")
    public String create (@ModelAttribute TaskSaveRequest task) {
        taskService.create(task);
        return "redirect:/task";
}


    @GetMapping("/edit")
    public ModelAndView showEdit (@RequestParam Long id) {
        ModelAndView view = new ModelAndView("/task/edit");
        Task task = taskService.findByID(id);
        view.addObject("task", task);
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }

    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task) {
        taskService.updateTask(task.getId(), task);
        return "redirect:/task";
    }

//    @GetMapping("/delete")
//    public ModelAndView showDelete(@RequestParam Long id){
//        ModelAndView view = new ModelAndView("/task/delete");
//        Task task = taskService.findByID(id);
//        view.addObject("task", task);
//        view.addObject("taskTypes", TaskType.values());
//        view.addObject("taskStatuses", TaskStatus.values());
//        return view;
//    }

//    @PostMapping("/delete")
//    public String deleteTask(@ModelAttribute Task task){
//        taskService.delete(task.getId());
//        return "redirect:/task";
//    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        taskService.deleteById(id);
        return "redirect:/task?message=Deleted";
    }

}
