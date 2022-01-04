package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.*;
import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import mk.ukim.finki.wp.lab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Date;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

class LabGrBApplicationTests {

    MockMvc mockMvc;

    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;

    private static Course course;
    private static Teacher teacher;
    private static boolean isDataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext wac) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    private void initData() throws Exception {
        if(!isDataInitialized){
            isDataInitialized = true;
            teacher = this.teacherService.addTeacher("testTeacher","testSurname", LocalDate.of(2000,12,12));
            course = this.courseService.addCourse("testCourse","testing",teacher.getId().toString(), "WINTER");
            userService.register("username","pw","pw","user","surname",Role.ROLE_ADMIN);
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetCourses() throws Exception {
        MockHttpServletRequestBuilder courseRequest = MockMvcRequestBuilders.get("/courses");
        this.mockMvc.perform(courseRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("coursesController"))
                .andExpect(MockMvcResultMatchers.view().name("listCourses"));
    }

   // @Test
    public void testDeleteCourses() throws Exception {
        System.out.println(course);
        MockHttpServletRequestBuilder courseDeleteRequest = MockMvcRequestBuilders
                .delete("/courses/delete/"+course.getCourseId());
        this.mockMvc.perform(courseDeleteRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/courses"));
    }

}
