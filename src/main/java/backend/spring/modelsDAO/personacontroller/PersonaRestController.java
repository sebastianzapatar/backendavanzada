package backend.spring.modelsDAO.personacontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.modelentity.Persona;
import backend.spring.personaservice.IPersonaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*",allowedHeaders = "*")
public class PersonaRestController {
	@Autowired
	private IPersonaService personaservice;
	@GetMapping("/personas")
	public List<Persona> listarPersonas(){
		return personaservice.findAll();
	}
	@PostMapping("/personas")
	@ResponseStatus(HttpStatus.CREATED)
	public Persona guardar(@RequestBody Persona e) {
		return personaservice.save(e);
	}
	@DeleteMapping("/personas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void borrar(@PathVariable Long id) {
		personaservice.delete(id);
	}
	@GetMapping("/personas/{id}")
	public Persona encontraporId(@PathVariable Long id) {
		return personaservice.encontrarporId(id);
	}
	@PutMapping("/personas/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Persona editar(@PathVariable Long id,@RequestBody Persona e) {
		Persona actual=personaservice.encontrarporId(id);
		actual.setNombre(e.getNombre());
		actual.setApellido(e.getApellido());
		return personaservice.save(actual);
	}
}
