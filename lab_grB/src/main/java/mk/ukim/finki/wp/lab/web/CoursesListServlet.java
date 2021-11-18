package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.SessionService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CoursesListServlet",urlPatterns = "/d/listCourses")
public class CoursesListServlet extends HttpServlet {

    public final CourseService courseService;
    public final SpringTemplateEngine springTemplateEngine;
    public final SessionService sessionService;

    public CoursesListServlet(CourseService courseService, SpringTemplateEngine springTemplateEngine, SessionService sessionService) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
        this.sessionService = sessionService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        System.out.println("servlet");
        context.setVariable("courses", courseService.listAll());
        //Ova go setirame za da moze da se popolnat povekje kursevi od edna SESIJA (browser)
        request.getSession().setAttribute("chosenCourse", null);
        this.springTemplateEngine.process("listCourses.html", context, response.getWriter());
    }


}
