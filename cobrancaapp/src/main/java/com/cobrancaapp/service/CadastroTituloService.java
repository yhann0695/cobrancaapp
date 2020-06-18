package com.cobrancaapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cobrancaapp.model.StatusTitulo;
import com.cobrancaapp.model.Titulo;
import com.cobrancaapp.repository.TituloRepository;

@Service
public class CadastroTituloService {

	@Autowired
	private TituloRepository tituloRepository;

	public void salvar(Titulo titulo) {
		
		try {
			tituloRepository.save(titulo);
		} catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Data inválida!");
		}
	}
	
	public List<Titulo> listarTitulos() {
		return tituloRepository.findAll();
	}
	
	public void excluir(Long codigo) {
		tituloRepository.deleteById(codigo);
	}
	
	public Titulo editar(Long codigo) {
		return tituloRepository.findById(codigo).get();
	}
	
	public void receber(Long codigo) {
		Titulo titulo = tituloRepository.findById(codigo).get();
		titulo.setStatus(StatusTitulo.RECEBIDO);
		tituloRepository.save(titulo);
	}
}
