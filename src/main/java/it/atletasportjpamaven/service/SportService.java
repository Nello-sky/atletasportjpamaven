package it.atletasportjpamaven.service;

import java.util.List;

import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

public interface SportService {

	List<Sport> listAll() throws Exception; // tested - no interference ✓

	Sport caricaSingoloElemento(Long id) throws Exception; // tested - no interference ✓

	void aggiorna(Sport sportInstance) throws Exception; // tested - no interference ✓

	void inserisciNuovo(Sport sportInstance) throws Exception; // tested - no interference ✓

	void rimuovi(Long idSportToRemove) throws Exception; // **tested - interference ✓

	// per injection
	void setSportDAO(SportDAO sportDAO);

	void setAtletaDAO(AtletaDAO atletaDAO);

	// extra

	List<Sport> trovaErrori() throws Exception;

}
