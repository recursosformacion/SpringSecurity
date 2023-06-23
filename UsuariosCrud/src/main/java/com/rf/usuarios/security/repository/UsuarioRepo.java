package com.rf.usuarios.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rf.usuarios.security.model.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario,Long>{

	Optional<Usuario> findByUsUsername(String nombre);
	boolean existsByUsUsername (String nombre);
	
}
