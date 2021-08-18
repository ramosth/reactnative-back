package org.serratec.resources;

import java.util.List;
import java.util.Optional;

import org.serratec.dto.skill.SkillCadastroDTO;
import org.serratec.exception.SkillException;
import org.serratec.models.Skill;
import org.serratec.repository.SkillRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "API - Skills")
@RequestMapping("/skill")
public class SkillResource {

	SkillRepository skillRepository;

	@ApiOperation(value = "Consulta todos as skills")
	@GetMapping("/all")
	public ResponseEntity<?> getSkills() {

		List<Skill> skills = skillRepository.findAll();
		if (skills != null)
			return new ResponseEntity<>(skills, HttpStatus.OK);
		else
			return new ResponseEntity<>("Não há skills cadastradas", HttpStatus.BAD_REQUEST);

	}

	@ApiOperation(value = "Consulta de skill por id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getSkill(@PathVariable Long id) {
		Optional<Skill> optional = skillRepository.findById(id);

		if (optional.isEmpty())
			return new ResponseEntity<>("Skill não encontrada.", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new Skill(optional.get()), HttpStatus.OK);

	}

	@ApiOperation(value = "Adição de um skill")
	@PostMapping("/add")
	public ResponseEntity<?> postSkill(@Validated @RequestBody SkillCadastroDTO dto) {

		try {
			Skill skill = dto.toSkill();
			skillRepository.save(skill);

			return new ResponseEntity<>("Skill cadastrada com sucesso.", HttpStatus.OK);

		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Falha no cadastro.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Atualização de uma skill pelo seu ID")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> putSkill(@PathVariable Long id, @RequestBody Skill skill) {
		
		try {
			Skill optional = skillRepository.findById(id).orElseThrow(() -> new SkillException("Skill não encontrada."));
			
			if(skill.getName() != null && !skill.getName().isBlank())
				optional.setName(skill.getName());
			if(skill.getVersion() != null && !skill.getVersion().isBlank())
				optional.setVersion(skill.getVersion());
			if(skill.getDescription() != null && !skill.getDescription().isBlank())
				optional.setDescription(skill.getDescription());
			if(skill.getImage_url() != null && !skill.getImage_url().isBlank())
				optional.setImage_url(skill.getImage_url());
			
			skillRepository.save(optional);
			
			return new ResponseEntity<>("Skill atualizada com sucesso", HttpStatus.OK);
		} catch (SkillException e) {
			return new ResponseEntity<>("Erro ao atualizar cadastro.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Exclusão de uma skill")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
		Optional <Skill> optional = skillRepository.findById(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>("Skill não encontrada.", HttpStatus.NOT_FOUND);
		}
		
		Skill existente = optional.get();
		
		skillRepository.delete(existente);
		
		return new ResponseEntity<>("Skill deletada com sucesso.", HttpStatus.OK);
	}

}
