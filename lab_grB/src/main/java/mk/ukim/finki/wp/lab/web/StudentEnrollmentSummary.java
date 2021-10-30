package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    public final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;

    public StudentEnrollmentSummary(CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        String courseId = (String) request.getSession().getAttribute("chosenCourse");
        String username = (String) request.getParameter("studentUsername");
        courseService.addStudentInCourse(username, Long.parseLong(courseId));
        context.setVariable("allStudents", courseService.listStudentsByCourse(Long.parseLong(courseId)));
        context.setVariable("courseName", courseService.listAll().stream().filter(c->c.getCourseId().equals(Long.parseLong(courseId)))
                .findFirst().get().getName());

        this.springTemplateEngine.process("studentsInCourse.html", context, response.getWriter());
    }
}
