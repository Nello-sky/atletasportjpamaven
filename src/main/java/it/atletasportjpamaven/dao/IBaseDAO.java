package it.atletasportjpamaven.dao;

import java.util.List;

import javax.persistence.EntityManager;

public interface IBaseDAO<T> {

	List<T> list() throws Exception;

	T get(Long id) throws Exception;

	void update(T o) throws Exception;

	void insert(T o) throws Exception;

	void delete(T o) throws Exception;

	// questo mi serve per l'injection
	void setEntityManager(EntityManager entityManager);

}
