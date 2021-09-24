package backend.spring.personaservice;

import java.util.List;

import backend.spring.modelentity.Persona;

public interface IPersonaService {
	public List<Persona> findAll();
	public Persona save(Persona e);
	public void delete(Long id);
	public Persona encontrarporId(Long id);
}
