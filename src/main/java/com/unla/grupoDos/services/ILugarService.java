package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.models.LugarModel;

public interface ILugarService {
	public List<Lugar> getAll();
	public LugarModel insertOrUpdate(LugarModel LugarModel);
	public boolean remove(int id);
	LugarModel findById(int id);
	public List<Lugar> findByIds(List<Integer> ids);
	LugarModel findByCodPostal(String codPostal);
}
