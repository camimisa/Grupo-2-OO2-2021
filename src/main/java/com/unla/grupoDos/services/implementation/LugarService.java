package com.unla.grupoDos.services.implementation;

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
	
	@Autowired
	@Qualifier("lugarConverter")
	private LugarConverter lugarConverter;	
	
	@Override
	public List<Lugar> getAll() {
		return lugarRepository.findAll();
	}

	@Override
	public LugarModel findById(int id) {
		return lugarConverter.entidadAModelo(lugarRepository.findByIdLugar(id));
	}

	@Override
	public LugarModel findByCodPostal(String codPostal) {
		return lugarConverter.entidadAModelo(lugarRepository.findByCodPostal(codPostal));
	}
	@Override
	public LugarModel insertOrUpdate(LugarModel LugarModel) {
		Lugar Lugar = lugarRepository.save(lugarConverter.modeloAEntidad(LugarModel));
		return lugarConverter.entidadAModelo(Lugar);
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