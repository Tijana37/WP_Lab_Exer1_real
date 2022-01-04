package mk.ukim.finki.wp.lab.web.servlets;

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
    public final SpringTemplateEngine springTemplateEngine;

    //na predavanje se veli deka nie implementirame init() metodot, a ne konstruktorot, a na aud rabotime so kons??

    public ListStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        //It has to be with if condition because when inserting new Student,
        // this Servlet is accessed two times and sets attribute two times, ( the second time the parameter=null)
        if(request.getSession().getAttribute("chosenCourse") == null){
            //dali mora da se prikace vo context ili moze i preku sesija za da se upotrebuva vo html?
            //request.Parameter e na nivo na Request, request.getSession.getAttribute e na nivo na sesija
            request.getSession().setAttribute("chosenCourse", request.getParameter("courseId"));

        }
        response.setContentType("application/xhtml+xml");
        context.setVariable("students", this.studentService.listAll());
        this.springTemplateEngine.process("listStudents.html", context, response.getWriter());

    }
}
