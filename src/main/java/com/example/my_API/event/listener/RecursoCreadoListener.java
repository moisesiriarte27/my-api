package com.example.my_API.event.listener;

import java.net.URI;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.my_API.event.RecursoCreadoEvent;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class RecursoCreadoListener  implements ApplicationListener<RecursoCreadoEvent> {
	
	@Override
	public void onApplicationEvent(RecursoCreadoEvent recursoCreadoEvent) {
		HttpServletResponse response=recursoCreadoEvent.getResponse();
		Long codigo=recursoCreadoEvent.getCodigo();
		adicionarHeaderlocation(response, codigo);
	}

	private void adicionarHeaderlocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
    			.buildAndExpand(codigo).toUri();
    	  response.setHeader("Location", uri.toASCIIString());
	}
	

}
