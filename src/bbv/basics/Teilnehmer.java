package bbv.basics;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEILNEHMER")
public class Teilnehmer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "TEILNEHMERID")
  private long teilnehmerId;
  
  @Column(name = "NAME")
  private String name = "";

  @Column(name = "GESCHLECHT")
  private String geschlecht = "";
  
  @Column(name = "WUNSCH")
  private String wunsch = "";

  @Column(name = "ALTER")
  private int alter = 0;
  
  @Column(name = "ZELTID")
  private Long zeltId = 0L;
  
  @Column(name = "NOTIZ", columnDefinition="TEXT")
  private String notiz = "";
  
  public Teilnehmer() {
    
  }
  
  public Teilnehmer(String name, String geschlecht, int alter, String wunsch) {
    this.name = name;
    this.geschlecht = geschlecht;
    this.wunsch = wunsch;
    this.alter = alter;
  }
  
  
  public String getGeschlecht() {
    return geschlecht;
  }

  public void setGeschlecht(String geschlecht) {
    this.geschlecht = geschlecht;
  }

  public String getWunsch() {
    return wunsch;
  }

  public void setWunsch(String wunsch) {
    this.wunsch = wunsch;
  }
  
  public long getTeilnehmerId() {
    return teilnehmerId;
  }

  public void setTeilnehmerId(long teilnehmerId) {
    this.teilnehmerId = teilnehmerId;
  }

//  public Long getBetreuerID() {
//    return betreuerID;
//  }
//
//  public void setBetreuerID(Long betreuerID) {
//    this.betreuerID = betreuerID;
//  }
//
//  public Long getZeltID() {
//    return zeltID;
//  }
//
//  public void setZeltID(Long zeltID) {
//    this.zeltID = zeltID;
//  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public String getStrasse() {
//    return strasse;
//  }
//
//  public void setStrasse(String strasse) {
//    this.strasse = strasse;
//  }
//
//  public String getOrt() {
//    return ort;
//  }
//
//  public void setOrt(String ort) {
//    this.ort = ort;
//  }

  public String getNotiz() {
    return notiz;
  }

  public void setNotiz(String notiz) {
    this.notiz = notiz;
  }

  public int getAlter() {
    return this.alter;
  }
  
  public void setAlter(int alter) {
    this.alter = alter;
  }
  
}
