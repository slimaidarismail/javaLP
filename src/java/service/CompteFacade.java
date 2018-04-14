/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Compte;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author slim
 */
@Stateless
public class CompteFacade extends AbstractFacade<Compte> {

    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @EJB
    private OperationBanqauireFacade operationBanqauireFacade;

    public int debiter(Compte compte, double montant) {
        Compte loadedCompte = find(compte.getId());
        if (loadedCompte == null) {
            return -1;
        } else {
            compte.setSolde(compte.getSolde() + montant);
            edit(compte);
            operationBanqauireFacade.createOperationDebit(compte, montant);
            return 1;
        }
    }

    public int crediter(Compte compte, double montant) {
        Compte loadedCompte = find(compte.getId());
        if (loadedCompte == null) {
            return -1;
        } else {
            double nvSold = compte.getSolde() - montant;
            if (nvSold < 0) {
                return -2;
            } else {
                compte.setSolde(compte.getSolde() - montant);
                edit(compte);
                operationBanqauireFacade.createOperationDebit(compte, montant);
                return 1;
            }

        }
    }

    public int save(Compte compte) {
        Compte loadedCompte = new Compte();
        if (loadedCompte == null) {
            return -1;
        } else if (compte.getSolde() < 100) {
            return -2;
        } else {
            create(compte);
            operationBanqauireFacade.createOperationDebit(compte, compte.getSolde());
            return 1;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteFacade() {
        super(Compte.class);
    }

}
