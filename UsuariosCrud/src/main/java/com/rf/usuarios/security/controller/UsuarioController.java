package com.rf.usuarios.security.controller;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rf.usuarios.security.errors.ErrorNoExiste;
import com.rf.usuarios.security.model.Usuario;
import com.rf.usuarios.security.service.UsuarioService;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService cDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> leer(@PathVariable("id") String ids) {
		long idn;
		try {
			idn = Long.parseLong(ids);
		} catch (Exception e) {
			idn = 0;
		}
		Usuario us;
		try {
		 us = cDao.leerUno(idn);
		}catch (Exception e) {
			 us = new Usuario();
		}
		return ResponseEntity.ok(us);
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> leerTodos(){
		List<Usuario> lista = cDao.leerTodos();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/view")
	public ResponseEntity<List<Usuario>> leerTodosVista(){
		List<Usuario> lista = cDao.leerTodos();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	public ResponseEntity<Usuario> insertar(@RequestBody Usuario usr){
		usr.setUsPassword(passwordEncoder.encode(usr.getUsPassword()));
		Usuario us = cDao.insertar(usr);
		return ResponseEntity.ok(us);
	}

	@PutMapping
	public ResponseEntity<Usuario> actualiza(@RequestBody Usuario usr) throws ErrorNoExiste{
		Usuario us;
		try {
		 us = cDao.leerUno(usr.getIdUsuario());
		 usr.setUsPassword(us.getUsPassword());   //cancelo modificacion de password
		 us = cDao.actualiza(usr);
		}catch (Exception e) {
			 us = new Usuario();
		}
		
		return ResponseEntity.ok(us);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> borrar(@PathVariable("id") String ids) throws ErrorNoExiste{
		cDao.delete(Long.parseLong(ids));
		return ResponseEntity.ok("registro borrado");
	}

}
