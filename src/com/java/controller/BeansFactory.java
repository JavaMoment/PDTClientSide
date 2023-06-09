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
            	case Departamentos:
            		String depaRoute = baseRoute + "DepartamentoBean!com.services.DepartamentoBeanRemote";
            		return expectedType.cast(jndi.lookup(depaRoute));
            	case Localidades:
            		String localidadRoute = baseRoute + "LocalidadBean!com.services.LocalidadBeanRemote";
            		return expectedType.cast(jndi.lookup(localidadRoute));
            	case Itr:
            		String itrRoute = baseRoute + "ItrBean!com.services.ItrBeanRemote";
            		return expectedType.cast(jndi.lookup(itrRoute));
            	case Evento:
            		String eventoRoute = baseRoute + "EventoBean!com.services.EventoBeanRemote";
            		return expectedType.cast(jndi.lookup(eventoRoute));
            	case Reclamo:
            		String reclamoRoute = baseRoute + "ReclamoBean!com.services.ReclamoBeanRemote";
            		return expectedType.cast(jndi.lookup(reclamoRoute));
            	case Constancia:
            		String constanciaRoute = baseRoute + "ConstanciaBean!com.services.ConstanciaBeanRemote";
            		return expectedType.cast(jndi.lookup(constanciaRoute));
            	case StatusCertificados:
            		String statusRoute = baseRoute + "StatusCertificadosBean!com.services.StatusCertificadosBeanRemote";
            		return expectedType.cast(jndi.lookup(statusRoute));
            	case Area:
            		String areaRoute = baseRoute + "AreaBean!com.services.AreaBeanRemote";
            		return expectedType.cast(jndi.lookup(areaRoute));
            	case Modalidad:
            		String modalidadRoute = baseRoute + "ModalidadBean!com.services.ModalidadBeanRemote";
            		return expectedType.cast(jndi.lookup(modalidadRoute));
            	case Estado:
            		String estadoRoute = baseRoute + "EstadoBean!com.services.EstadoBeanRemote";
            		return expectedType.cast(jndi.lookup(estadoRoute));
            	case EstudianteEventoPK:
            		String EstudianteEventoPK = baseRoute + "EstudianteEventoPKBean!com.services.EstudianteEventoPKBeanRemote";
            		return expectedType.cast(jndi.lookup(EstudianteEventoPK));
            	case EstudianteEvento:
            		String EstudianteEvento = baseRoute + "EstudianteEventoBean!com.services.EstudianteEventoBeanRemote";
            		return expectedType.cast(jndi.lookup(EstudianteEvento));
            	default:
            		return null;
            }
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

}