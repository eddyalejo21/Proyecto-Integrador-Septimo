/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.epmmop.utilitarios;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * @author daniel
 */
public abstract class ManejoDatosGenerico<T> {

	private Class<T> entityClass;

	public ManejoDatosGenerico(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	protected void create(T entity) throws Exception {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().persist(entity);			
			getEntityManager().getTransaction().commit();	
//			getEntityManager().flush();
		
		} catch (Exception e) {
			getEntityManager().getTransaction().rollback();
			throw new Exception(e);
		}

	}

	protected void createBatch(List<T> lista) throws Exception {
		try {
			for (T en : lista) {
				try {
					getEntityManager().getTransaction().begin();
					getEntityManager().persist(en);
					getEntityManager().getTransaction().commit();
//					getEntityManager().flush();
				} catch (Exception e) {
					getEntityManager().getTransaction().rollback();
					throw new Exception(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateBatch(List<T> lista) throws Exception {
		try {
			for (T en : lista) {
				try {
					getEntityManager().getTransaction().begin();
					getEntityManager().merge(en);
					getEntityManager().getTransaction().commit();
//					getEntityManager().flush();
				} catch (Exception e) {
					getEntityManager().getTransaction().rollback();
					throw new Exception(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void editBatch(List<T> lista) throws Exception {
		try {
			for (T en : lista) {
				try {
					getEntityManager().getTransaction().begin();
					getEntityManager().merge(en);
					getEntityManager().getTransaction().commit();
//					getEntityManager().flush();
				} catch (Exception e) {
					getEntityManager().getTransaction().rollback();
					throw new Exception(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	protected void edit(T entity) throws Exception {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().merge(entity);
			getEntityManager().getTransaction().commit();
//			getEntityManager().flush();
		} catch (Exception e) {
			getEntityManager().getTransaction().rollback();
			throw new Exception(e);
		}
	}

	protected void remove(T entity) throws Exception {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().remove(getEntityManager().merge(entity));
			getEntityManager().getTransaction().commit();
//			getEntityManager().flush();
		} catch (Exception e) {
			getEntityManager().getTransaction().rollback();
			throw new Exception(e);
		}
	}

	protected T find(Object id) throws Exception {
		return getEntityManager().find(entityClass, id);
	}

	protected List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	protected List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	protected int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	protected List<T> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		List<T> resultados = null;
		String[] params = null;
		Object[] values = null;
		int index = 0;
		if (queryParams != null) {
			params = new String[queryParams.size()];
			values = new Object[queryParams.size()];
			Iterator<String> i = queryParams.keySet().iterator();
			while (i.hasNext()) {
				String key = i.next();
				params[index] = key;
				values[index++] = queryParams.get(key);
			}
		}
		Query query = getEntityManager().createNamedQuery(queryName);
		for (int j = 0; j < index; j++) {
			query.setParameter(params[j], values[j]);

		}
		resultados = (List<T>) query.getResultList();
		return resultados;
	}

	protected List<T> findByNamedQueryWithPaging(String sql,
			Map<String, Object> queryParams, int pageNumber, int pageSize) {
		List<T> resultados = null;

		String[] params = null;
		Object[] values = null;
		int index = 0;
		if (queryParams != null) {
			params = new String[queryParams.size()];
			values = new Object[queryParams.size()];
			Iterator<String> i = queryParams.keySet().iterator();
			while (i.hasNext()) {
				String key = i.next();
				params[index] = key;
				values[index++] = queryParams.get(key);
			}
		}
		Query query = getEntityManager().createQuery(sql);
		query.setFirstResult(pageNumber * pageSize);
		query.setMaxResults(pageSize);
		for (int j = 0; j < index; j++) {
			query.setParameter(params[j], values[j]);

		}
		resultados = (List<T>) query.getResultList();

		return resultados;
	}

	
}
