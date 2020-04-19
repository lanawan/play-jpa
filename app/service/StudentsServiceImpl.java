package service;

import config.DatabaseExecutionContext;
import models.Student;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class StudentsServiceImpl implements StudentRepository {
    private JPAApi jpaApi;
    private DatabaseExecutionContext executionContext;

    @Inject
    public StudentsServiceImpl(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Student> addStudent(Student student) {
        /*  Означает :
            1. executionContext создает отдельный поток для следующей операции записи
            2. Попытка посредством EntityManager записи в базу (persist) полученного из формы объекта (student)
            3. Асинхронный возврат результата записи
         */
        return supplyAsync(() -> wrap(em -> insert(em, student)), executionContext);
    }

    @Override
    public CompletionStage<List<Student>> getAllStudents() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Student insert(EntityManager em, Student student) {
        em.persist(student);
        student.setAge(0);
        return student;
    }

    private List<Student> list(EntityManager em) {
        // TODO: найти альтернативу
        List<Student> students = em.createQuery("select p from Student p", Student.class).getResultList();
        return students;
    }
}
