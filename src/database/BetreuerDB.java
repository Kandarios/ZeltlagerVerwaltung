package database;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bbv.basics.Betreuer;
import bbv.basics.JournalEntry;

/**
 *
 * @author sqlitetutorial.net
 */
public class BetreuerDB {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();
  private static BetreuerDB instance = null;

  private BetreuerDB() {

  }

  public static BetreuerDB getInstance() {
    if(instance == null) {
      instance = new BetreuerDB();
    } 
    return instance;
  }

  public boolean saveBetreuer(Betreuer betreuer) {
    try {
      entityManager.getTransaction().begin();
      entityManager.merge(betreuer);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
    return true;
  }

  public List<Betreuer> getBetreuerList() {
    List<Betreuer> betreuerList = new ArrayList<Betreuer>();
    try {
      entityManager.getTransaction().begin();
      betreuerList = entityManager.createQuery("from Betreuer").getResultList();
      entityManager.getTransaction().commit();
      return betreuerList;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
    return betreuerList;
  }
  
  public Betreuer getBetreuer(Long betreuerID) {
   Betreuer betreuer = new Betreuer();
    try {
      entityManager.getTransaction().begin();
      betreuer = (Betreuer) entityManager.find(Betreuer.class, betreuerID);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
    return betreuer;
  }

  public void updateBetreuer(Long betreuerID, String newName, String newZelt, String newPicture) {
    try {
      entityManager.getTransaction().begin();
      Betreuer betreuer = (Betreuer) entityManager.find(Betreuer.class, betreuerID);
      betreuer.setName(newName);
      betreuer.setZelt(newZelt);
      betreuer.setPicture(newPicture);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }

  public void deleteBetreuer(Long betreuerId) {
    try {
      entityManager.getTransaction().begin();
      Betreuer betreuer = (Betreuer) entityManager.find(Betreuer.class, betreuerId);
      entityManager.remove(betreuer);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }
  
  
  public boolean saveJournalEntry(JournalEntry entry) {
    try {
      System.out.println("In Saving the ID is " + entry.getBetreuerID());

      entityManager.getTransaction().begin();
      entityManager.merge(entry);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return true;
  }

  public List<JournalEntry> getJournalEntryList() {
    List<JournalEntry> journalEntryList = new ArrayList<JournalEntry>();
    try {
      entityManager.getTransaction().begin();
      journalEntryList = entityManager.createQuery("from JournalEntry").getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return journalEntryList;
  }
  
  public List<JournalEntry> getJournalEntryList(Long betreuerID) {
    List<JournalEntry> journalEntryList = new ArrayList<JournalEntry>();
    try {
      System.out.println("Query for entries with ID:  " + betreuerID);

      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from JournalEntry where BETREUERID = :id ");
      query.setParameter("id", betreuerID);
      journalEntryList = query.getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return journalEntryList;
  }

  public void updateJournalEntry(Long entryID, String text, String date) {
    try {
      entityManager.getTransaction().begin();
      JournalEntry entry = (JournalEntry) entityManager.find(JournalEntry.class, entryID);
      entry.setText(text);
      entry.setDate(date);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }

  public void deleteJournalEntry(Long entryId) {
    try {
      entityManager.getTransaction().begin();
      JournalEntry entry = (JournalEntry) entityManager.find(JournalEntry.class, entryId);
      entityManager.remove(entry);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }


}






