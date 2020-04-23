import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import controllers.StudentsController;
import models.Student;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.Test;
import play.data.format.Formatters;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import service.StudentRepository;
import service.StudentsServiceImpl;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static play.test.Helpers.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;

public class StudentTest {
    private FormFactory formFactory = mock(FormFactory.class);
    private StudentRepository studentRepository = mock(StudentRepository.class);

    private StudentsServiceImpl studentsServiceImpl;
    private HttpExecutionContext ec = new HttpExecutionContext(ForkJoinPool.commonPool());
    // Тестовые данные студента
    private String firstname = "Александр";
    private String lastname = "Тимофеев";
    private int age = 78;

    @Test
    public void checkIndex() {
        // Создаем класс и вызываем метод index()
        Result result = new StudentsController(formFactory, studentRepository, ec).index();
        // Проверяем результат вызова
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).contains("Работает"));
    }

    @Test
    public void checkRepo() {
        List<Student> students = new ArrayList<>();
        // Создание объекта с тестовыми данными студента
        Student student = new Student();
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setAge(age);
        Student addedStudent = new Student();

        // вызов переопределяемого метода mock-объекта (addStudent)
        // нужна независимая от аргументов реакция (any) на добавление студента
        when(studentRepository.addStudent(any())).thenReturn(supplyAsync(() -> student));

        // Иммитация запроса
        Http.Request request = Helpers.fakeRequest("POST", "/").bodyJson(Json.toJson(student)).build().withTransientLang("ru");
        // Создать тестируемый контроллер нужно создать сначала ValidatorFactory, FormFactory и  HttpExecutionContext
        Messages messages = mock(Messages.class);
        MessagesApi messagesApi = mock(MessagesApi.class);
        when(messagesApi.preferred(request)).thenReturn(messages);
        ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Config config = ConfigFactory.load();
        FormFactory formFactory = new FormFactory(messagesApi, new Formatters(messagesApi), validatorFactory, config);
        HttpExecutionContext ec = new HttpExecutionContext(ForkJoinPool.commonPool());
        // Создать тестируемый контроллер
        StudentsController controller = new StudentsController(formFactory, studentRepository, ec);
        CompletionStage<Result> testAdd = controller.addStudent(request);

        await().atMost(5, SECONDS).untilAsserted(
                () -> assertThat(testAdd.toCompletableFuture()).isCompletedWithValueMatching(
                        result -> result.status() == OK, ""
                )
        );

        // TODO: Протестировать getAllStudentsList()
    }
}