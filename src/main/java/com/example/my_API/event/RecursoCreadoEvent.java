package com.example.my_API.event;

import org.springframework.context.ApplicationEvent;

import jakarta.servlet.http.HttpServletResponse;

public class RecursoCreadoEvent extends ApplicationEvent{
	
   private static final long serialVersionUID=1L;
   
   private HttpServletResponse response;
   private Long codigo;
   
   public RecursoCreadoEvent(Object source,HttpServletResponse response, Long codigo) {
		super(source);
		this.response=response;
		this.codigo=codigo;
		
	}

   public HttpServletResponse getResponse() {
	return response;
   }

   public Long getCodigo() {
	return codigo;
   }


		
}
