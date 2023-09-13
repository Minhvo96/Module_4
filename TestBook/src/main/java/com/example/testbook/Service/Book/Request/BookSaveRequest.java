package com.example.testbook.Service.Book.Request;
import com.example.testbook.Service.Request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class BookSaveRequest {
    private String title;

    private String price;

    private String publishDate;

    private String description;

    private String type;

    private List<String> idAuthors;

    private SelectOptionRequest category;

}
