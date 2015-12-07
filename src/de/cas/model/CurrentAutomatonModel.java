package de.cas.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.cas.util.ACallable;

public class CurrentAutomatonModel {

	final Object PARAM_NONE[] = {};
	ArrayList<Method> methods;
	Automaton automaton;
	
	public ArrayList<Method> getMethods() {
		return methods;
	}
	
	public CurrentAutomatonModel(Automaton automaton){
		this.automaton = automaton;
		this.methods = getNoParameterMethodsOfCurrentAutomaton();
		/*
		for (Method method : methods) {
			System.out.printf("Method:%s , Parameter:", method.getName());
			for (Parameter param : method.getParameters()) {
				System.out.print(param.getParameterizedType().getTypeName() + ", ");
			}
			System.out.println("");
		}
		*/
	}
	
	public ArrayList<Method> getNoParameterMethodsOfCurrentAutomaton(){
		ArrayList<Method> rMethods = new ArrayList<>();
		try {
			Class<?> cls = Class.forName(automaton.getClass().getName());
			for (Method method : cls.getDeclaredMethods()) {
				if(method.getParameterTypes().length==0){
					if(method.getAnnotation(ACallable.class) != null){
						rMethods.add(method);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rMethods;
	}
	
	public void invokeMethodParameterless(Method method){
		try {
			method.invoke(automaton, PARAM_NONE);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public String getAutomatonSimpleName(){
		return automaton.getClass().getSimpleName();
	}
}
