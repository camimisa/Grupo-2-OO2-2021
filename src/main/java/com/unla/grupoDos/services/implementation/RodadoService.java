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
	public Rodado findById(int id) {
		return rodadoRepository.findByIdRodado(id);
	}

	@Override
	public Rodado findByDominio(String dominio) {
		return rodadoRepository.findByDominio(dominio);
	}
	@Override
	public Rodado insertOrUpdate(Rodado rodado) {
		return rodadoRepository.save(rodado);
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
