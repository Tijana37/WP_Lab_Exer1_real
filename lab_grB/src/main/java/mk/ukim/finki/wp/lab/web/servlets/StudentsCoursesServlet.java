package mk.ukim.finki.wp.lab.web.servlets;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "Student'sCoursesServlet", urlPatterns = "/studentsCoursesServlet")
public class StudentsCoursesServlet extends HttpServlet {
    public final StudentService studentService;
    public final CourseService courseService;
    public final SpringTemplateEngine springTemplateEngine;

    public StudentsCoursesServlet(StudentService studentService, CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("students", this.studentService.listAll());
        this.springTemplateEngine.process("studentCourse.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        String chosenStudentUsername = request.getParameter("studentToShow");
        System.out.println(chosenStudentUsername);
        Optional<Student> chosenStudent = this.studentService.findByUsername(chosenStudentUsername);
        if(chosenStudent.isPresent()){
            List<Course> hisCourses = studentService.getCoursesForStudent(chosenStudent.get());
            context.setVariable("chosenStudent", chosenStudent.get().getUsername());
            context.setVariable("Courses", hisCourses);
        }

        this.springTemplateEngine.process("ShowCourses.html", context, response.getWriter());

    }
}
