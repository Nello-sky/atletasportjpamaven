package it.atletasportjpamaven.service;

import java.util.List;

import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

public interface AtletaService {

	List<Atleta> listAll() throws Exception; // tested - no interference ✓

	Atleta caricaSingoloElemento(Long id) throws Exception; // tested - no interference ✓

	void aggiorna(Atleta atletaInstance) throws Exception; // tested - no interference ✓

	void inserisciNuovo(Atleta atletaInstance) throws Exception; // tested - no interference ✓

	void rimuovi(Long idAtletaToRemove) throws Exception; // **tested - interference ✓

	// per injection
	void setSportDAO(SportDAO sportDAO);

	void setAtletaDAO(AtletaDAO atletaDAO);

	// extra

	void aggiungiSport(Atleta atletaEsistente, Sport sportInstance) throws Exception; // *ready to control ✓

	Atleta caricaAtletaSingoloConSports(Long id) throws Exception;

	void rimuoviSportDaAtleta(Long idAtleta, Long idSport) throws Exception; // *ready to control ✓

	void deleteClean(Atleta atletaToDeleteClear) throws Exception;

	int contaMedaglieSportChiusi() throws Exception;
}
