package backend.spring.personaservice;

import java.util.List;

import backend.spring.modelentity.Jefe;
import backend.spring.modelentity.Persona;

public interface IPersonaService {
	public List<Persona> findAll();
	public Persona save(Persona e);
	public void delete(Long id);
	public Persona encontrarporId(Long id);
	public List<Jefe> findAllJefes();
	public List<Jefe> encontarJefes();
	public Jefe savej(Jefe e);
	public List<Jefe> encontrarTodos();
}
