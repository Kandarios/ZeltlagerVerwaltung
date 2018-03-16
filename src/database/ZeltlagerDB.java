package database;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bbv.basics.Betreuer;
import bbv.basics.JournalEntry;
import bbv.basics.Teilnehmer;
import bbv.basics.Zelt;

/**
 *
 * @author sqlitetutorial.net
 */
public class ZeltlagerDB {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();
  private static ZeltlagerDB instance = null;

  private ZeltlagerDB() {
  }

  public static ZeltlagerDB getInstance() {
    if(instance == null) {
      instance = new ZeltlagerDB();
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
  
  
  public boolean saveTeilnehmer(Teilnehmer  teilnehmer) {
    try {
      entityManager.getTransaction().begin();
      entityManager.merge(teilnehmer);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return true;
  }

  public List<Teilnehmer> getTeilnehmerOfBetreuer(Long betreuerID) {
    List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Teilnehmer where BETREUERID = :id ");
      query.setParameter("id", betreuerID);
      teilnehmerList = query.getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return teilnehmerList;
  }
  
  public List<Teilnehmer> getTeilnehmer() {
    List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Teilnehmer");
      teilnehmerList = query.getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return teilnehmerList;
  }

  public void updateTeilnehmer(Long teilnehmerID, String name, String notiz, Long betreuerID) {
    try {
      entityManager.getTransaction().begin();
      Teilnehmer teilnehmer = (Teilnehmer) entityManager.find(Teilnehmer.class, teilnehmerID);
      teilnehmer.setName(name);
      teilnehmer.setNotiz(notiz);
      teilnehmer.setBetreuerID(betreuerID);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }

  public void deleteTeilnehmer(Long teilnehmerId) {
    try {
      entityManager.getTransaction().begin();
      Teilnehmer teilnehmer = (Teilnehmer) entityManager.find(Teilnehmer.class, teilnehmerId);
      entityManager.remove(teilnehmer);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }
  
  public Teilnehmer getTeilnehmer(Long teilnehmerID) {
    Teilnehmer teilnehmer = new Teilnehmer();
     try {
       entityManager.getTransaction().begin();
       teilnehmer = (Teilnehmer) entityManager.find(Teilnehmer.class, teilnehmerID);
       entityManager.getTransaction().commit();
     } catch (Exception e) {
       entityManager.getTransaction().rollback();
     }
     return teilnehmer;
   }
  
  public boolean saveZelt(Zelt zelt) {
    try {
      entityManager.getTransaction().begin();
      entityManager.merge(zelt);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return true;
  }

  public List<Zelt> getZeltList() {
    List<Zelt> zeltList = new ArrayList<Zelt>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Zelt");
      zeltList = query.getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return zeltList;
  }
  
  public Zelt getZelt(Long zeltID) {
    Zelt zelt = new Zelt();
     try {
       entityManager.getTransaction().begin();
       zelt = (Zelt) entityManager.find(Zelt.class, zeltID);
       entityManager.getTransaction().commit();
     } catch (Exception e) {
       entityManager.getTransaction().rollback();
     }
     return zelt;
   }

  public void updateZelt(Long zeltID, String name) {
    try {
      entityManager.getTransaction().begin();
      Zelt zelt = (Zelt) entityManager.find(Zelt.class, zeltID);
      zelt.setName(name);
//      zelt.setDate(date);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }

  public void deleteZelt(Long zeltId) {
    try {
      entityManager.getTransaction().begin();
      Zelt zelt = (Zelt) entityManager.find(Zelt.class, zeltId);
      entityManager.remove(zelt);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }
  
  public boolean saveJournalEntry(JournalEntry entry) {
    try {
      entityManager.getTransaction().begin();
      entityManager.merge(entry);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return true;
  }

  public List<JournalEntry> getJournalEntryList(Long betreuerID) {
    List<JournalEntry> journalEntryList = new ArrayList<JournalEntry>();
    try {
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