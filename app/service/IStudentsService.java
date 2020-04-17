package service;

import com.google.inject.ImplementedBy;
import models.Student;

import java.util.List;
import java.util.concurrent.CompletionStage;

// В Play нужно четко указать класс-реализатор интерфейса. Иначе интерфейс нельзя будет инжектить в контроллере
@ImplementedBy(StudentsServiceImpl.class)
public interface IStudentsService {
    CompletionStage<Student> addStudent(Student student);
    CompletionStage<List<Student>> getAllStudents();
}
