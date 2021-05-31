package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.models.RodadoModel;

public interface IRodadoService {
	
	public List<Rodado> getAll();
	public Rodado insertOrUpdate(Rodado rodado);
	public boolean remove(int id);
	Rodado findById(int id);
	Rodado findByDominio(String dominio);
	
}
