package com.java.GUI.utils;

import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.entities.Evento;
import com.services.EventoBeanRemote;

public class Test {

	public static void main(String[] args) throws NamingException {
		
		EventoBeanRemote eventoBean = (EventoBeanRemote) InitialContext.doLookup("ejb:/PDTServerSide/EventoBean!com.services.EventoBeanRemote");
		
		
		List<Evento> eventos = eventoBean.selectAll();
		
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
				.filter(value -> !value.equals("idEvento") && !value.equals("analistas")).toArray(String[]::new);
		
		// Recorremos los eventos que hay en la BD	
		for (var evento : eventos) {
			// Conseguir el tutor del evento del ciclo
			// Creamos una variable de apoyo para conseguir el id del Tutor
			long idTutor = 0;
			// Recorremos todos los registros que haya en TutorEventos para conseguir el idTutor
			for(var eventoTutor : evento.getTutorEventos()) {
				// Cuando el id del evento coicide con el id del evento de ciclo
				if (eventoTutor.getId().getIdEvento() == evento.getIdEvento()){
					idTutor = eventoTutor.getId().getIdTutor();
					break;
				}
			}
			// Si es null no hago nada 
			if(idTutor == 0) {
				System.out.println(":(");
			}else {
				var tutor = eventoBean.tutorDelEvento(idTutor);
				System.out.println(tutor.getUsuario().getNombre1());
			}
		}
	}

}
