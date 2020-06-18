package com.cobrancaapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cobrancaapp.model.Titulo;

@Transactional
@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long>{

	
	 @Query("select u from Titulo u where u.descricao like %?1")
	  List<Titulo> findByFirstnameEndsWith(String descricao); 
	}
