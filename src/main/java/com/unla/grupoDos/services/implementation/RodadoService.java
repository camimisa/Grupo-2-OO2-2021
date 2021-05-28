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
	private IRodadoRepository RodadoRepository;
	
	@Autowired
	@Qualifier("rodadoConverter")
	private RodadoConverter RodadoConverter;	
	
	@Override
	public List<Rodado> getAll() {
		return RodadoRepository.findAll();
	}

	@Override
	public RodadoModel findById(int id) {
		return RodadoConverter.entidadAModelo(RodadoRepository.findByIdRodado(id));
	}

	@Override
	public RodadoModel findByDominio(String dominio) {
		return RodadoConverter.entidadAModelo(RodadoRepository.findByDominio(dominio));
	}
	@Override
	public RodadoModel insertOrUpdate(RodadoModel RodadoModel) {
		Rodado Rodado = RodadoRepository.save(RodadoConverter.modeloAEntidad(RodadoModel));
		return RodadoConverter.entidadAModelo(Rodado);
	}

	@Override
	public boolean remove(int id) {
		try {
			RodadoRepository.deleteById(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}
