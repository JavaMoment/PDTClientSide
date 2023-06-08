package com.java.controller;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.java.enums.Beans;

public class BeansFactory {
    private static final String baseRoute = "ejb:/PDTServerSide/";
    private static Context jndi;

    public static <T> T getBean(Beans bean, Class<T> expectedType) {
        try {
        	if(jndi == null) {
        		jndi = new InitialContext();
        	}
            switch(bean) {
            	case Usuario:
            		String userRoute = baseRoute + "UsuarioBean!com.services.UsuarioBeanRemote";
            		return expectedType.cast(jndi.lookup(userRoute));
            	case Estudiante:
            		String studentRoute = baseRoute + "EstudianteBean!com.services.EstudianteBeanRemote";
            		return expectedType.cast(jndi.lookup(studentRoute));
            	case Tutor:
            		String teacherRoute = baseRoute + "TutorBean!com.services.TutorBeanRemote";
            		return expectedType.cast(jndi.lookup(teacherRoute));
            	case Analista:
            		String analisRoute = baseRoute + "AnalistaBean!com.services.AnalistaBeanRemote";
            		return expectedType.cast(jndi.lookup(analisRoute));
            	default:
            		return null;
            }
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }
}