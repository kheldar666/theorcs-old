package org.libermundi.theorcs.services.impl;

import java.io.Serializable;

import org.libermundi.theorcs.domain.base.Identifiable;
import org.libermundi.theorcs.services.base.Service;
import org.libermundi.theorcs.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract Service
 * Implements an set of utility methods used by all manager
 * @param <T>
 * @param <I>
 *
 */
@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
public abstract class AbstractServiceImpl<T extends Identifiable<I>,I extends Serializable> implements Service<T, I> {
	private static final Logger logger = LoggerFactory.getLogger(AbstractServiceImpl.class);
	
	protected SimpleJpaRepository<T,I> repository;
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#getRepository()
	 */
	@Override
	public SimpleJpaRepository<T,I> getRepository() {
		return this.repository;
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#setDao(org.libermundi.theorcs.repositories.base.BaseRepository)
	 */
	@Override
	public void setRepository(SimpleJpaRepository<T,I> repository) {
		if(logger.isDebugEnabled()) {
			logger.debug("Set Repository : " + repository);
		}
		this.repository = repository;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#delete(java.io.Serializable)
	 */
	@Override
	public void delete(I id) {
		if(logger.isDebugEnabled()) {
			logger.debug("Delete Object with ID : " + id);
		}
		this.repository.delete((T) id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#delete(org.libermundi.theorcs.core.model.base.Identifiable)
	 */
	@Override
	public void delete(T entity) {
		this.repository.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#getAll()
	 */
	@Override
	public Iterable<T> findAll() {
		return this.repository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#duplicate(org.libermundi.theorcs.core.model.base.Identifiable)
	 */
	@Override
	public T duplicate(T original) {
		return duplicate(original, Boolean.TRUE);
	}
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#duplicate(org.libermundi.theorcs.core.model.base.Identifiable, boolean)
	 */
	@Override
	public T duplicate(T original, boolean resetPrimaryKey) {
		T copy = ObjectUtils.safeDeepCopy(original);
		if(resetPrimaryKey){
			copy.setId(null);
		}
		return copy;
	}
	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#count()
	 */
	@Override
	public long count() {
		if(logger.isDebugEnabled()) {
			//logger.debug(" Execute : getAllCount() for entity " + this.repository.getEntityClass().getName());
		}
		return this.repository.count();
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#findOne(java.io.Serializable)
	 */
	@Override
	public T findOne(I id) {
		return this.repository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#save(org.libermundi.theorcs.domain.base.Identifiable)
	 */
	@Override
	public <S extends T> S save(S entity) {
		return this.repository.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see org.libermundi.theorcs.services.base.Service#save(java.lang.Iterable)
	 */
	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		return this.repository.save(entities);
	}
	

}
