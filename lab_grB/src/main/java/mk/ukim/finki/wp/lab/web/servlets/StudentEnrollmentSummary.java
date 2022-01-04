package mk.ukim.finki.wp.lab.web.servlets;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "StudentEnrollmentSummary", urlPatterns = "/studentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    public final CourseService courseService;
    public final SpringTemplateEngine springTemplateEngine;
    public final StudentService studentService;
    public final GradeService gradeService;

    public StudentEnrollmentSummary(CourseService courseService, SpringTemplateEngine springTemplateEngine, StudentService studentService, GradeService gradeService) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        Long courseId = (Long) request.getSession().getAttribute("chosenCourseId");
        String username = (String) request.getParameter("studentUsername");
        String grade_character = request.getParameter("studentGrade");

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        Course courseToShow = null;
        Grade grade = null;
        if (username == null) {
            context.setVariable("hasError", true);
            context.setVariable("error", "You must choose student");
            context.setVariable("students", this.studentService.listAll());
            springTemplateEngine.process("listStudents.html", context, response.getWriter());
        }

        try {
            this.courseService.addStudentInCourse(username, courseId);
            courseToShow = courseService.getCourse(courseId);
            grade = gradeService.insertGrade(courseId,username, grade_character.charAt(0), localDateTime);
            context.setVariable("allStudentsInChosenCourse", courseService.listStudentsByCourse(courseId));
            context.setVariable("courseToShow", courseToShow);
            request.getSession().setAttribute("courseToShow", courseToShow);
            //extension lab3
            context.setVariable("grades", gradeService.getGradesForStudentsInCourse(courseId));
            //To render html properly
            response.setContentType("application/xhtml+xml");
            this.springTemplateEngine.process("studentsInCourse.html", context, response.getWriter());
        } catch (CourseIDException | StudentNotExist e) {
            context.setVariable("hasError", true);
            context.setVariable("error", e.getMessage());
            //Return to page listStudents if it has erros, do not proceed.
            springTemplateEngine.process("listStudents.html", context, response.getWriter());
        }
    }
}
