package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ListStudentServlet", urlPatterns="/addStudent")
public class ListStudentServlet extends HttpServlet {
    public final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;

    public ListStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("students", this.studentService.listAll());
        request.getSession().setAttribute("chosenCourse", request.getParameter("courseId"));
        //dali mora da se prikace kako variable za da se upotrebuva vo html?
        context.setVariable("courseToShow", request.getSession().getAttribute("chosenCourse"));
        this.springTemplateEngine.process("listStudents.html", context, response.getWriter());

    }
}
