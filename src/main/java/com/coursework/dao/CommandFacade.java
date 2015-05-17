package com.coursework.dao;

import com.coursework.model.Command;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author User
 */
@Stateless
public class CommandFacade extends AbstractFacade<Command> {
    @PersistenceContext(unitName = "manager")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    public List<Command> getLastCommandsForApp(int idApp, Date date){
        List<Command> listRes = null;
        Query q = entityManager.createQuery("Select c FROM Command c WHERE c.idApplication = ?1 AND c.dateMake>?2")
                .setParameter(1, idApp)
                .setParameter(2, date, TemporalType.TIMESTAMP);
        listRes = q.getResultList();
        return listRes;
    }
    public CommandFacade() {
        super(Command.class);
    }
    
}
