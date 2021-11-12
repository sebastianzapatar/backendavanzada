package backend.spring.modelsDAO.personacontroller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.spring.modelentity.Jefe;
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
	public ResponseEntity<?> guardar(@Valid @RequestBody Persona e, 
			BindingResult result) {
		Map<String, Object> response=new HashMap<>();
		Persona w=new Persona();
		if(result.hasErrors()) {
			List<String> error=new ArrayList<>();
			for(FieldError err: result.getFieldErrors()) {
				error.add(err.getField());
			}
			response.put("errors", error);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			w=personaservice.save(e);
		}
		catch(Exception e1){
			response.put("Mensaje", "error al insertar");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<Persona>(w,HttpStatus.OK);
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
	
	@GetMapping("personas/jefes")
	public List<Jefe> listarJefes(){
		return personaservice.encontarJefes();
	}
	@PostMapping("/personas/jefes")
	@ResponseStatus(HttpStatus.CREATED)
	public Jefe guardarj(@RequestBody Jefe e) {
		return personaservice.savej(e);
	}
	@PostMapping("/personas/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,
			@RequestParam("id") Long id){
		Map<String,Object> response=new HashMap<>();
		Persona actual=personaservice.encontrarporId(id);
		if(!archivo.isEmpty()) {
			String nombre=UUID.randomUUID()+"_"
					+archivo.getOriginalFilename().replaceAll(" ", "");
			java.nio.file.Path rutaArchivo=
					Paths.get("uploads").resolve(nombre).toAbsolutePath();
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			}
			catch(Exception e) {
				response.put("Mensaje", "Error al subir la imagen");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String fotoActual=actual.getFoto();
			if(fotoActual!=null && fotoActual.length()>0) {
				java.nio.file.Path rutaFotoAnterior=
						Paths.get("uploads").resolve(fotoActual).toAbsolutePath();
				File fotoAnterior=rutaFotoAnterior.toFile();
				if(fotoAnterior.canRead() && fotoAnterior.exists()) {
					fotoAnterior.delete();
				}
			}
			actual.setFoto(nombre);
			personaservice.save(actual);
			response.put("Mensaje", "Se subio la imagen");
			response.put("Futbolista", actual);
		}
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	@GetMapping("/personas/upload/img/{nombrefoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombrefoto){
		java.nio.file.Path rutaFoto=
				 Paths.get("uploads").resolve(nombrefoto).toAbsolutePath();
		Resource recurso=null;
		try {
			recurso=new UrlResource(rutaFoto.toUri());
		}
		catch(Exception e) {
			
		}
		HttpHeaders cabecera=new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""
		+recurso.getFilename()+"\"");
		return new ResponseEntity<Resource>(recurso, cabecera,HttpStatus.OK);
	}
}
