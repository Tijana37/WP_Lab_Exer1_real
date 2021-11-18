package mk.ukim.finki.wp.lab.web.Filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class FilterCourse implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String courseInAttribute = (String) request.getSession().getAttribute("chosenCourse");
        String courseInParameter = (String) request.getParameter("courseId");
        String path = request.getServletPath();

        if(courseInAttribute == null && courseInParameter==null && path.compareTo("/studentsCoursesServlet")!=0 &&path.compareTo("/listCourses")!=0
                && !path.startsWith("/courses")) {
            response.sendRedirect("/listCourses");
        }
        else {
            chain.doFilter(request, response);
            System.out.println("end filter");
        }

    }
}
