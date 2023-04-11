package it.atletasportjpamaven.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import eccezioni.AtletaAssociatoSportException;
import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.EntityManagerUtil;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

public class AtletaServiceImpl implements AtletaService {

	private AtletaDAO atletaDAO;
	private SportDAO sportDAO;

	@Override
	public void setSportDAO(SportDAO sportDAO) {
		this.sportDAO = sportDAO;
	}

	@Override
	public void setAtletaDAO(AtletaDAO atletaDAO) {
		this.atletaDAO = atletaDAO;
	}

	@Override
	public List<Atleta> listAll() throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return atletaDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Atleta caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return atletaDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Atleta atletaInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			atletaDAO.update(atletaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void inserisciNuovo(Atleta atletaInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			atletaDAO.insert(atletaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Long idAtletaToRemove) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			// ruoloDAO.setEntityManager(entityManager);
			atletaDAO.setEntityManager(entityManager);

			if (atletaDAO.get(idAtletaToRemove).getSports().size() > 0) {
				throw new AtletaAssociatoSportException("Problema: stai rimuovendo un atleta colleagato a degli sport");
			}

			// eseguo quello che realmente devo fare
			atletaDAO.delete(atletaDAO.get(idAtletaToRemove));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public Atleta caricaAtletaSingoloConSports(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return atletaDAO.findByIdFetchingSports(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiungiSport(Atleta atletaEsistente, Sport sportInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);

			// 'attacco' alla sessione di hibernate i due oggetti
			// così jpa capisce che se è già presente quel ruolo non deve essere inserito
			atletaEsistente = entityManager.merge(atletaEsistente);
			sportInstance = entityManager.merge(sportInstance);

			atletaEsistente.getSports().add(sportInstance);
			// l'update non viene richiamato a mano in quanto
			// risulta automatico, infatti il contesto di persistenza
			// rileva che utenteEsistente ora è dirty vale a dire che una sua
			// proprieta ha subito una modifica (vale anche per i Set ovviamente)

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void rimuoviSportDaAtleta(Long idAtleta, Long idSport) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);
			sportDAO.setEntityManager(entityManager);

			// carico nella sessione di hibernate i due oggetti
			// così jpa capisce che se è già presente quel ruolo non deve essere inserito
			Atleta atletaEsistente = atletaDAO.findByIdFetchingSports(idAtleta);
			Sport sportInstance = sportDAO.get(idSport);

			atletaEsistente.getSports().remove(sportInstance);
			// l'update non viene richiamato a mano in quanto
			// risulta automatico, infatti il contesto di persistenza
			// rileva che utenteEsistente ora è dirty vale a dire che una sua
			// proprieta ha subito una modifica (vale anche per i Set ovviamente)

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void deleteClean(Atleta atletaToDeleteClear) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			// ruoloDAO.setEntityManager(entityManager);
			atletaDAO.setEntityManager(entityManager);

			if (atletaDAO.get(atletaToDeleteClear.getId()).getSports().size() > 0) {
				atletaDAO.get(atletaToDeleteClear.getId()).getSports().clear();

			}

			// eseguo quello che realmente devo fare
			atletaDAO.delete(atletaDAO.get(atletaToDeleteClear.getId()));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int contaMedaglieSportChiusi() throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			atletaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return atletaDAO.countMedaglieSportClosed();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
