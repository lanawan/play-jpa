package controllers;

import play.data.FormFactory;
import models.Student;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.IStudentsService;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class StudentsController extends Controller {
    private IStudentsService studentsService;
    private HttpExecutionContext httpExecutionContext;
    private FormFactory formFactory;

    @Inject
    public StudentsController(FormFactory formFactory,
                              IStudentsService studentsService,
                              HttpExecutionContext httpExecutionContext) {
        this.formFactory = formFactory;
        this.studentsService = studentsService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result index() {
        return ok("Работает");
    }

    public Result newStudent(Http.Request request) {
        return ok(views.html.newstudent.render(request));
    }

    public CompletionStage<Result> addStudent(Http.Request request) {
        Student student = formFactory.form(Student.class).bindFromRequest(request).get();
        return studentsService
                .addStudent(student)
                .thenApplyAsync(p -> ok("User created"), httpExecutionContext.current());
    }

    public CompletionStage<Result> getAllStudentsList() {
        return studentsService
                .getAllStudents()
                .thenApplyAsync(list -> {
                    return ok(views.html.students.render(list));
                }, httpExecutionContext.current());
        /*
            Если Play используется в микросервисной архитектуре, то вариант ответа будет уже не 'ok(views.html.students.render(list))'
            так как фронтенд отделен от бекенд, а ответ будет таким : ok(toJson(studentStream.collect(Collectors.toList())))
         */
    }
}
