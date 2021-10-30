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

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        if(username!=null) //Option: insert new student was chosen
            studentService.save(username,password,name,surname);

        //It has to be with if condition because when inserting new Student, this Servlet is accessed two times and sets attribute two times
        if(request.getSession().getAttribute("chosenCourse") == null){
            //dali mora da se prikace kako variable za da se upotrebuva vo html?
            request.getSession().setAttribute("chosenCourse", request.getParameter("courseId"));
        }
        context.setVariable("students", this.studentService.listAll());
        context.setVariable("courseToShow", request.getSession().getAttribute("chosenCourse"));
        this.springTemplateEngine.process("listStudents.html", context, response.getWriter());

    }
}
