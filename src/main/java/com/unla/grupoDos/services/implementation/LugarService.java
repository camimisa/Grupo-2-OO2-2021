package com.unla.grupoDos.services.implementation;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.converters.LugarConverter;
import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.models.LugarModel;
import com.unla.grupoDos.repositories.ILugarRepository;
import com.unla.grupoDos.services.ILugarService;

@Service("lugarService")
public class LugarService implements ILugarService{
	@Autowired
	@Qualifier("lugarRepository")
	private ILugarRepository lugarRepository;
	
	@Override
	public List<Lugar> getAll() {
		return lugarRepository.findAll();
	}

	@Override
	public Lugar findById(int id) {
		return lugarRepository.findByIdLugar(id);
	}

	@Override
	public Lugar findByCodPostal(String codPostal) {
		return lugarRepository.findByCodPostal(codPostal);
	}
	@Override
	public Lugar insertOrUpdate(Lugar lugar) {
		return lugarRepository.save(lugar);
	}
	@Override
	public List<Lugar> findByIds(List<Integer> ids){
		return lugarRepository.findAllByIds(ids);
	}
	@Override
	public boolean remove(int id) {
		try {
			lugarRepository.deleteById(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
}