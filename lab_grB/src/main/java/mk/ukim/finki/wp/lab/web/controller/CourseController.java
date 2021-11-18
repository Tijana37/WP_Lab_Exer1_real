package mk.ukim.finki.wp.lab.web.controller;

import com.sun.net.httpserver.HttpContext;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/courses"})
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model){
        model.addAttribute("coursesController", courseService.listAll());
       // System.out.println("controller /courses");
        //model.addAttribute("bodyContent", "bodyContent");
        return "listCourses";
    }


    @PostMapping("/add-form")
    public String saveCourse( @RequestParam String courseName, @RequestParam String description,
                              @RequestParam String teacherId,HttpServletRequest request){
        //imeplenetiraj da pokazuva error ako nema profesor so takov ID za da se dodade
        try {
              Course toEditCourse = (Course) request.getSession().getAttribute("editCourse");
              if(toEditCourse!= null) {//znaci sakame edit na star course, a ne add New Course
                  this.courseService.deleteCourse(toEditCourse.getCourseId());
                  request.getSession().setAttribute("editCourse", null);

              }
        } catch (CourseIDException e) {
            e.printStackTrace();
        }
        courseService.addCourse(courseName,description,teacherId);
        return "redirect:/courses";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, Model model){
        //imeplenetiraj da pokazuva error ako nema kurs so takov ID za da se izbrise
        try {
            this.courseService.deleteCourse(id);
        } catch (CourseIDException e) {
            model.addAttribute("hasError",true);
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/courses";
    }

    @PostMapping({"/add/edit-form/{id}", "/add/edit-form"})
    public String getEditCoursePage(@PathVariable(required = false) Long id, Model model, HttpServletRequest req){
        try {
            req.getSession().setAttribute("editCourse", courseService.getCourse(id));
            model.addAttribute("editCourse", courseService.getCourse(id));
        }catch (Exception e){

        }
        model.addAttribute("teachers", teacherService.findAll());
        return "add-course";
    }


}
