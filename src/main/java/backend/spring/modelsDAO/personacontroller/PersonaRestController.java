package backend.spring.modelsDAO.personacontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.modelentity.Persona;
import backend.spring.personaservice.IPersonaService;

@RestController
@RequestMapping("/api")
public class PersonaRestController {
	@Autowired
	private IPersonaService personaservice;
	@GetMapping("/personas")
	public List<Persona> listarPersonas(){
		return personaservice.findAll();
	}
}
