package ru.mike.crudappjdbc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.mike.crudappjdbc.controllers.EmployeeController;

public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class, EmployeeController.class};}

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
