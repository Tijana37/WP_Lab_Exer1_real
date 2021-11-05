package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/studentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    //dali treba da se iskoristi courseService od CoursesListServlet ili treba da se krerira nov ?
    //istoto prasanje i za template ENgine
    public final CourseService courseService;
    public final SpringTemplateEngine springTemplateEngine;

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
        String courseToShow =  this.courseService.listAll().stream().filter(c->c.getCourseId().toString().compareTo(courseId)==0).findFirst().get().getName();
        System.out.println("do tuka e ok");
        System.out.println(username+"--"+courseId);
        this.courseService.addStudentInCourse(username, Long.parseLong(courseId));
        context.setVariable("allStudentsInChosenCourse", courseService.listStudentsByCourse(Long.parseLong(courseId)));
        context.setVariable("courseToShow", courseToShow);
        System.out.println("enddddd");
        this.springTemplateEngine.process("studentsInCourse.html", context, response.getWriter());
    }
}
