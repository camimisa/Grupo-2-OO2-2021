package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.models.RodadoModel;

public interface IRodadoService {
	
	public List<Rodado> getAll();
	public RodadoModel insertOrUpdate(RodadoModel rodadoModel);
	public boolean remove(int id);
	RodadoModel findById(int id);
	RodadoModel findByDominio(String dominio);
	
}
