package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.models.LugarModel;

public interface ILugarService {
	public List<Lugar> getAll();
	public Lugar insertOrUpdate(Lugar Lugar);
	public boolean remove(int id);
	public List<Lugar> findByIds(List<Integer> ids);
	Lugar findById(int id);
	Lugar findByCodPostal(String codPostal);
}
