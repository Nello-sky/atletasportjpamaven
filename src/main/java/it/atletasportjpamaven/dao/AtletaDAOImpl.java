package it.atletasportjpamaven.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

public class AtletaDAOImpl implements AtletaDAO {

	private EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Atleta> list() throws Exception {
		return entityManager.createQuery("from Atleta", Atleta.class).getResultList();
	}

	@Override
	public Atleta get(Long id) throws Exception {
		return entityManager.find(Atleta.class, id);
	}

	@Override
	public void update(Atleta atletaInstance) throws Exception {
		if (atletaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		atletaInstance = entityManager.merge(atletaInstance);
	}

	@Override
	public void insert(Atleta atletaInstance) throws Exception {
		if (atletaInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(atletaInstance);
	}

	@Override
	public void delete(Atleta atletaInstance) throws Exception {
		if (atletaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(atletaInstance));
	}

	@Override
	public List<Atleta> findAllBySport(Sport sportInput) throws Exception {
		TypedQuery<Atleta> query = entityManager.createQuery("select a FROM Atleta a join a.sports s where s = :sport",
				Atleta.class);
		query.setParameter("sport", sportInput);
		return query.getResultList();
	}

	@Override
	public Atleta findByIdFetchingSports(Long id) {
		TypedQuery<Atleta> query = entityManager
				.createQuery("select a FROM Atleta a left join fetch a.sports s where a.id = :idAtleta", Atleta.class);
		query.setParameter("idAtleta", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public int countMedaglieSportClosed() throws Exception {
		Query query = entityManager.createNativeQuery("select sum(p.medaglievinte) from\r\n"
				+ "(select distinct (z.medaglievinte) from\r\n" + "(select  a.id,a.medaglievinte from\r\n"
				+ "	atleta a \r\n" + "inner join\r\n" + "	atleta_sport y on a.id=y.atleta_id\r\n"
				+ "		inner join\r\n" + "			sport s on s.id=y.sport_id where s.datafine < ?1) z ) p");
		query.setParameter(1, LocalDate.now());
		return ((Number) query.getSingleResult()).intValue();

	}

}
