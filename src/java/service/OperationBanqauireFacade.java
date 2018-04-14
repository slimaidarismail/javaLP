/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Compte;
import bean.OperationBanqauire;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author slim
 */
@Stateless
public class OperationBanqauireFacade extends AbstractFacade<OperationBanqauire> {

    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void createOperationDebit(Compte compte, Double montant) {
        createOperation(compte, montant, 2);
    }
    public void createOperationCreadit(Compte compte, Double montant) {
        createOperation(compte, montant, 1);
    }
    
    public List<OperationBanqauire> findByCompte(Compte compte){
        return em.createQuery("select op from OperationBanqauire op where op.compte.id = '"+compte.getId()+"'").getResultList();
    }
    
    private void createOperation(Compte compte, Double montant, int type) {
        OperationBanqauire operationBanqauire = new OperationBanqauire();
        operationBanqauire.setMontant(montant);
        operationBanqauire.setDateOperation(new Date());
        operationBanqauire.setCompte(compte);
        operationBanqauire.setType(type);
        create(operationBanqauire);
    }

    public OperationBanqauireFacade() {
        super(OperationBanqauire.class);
    }

}
