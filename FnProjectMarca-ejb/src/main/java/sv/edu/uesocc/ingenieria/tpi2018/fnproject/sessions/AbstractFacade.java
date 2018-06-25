/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.tpi2018.fnproject.sessions;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author ricky
 * @param <T>
 */
public abstract class AbstractFacade<T> {

      private final Class<T> entityClass;
    private String consulta="";

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public AbstractFacade(Class<T> entityClass, String consulta) {
        this.entityClass = entityClass;
        this.consulta = consulta;
    }

    protected abstract EntityManager getEntityManager();
    
    public List findByName (String name, int first, int pageSize){
        if (!(name.isEmpty())) {
              if(!consulta.isEmpty()){
            try {
                Query q = getEntityManager().createNamedQuery(consulta);
                q.setParameter("name", name);
                q.setMaxResults(pageSize);
                q.setFirstResult(first);
                return q.getResultList();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
         }
        return Collections.emptyList();
    }

    public T create(T entity) {
        T output = null;
        try {
            EntityManager em = getEntityManager();
            if (em != null && entity != null) {
                em.persist(entity);
                output = entity;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return output;
    }

    public T edit(T entity) {
        T output = null;
        try {
            EntityManager em = getEntityManager();
            if (em != null && entity != null) {
                em.merge(entity);
                output = entity;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return output;
    }

    public boolean remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            return true;
        } catch (Exception e) {
            return false;

        }
    }

    public boolean crear(T entity) {

        if (entity != null && getEntityManager() != null) {
            return create(entity) == entity;
        }
        return false;
    }

    public boolean editar(T entity) {
        if (entity != null && getEntityManager() != null) {
            return edit(entity) == entity;
        }
        return false;
    }

    public T findById(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int lower, int higher) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setFirstResult(lower);
        q.setMaxResults(higher);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
