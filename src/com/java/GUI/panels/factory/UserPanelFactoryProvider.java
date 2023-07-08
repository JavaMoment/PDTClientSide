package com.java.GUI.panels.factory;

import java.util.Map;

import com.entities.Usuario;

public class UserPanelFactoryProvider {
	
	private final static Map<String, UserPanelFactory> userTypePanels = Map.of(
			"Analista", new AnalystPanelFactory(),
			"Estudiante", new StudentPanelFactory(),
			"Tutor", new TeacherPanelFactory()
			);
	
	public static UserPanelFactory getUserPanel(Usuario user) {
		return userTypePanels.get(user.getTipoUsuario());
	}

}
