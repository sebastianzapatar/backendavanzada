package backend.spring.modelsDAO;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.modelentity.Persona;

public interface IPersonaDAO extends JpaRepository<Persona, Long> {

}
