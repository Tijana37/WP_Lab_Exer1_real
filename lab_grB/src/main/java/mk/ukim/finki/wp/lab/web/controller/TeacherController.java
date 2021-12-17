package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/teachers")
public class TeacherController {
    public final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String listAllTeachers(Model model){
        model.addAttribute("teachers", teacherService.findAll());
        return "teacherPage";
    }

    @PostMapping("/add-form")
    public String saveTeacher(@RequestParam String teacherName, @RequestParam String teacherSurname,
                             @RequestParam(required = false) String teacherId, HttpServletRequest request){
        //imeplenetiraj da pokazuva error ako nema profesor so takov ID za da se dodade
        try {
            Teacher toEditTeacher = (Teacher) request.getSession().getAttribute("editTeacher");
            if(toEditTeacher!= null) {//znaci sakame edit na star course, a ne add New Course
                request.getSession().setAttribute("editTeacher", null);
                teacherService.delete(toEditTeacher.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        teacherService.addTeacher(teacherName,teacherSurname);
        return "redirect:/teachers";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, Model model){
        try {
            this.teacherService.delete(id);
        } catch (Exception e) {
            model.addAttribute("hasError",true);
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/teachers";
    }

    @PostMapping({"/add/edit-form/{id}", "/add/edit-form"})
    public String getEditCoursePage(@PathVariable(required = false) Long id, Model model, HttpServletRequest req){
        try {
            req.getSession().setAttribute("editTeacher", teacherService.findByID(id));
            model.addAttribute("editTeacher", teacherService.findByID(id));
        }catch (Exception e){}
        model.addAttribute("teachers", teacherService.findAll());
        return "add-teacher";
    }

}
