package backend.spring.modelsDAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.spring.modelentity.Jefe;
import backend.spring.modelentity.Persona;

public interface IPersonaDAO extends JpaRepository<Persona, Long> {
	@Query("from Jefe")
	public List<Jefe> findAllJefes();
}
