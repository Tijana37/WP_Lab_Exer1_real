package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.SessionService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class Listener implements HttpSessionListener {

    public Listener() {

    }



    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
        if(se.getSession().getAttribute("broj")!=null)
            se.getSession().getServletContext().setAttribute("broj", (int)se.getSession().getAttribute("broj")+1);
        else
            se.getSession().getServletContext().setAttribute("broj",0);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        if(se.getSession().getAttribute("broj")!=null)
            se.getSession().getServletContext().setAttribute("broj", (int)se.getSession().getAttribute("broj")-1);
    }

}
