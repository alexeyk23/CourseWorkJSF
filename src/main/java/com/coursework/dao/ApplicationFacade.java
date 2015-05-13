/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.dao;

import com.coursework.model.Application;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class ApplicationFacade extends AbstractFacade<Application> {
    @PersistenceContext(unitName = "manager")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Application> findAll() {
        Query q = em.createQuery("SELECT u FROM Application u");
        List<Application>    appList = q.getResultList();
        return  appList;
    }
    
    public ApplicationFacade() {
        super(Application.class);
    }
    
}
