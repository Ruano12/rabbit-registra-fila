package com.rabbitMqSend.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitMqSend.rest.dto.MessageDto;

@RestController
public class SendMessageApi {
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Value("${fila.saida}")
	private String nomeFila;
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	public ResponseEntity<String> pagamento(
			@Valid @NotNull @RequestBody String message) throws Exception  {

		MessageDto messageDto = new MessageDto(message);
		
		ObjectMapper obj = new ObjectMapper();

		String json = obj.writeValueAsString(messageDto);
		
		rabbitTemplate.convertAndSend(nomeFila, json);
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
}
