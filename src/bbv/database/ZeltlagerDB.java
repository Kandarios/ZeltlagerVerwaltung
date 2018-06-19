package bbv.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bbv.basics.Betreuer;
import bbv.basics.Teamer;
import bbv.basics.Teilnehmer;
import bbv.basics.Zelt;

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
  
  public void refresh(Object object) {
    entityManager.refresh(object);  
  }
  
  public boolean save(Object object) {
    try {
      entityManager.getTransaction().begin();
      entityManager.merge(object);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
    return true;
  }
  
  public void delete(Object object) {
    try {
      entityManager.getTransaction().begin();
      entityManager.remove(object);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }

  @SuppressWarnings("unchecked")
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
  
  @SuppressWarnings("unchecked")
  public List<Betreuer> getUnusedBetreuerList() {
    List<Betreuer> betreuerList = new ArrayList<Betreuer>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Betreuer where ZELTID is null ");
      betreuerList = query.getResultList();
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

  public void updateBetreuer(Long betreuerID, String newName, String newPicture) {
    try {
      System.out.print("Ist doch diese Funktion?");
      entityManager.getTransaction().begin();
      Betreuer betreuer = (Betreuer) entityManager.find(Betreuer.class, betreuerID);
      betreuer.setName(newName);
      betreuer.setPicture(newPicture);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<Teilnehmer> getUnusedTeilnehmerList(String gender) {
    List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Teilnehmer where ZELTID is null and GESCHLECHT='" + gender + "' order by ALTER");
      teilnehmerList = query.getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return teilnehmerList;
  }

  @SuppressWarnings("unchecked")
  public List<Teilnehmer> getAbgereistTeilnehmerList() {
    List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Teilnehmer where ABREISEDATE is not null");
      teilnehmerList = query.getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return teilnehmerList;
  }
  
  @SuppressWarnings("unchecked")
  public List<Teilnehmer> getUnterwegsTeilnehmerList() {
    List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Teilnehmer where ABWESENDZEIT is not null");
      teilnehmerList = query.getResultList();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
      entityManager.getTransaction().rollback();
    }
    return teilnehmerList;
  }
  
  
  public void updateTeilnehmerZelt(Long teilnehmerID, Long zeltID) {
    try {
      entityManager.getTransaction().begin();
      Teilnehmer teilnehmer = (Teilnehmer) entityManager.find(Teilnehmer.class, teilnehmerID);
      teilnehmer.setZeltId(zeltID);
      entityManager.getTransaction().commit();
      entityManager.flush();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }
  
  public void updateTeilnehmerAbreise(Long teilnehmerID, boolean abgereist, String date) {
    try {
      entityManager.getTransaction().begin();
      Teilnehmer teilnehmer = (Teilnehmer) entityManager.find(Teilnehmer.class, teilnehmerID);
      teilnehmer.setAbreiseDate(date);
      teilnehmer.setAbgereist(abgereist);
      entityManager.getTransaction().commit();
      entityManager.flush();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }
  
  //TODO: Do an update dynamically: get Teilnehmer form DB, override with Teilnehmer, save again.
  
  public void updateTeilnehmerUnterwegs(Long teilnehmerID, String time) {
    try {
      entityManager.getTransaction().begin();
      Teilnehmer teilnehmer = (Teilnehmer) entityManager.find(Teilnehmer.class, teilnehmerID);
      teilnehmer.setAbwesendZeit(time);
      entityManager.getTransaction().commit();
      entityManager.flush();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }
  
  public List<Teilnehmer> searchTeilnehmer(String name) {
    List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Teilnehmer where NAME LIKE '%" + name + "%'");
      teilnehmerList = query.getResultList();
      entityManager.getTransaction().commit();
      entityManager.flush();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
    return teilnehmerList;
  }
  
  public void updateBetreuerZelt(Long betreuerID, Long zeltID) {
    try {
      entityManager.getTransaction().begin();
      Betreuer betreuer = (Betreuer) entityManager.find(Betreuer.class, betreuerID);
      betreuer.setZeltId(zeltID);
      entityManager.getTransaction().commit();
      entityManager.flush();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
  }

  @SuppressWarnings("unchecked")
  public List<Zelt> getZeltList(String gender) {
    List<Zelt> zeltList = new ArrayList<Zelt>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Zelt  where GESCHLECHT='" + gender + "'");
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

  public List<Zelt> searchZelt(String name) {
    List<Zelt> teilnehmerList = new ArrayList<Zelt>();
    try {
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("from Zelt where NAME LIKE '%" + name + "%'");
      teilnehmerList = query.getResultList();
      entityManager.getTransaction().commit();
      entityManager.flush(); 
    } catch (Exception e) { 
      entityManager.getTransaction().rollback() ;
    }
    return teilnehmerList;
  }
 
  public List<Teamer> getTeamerList() {
    List<Teamer> betreuerList = new ArrayList<Teamer>();
    try {
      entityManager.getTransaction().begin();
      betreuerList = entityManager.createQuery("from Teamer").getResultList();
      entityManager.getTransaction().commit();
      return betreuerList;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
    }
    return betreuerList;
  }

  public void updateTeamer(long teamerId, String name) {
    // TODO Auto-generated method stub
    System.out.println(teamerId + " " + name);
  }
}