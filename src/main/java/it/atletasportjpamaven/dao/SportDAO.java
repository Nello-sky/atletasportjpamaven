package it.atletasportjpamaven.dao;

import java.util.List;

import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

public interface SportDAO extends IBaseDAO<Sport> {

	List<Sport> findError() throws Exception;

}
