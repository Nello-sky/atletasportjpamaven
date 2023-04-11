package it.atletasportjpamaven.service;

import java.util.List;

import javax.persistence.EntityManager;

import eccezioni.SportAssociatoAtletiException;
import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.EntityManagerUtil;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

public class SportServiceImpl implements SportService {

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
	public List<Sport> listAll() throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			sportDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return sportDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Sport caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			sportDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return sportDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Sport sportInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			sportDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			sportDAO.update(sportInstance);

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
	public void inserisciNuovo(Sport sportInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			sportDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			sportDAO.insert(sportInstance);

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
	public void rimuovi(Long idSportToRemove) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			sportDAO.setEntityManager(entityManager);
			atletaDAO.setEntityManager(entityManager);

			if (atletaDAO.findAllBySport(sportDAO.get(idSportToRemove)).size() > 0) {
				throw new SportAssociatoAtletiException("Problema: stai rimuovendo uno sport colleagato ad atleti");
			}

			// eseguo quello che realmente devo fare
			sportDAO.delete(sportDAO.get(idSportToRemove));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public List<Sport> trovaErrori() throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			sportDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return sportDAO.findError();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
