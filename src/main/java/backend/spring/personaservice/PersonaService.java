package backend.spring.personaservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.spring.modelentity.Persona;
import backend.spring.modelsDAO.IPersonaDAO;

@Service
public class PersonaService implements IPersonaService {
	@Autowired
	private IPersonaDAO personadao;

	@Override
	public List<Persona> findAll() {
		// TODO Auto-generated method stub
		return personadao.findAll();//orm
		
	}
	
}
