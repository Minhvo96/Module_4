package com.example.dailytask.Domain;

import com.example.dailytask.Domain.Enumration.TaskStatus;
import com.example.dailytask.Domain.Enumration.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_history")
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime start;

    private LocalDateTime end;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Enumerated(value = EnumType.STRING)
    private TaskType type;

    private LocalDate created;
    private LocalDate renewaDate = LocalDate.now().plusDays(1);

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @PrePersist
    public void setupBeforeInsert(){
        status = TaskStatus.TODO;
        created = LocalDate.now();
    }
}
