package it.atletasportjpamaven.dao;

import java.util.List;

import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

public interface AtletaDAO extends IBaseDAO<Atleta> {
	
	List<Atleta> findAllBySport(Sport sportInput)throws Exception;
	
	Atleta findByIdFetchingSports(Long id) throws Exception;
	
	int countMedaglieSportClosed() throws Exception;
}
