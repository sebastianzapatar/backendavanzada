package backend.spring.personaservice;

import java.util.List;

import backend.spring.modelentity.Persona;

public interface IPersonaService {
	public List<Persona> findAll();
}
