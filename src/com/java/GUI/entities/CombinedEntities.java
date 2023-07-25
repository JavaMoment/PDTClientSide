package com.java.GUI.entities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CombinedEntities <T, Z> {
	
	private T entityA;
	private Z entityB;
	private List<Method> entityAMethods;
	private List<Method> entityBMethods;
	
	public CombinedEntities(T entity1, Z entity2) {
		this.entityA = entity1;
		this.entityB = entity2;
		this.entityAMethods = Arrays.asList(entity1.getClass().getMethods());
		this.entityBMethods = Arrays.asList(entity2.getClass().getMethods());;
	}

	public T getEntityA() {
		return entityA;
	}

	public Z getEntityB() {
		return entityB;
	}
	
	public Object callMethod(String methodName) {
		Optional<Method> colMethodEntityA = entityAMethods.stream()
				.filter(m -> m.getName().equalsIgnoreCase(methodName)).findFirst();
		Optional<Method> colMethodEntityB = entityBMethods.stream()
				.filter(m -> m.getName().equalsIgnoreCase(methodName)).findFirst();
		try {
			if(colMethodEntityA == null | colMethodEntityB == null) {
				return null;
			}
			else if (!colMethodEntityA.isEmpty()) {
				return colMethodEntityA.get().invoke(entityA);
			} else {
				return colMethodEntityB.get().invoke(entityB);
			}
		} catch  (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
}
