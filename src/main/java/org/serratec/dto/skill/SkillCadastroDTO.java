package org.serratec.dto.skill;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.serratec.models.Skill;

public class SkillCadastroDTO {

	@NotNull
	@NotBlank
	@Column(unique = true, length = 100)
	private String name;

	@NotBlank
	@Column(length = 10)
	private String version;

	@NotBlank
	@NotNull
	@Column(length = 255)
	private String description;

	@NotBlank
	@Column(length = 255)
	private String image_url;
	
	public Skill toSkill() {
		
		Skill skill = new Skill();
		skill.setName(this.name);
		skill.setVersion(this.version);
		skill.setDescription(this.description);
		skill.setImage_url(this.image_url);
		
		return skill;
	}
	
	public SkillCadastroDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return description;
	}

	public String getImage_url() {
		return image_url;
	}
	
}
