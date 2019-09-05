package com.g4s.curso.db.uno.serviciosweb;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class Calculadora {
	
	public Double sumar(@WebParam(name="a")Double a, @WebParam(name="b")Double b){
	 	return a+b;
	}
	public Double restar(@WebParam (name="a")Double a, @WebParam (name="b")Double b){
	 	return a-b;
	}
	public Double multiplicar(@WebParam (name="a")Double a, @WebParam (name="b")Double b){
	 	return a*b;
	}
	public Double dividir(@WebParam (name="a")Double a, @WebParam (name="b")Double b){
	 	return a/b;
	}
}
