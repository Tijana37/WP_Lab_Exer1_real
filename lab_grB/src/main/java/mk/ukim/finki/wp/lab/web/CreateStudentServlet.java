package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.impl.StudentServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateStudentServlet", urlPatterns = "/createStudent")
public class CreateStudentServlet extends HttpServlet {

    public final StudentServiceImpl studentService;
    public final SpringTemplateEngine springTemplateEngine;

    public CreateStudentServlet(StudentServiceImpl studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request,response, request.getServletContext());

        this.springTemplateEngine.process("createStudent.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        this.studentService.save(username,password,name,surname);

        context.setVariable("students", this.studentService.listAll());
        //context.setVariable("courseToShow", request.getSession().getAttribute("chosenCourse"));

        this.springTemplateEngine.process("listStudents.html", context, response.getWriter());


    }
}
