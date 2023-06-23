package com.rf.usuarios.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rf.usuarios.security.errors.ErrorNoExiste;
import com.rf.usuarios.security.model.Usuario;
import com.rf.usuarios.security.repository.UsuarioRepo;

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
		user.setIdUsuario(0l);
		if (repo.existsByUsUsername(user.getUsUsername())) {
			return null;
		}

		return repo.save(user);
	}

	public Usuario actualiza(Usuario user) throws ErrorNoExiste {
		Optional<Usuario> oUser = repo.findById(user.getIdUsuario());
		
		if (oUser.isEmpty())
			throw new ErrorNoExiste("Al actualizar Usuario " + user.getIdUsuario());
		Usuario usrOld = oUser.get();
		if (!usrOld.getUsUsername().equals(user.getUsUsername())  && repo.existsByUsUsername(user.getUsUsername())) {
			return null;
		}
		System.out.println(user);
		return repo.save(user);
	}

	public void delete(long id) throws ErrorNoExiste {
		Optional<Usuario> oUser = repo.findById(id);
		if (oUser.isEmpty())
			throw new ErrorNoExiste("Al borrar Usuario " + id);
		repo.deleteById(id);
	}


}
