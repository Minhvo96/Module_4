package com.example.dailytask.Repository;
import com.example.dailytask.Domain.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    @Query(value = "SELECT t  FROM TaskHistory t WHERE  " +
            "(DATE_FORMAT(t.start, '%Y-%m-%d') <= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND " +
            "DATE_FORMAT(t.end, '%Y-%m-%d') >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND t.type = 'NONE_DAILY')" +
            "OR (DATE_FORMAT(t.start, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND t.type = 'DAILY')" )

    List<TaskHistory> findAllTaskToDay();
}
