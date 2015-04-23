/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coursework.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kunakovsky A.
 */
public class UtilHibernate {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManager();
    private static EntityManagerFactory buildEntityManager()
    {   EntityManagerFactory emf=null;
        try {
            emf = Persistence.createEntityManagerFactory("manager");
        } catch (Exception e) {            
        }
        return emf;
    }
    public static EntityManagerFactory getEntityManagerFactory()
    {
        return ENTITY_MANAGER_FACTORY;
    }
}
