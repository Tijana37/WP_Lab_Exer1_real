package mk.ukim.finki.wp.lab.web.servlets;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/studentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    public final CourseService courseService;
    public final SpringTemplateEngine springTemplateEngine;
    public final StudentService studentService;

    public StudentEnrollmentSummary(CourseService courseService, SpringTemplateEngine springTemplateEngine, StudentService studentService) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
        this.studentService = studentService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        String courseId = (String) request.getSession().getAttribute("chosenCourse");
        String username = (String) request.getParameter("studentUsername");

        Course courseToShow = null;
        if(username == null){
            context.setVariable("hasError", true);
            context.setVariable("error", "You must choose student");
            context.setVariable("students", this.studentService.listAll());
            springTemplateEngine.process("listStudents.html", context, response.getWriter());
        }

        try {
            this.courseService.addStudentInCourse(username, Long.parseLong(courseId));
            courseToShow = courseService.getCourse(Long.parseLong(courseId));
            context.setVariable("allStudentsInChosenCourse", courseService.listStudentsByCourse(Long.parseLong(courseId)));
            context.setVariable("courseToShow", courseToShow);
            this.springTemplateEngine.process("studentsInCourse.html", context, response.getWriter());
        } catch ( CourseIDException | StudentNotExist e) {
            context.setVariable("hasError", true);
            context.setVariable("error", e.getMessage());
            //Return to page listStudents if it has erros, do not proceed.
            springTemplateEngine.process("listStudents.html", context, response.getWriter());
        }
    }
}
