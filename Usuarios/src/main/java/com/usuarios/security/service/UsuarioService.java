package com.usuarios.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuarios.security.errors.ErrorNoExiste;
import com.usuarios.security.model.Usuario;
import com.usuarios.security.repository.UsuarioRepo;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepo repo;


	public Usuario leerUno(long id) throws ErrorNoExiste {
		Optional<Usuario> oUser = repo.findById(id);
		if (oUser.isEmpty())
			throw new ErrorNoExiste("Usuario " + id);
		return oUser.get();
	}

	public List<Usuario> leerTodos(){
		return repo.findAll();
	}

	public Usuario insertar(Usuario user) {
		user.setId_usuario(0l);
		if (repo.existsByUsername(user.getUs_username())) {
			return null;
		}

		return repo.save(user);
	}

	public Usuario actualiza(Usuario user) throws ErrorNoExiste {
		Optional<Usuario> oUser = repo.findById(user.getId_usuario());
		
		if (oUser.isEmpty())
			throw new ErrorNoExiste("Al actualizar Usuario " + user.getId_usuario());
		if (repo.existsByUsername(user.getUs_username())) {
			return null;
		}
		return repo.save(user);
	}

	public void delete(long id) throws ErrorNoExiste {
		Optional<Usuario> oUser = repo.findById(id);
		if (oUser.isEmpty())
			throw new ErrorNoExiste("Al borrar Usuario " + id);
		repo.deleteById(id);
	}


}
