package org.serratec.dto.usuario;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.serratec.models.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioCadastroDTO {

	@NotNull
	@Column(unique = true, length = 12)
	private String login;

	@NotNull
	@Column(length = 100)
	private String password;
	
	public Usuario toUsuario() {
		
		Usuario usuario = new Usuario();
		usuario.setLogin(this.login);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(this.password);
		usuario.setPassword(senhaCodificada);
		
		usuario.setLast_login_date(LocalDateTime.now());
		
		return usuario;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

}
