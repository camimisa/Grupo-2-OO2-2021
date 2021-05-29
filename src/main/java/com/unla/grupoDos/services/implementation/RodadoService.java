package com.unla.grupoDos.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.converters.RodadoConverter;
import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.models.RodadoModel;
import com.unla.grupoDos.repositories.IRodadoRepository;
import com.unla.grupoDos.services.IRodadoService;
@Service("rodadoService")
public class RodadoService implements IRodadoService{
	@Autowired
	@Qualifier("rodadoRepository")
	private IRodadoRepository rodadoRepository;
	
	@Autowired
	@Qualifier("rodadoConverter")
	private RodadoConverter rodadoConverter;	
	
	@Override
	public List<Rodado> getAll() {
		return rodadoRepository.findAll();
	}

	@Override
	public RodadoModel findById(int id) {
		Rodado rodado = rodadoRepository.findByIdRodado(id);
		RodadoModel rodadoModel = null;
		if(rodado != null)
			rodadoModel = rodadoConverter.entidadAModelo(rodado);
		return rodadoModel;
	}

	@Override
	public RodadoModel findByDominio(String dominio) {
		Rodado rodado = rodadoRepository.findByDominio(dominio);
		RodadoModel rodadoModel = null;
		if(rodado != null)
			rodadoModel = rodadoConverter.entidadAModelo(rodado);
		return rodadoModel;
	}
	@Override
	public RodadoModel insertOrUpdate(RodadoModel RodadoModel) {
		Rodado Rodado = rodadoRepository.save(rodadoConverter.modeloAEntidad(RodadoModel));
		return rodadoConverter.entidadAModelo(Rodado);
	}

	@Override
	public boolean remove(int id) {
		try {
			rodadoRepository.deleteById(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}
