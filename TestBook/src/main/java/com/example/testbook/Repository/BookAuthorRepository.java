package com.example.testbook.Repository;

import com.example.testbook.Domain.BookAuthor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    @Transactional
    void deleteBookAuthorsByBookId(Long id);
}
