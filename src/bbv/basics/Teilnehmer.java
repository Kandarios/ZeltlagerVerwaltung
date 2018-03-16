package bbv.basics;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Teilnehmer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "TEILNEHMERID")
  private long teilnehmerId;
  
  @Column(name = "BETREUERID")
  private Long betreuerID = 0L;
  
  @Column(name = "ZELTID")
  private Long zeltID = 0L;
  
  @Column(name = "NAME")
  private String name = "";
  
  @Column(name = "STRASSE")
  private String strasse = "";
  
  @Column(name = "ORT")
  private String ort = "";
  
  @Column(name = "NOTIZ", columnDefinition="TEXT")
  private String notiz = "";
  
  public Teilnehmer() {
    
  }
  
  public Teilnehmer(String name, String strasse, String ort, long betreuerID, long zeltID) {
    this.name = name;
    this.strasse = strasse;
    this.ort = ort;
    this.betreuerID = betreuerID;
    this.zeltID = zeltID;
  }
  
  
  public long getTeilnehmerId() {
    return teilnehmerId;
  }

  public void setTeilnehmerId(long teilnehmerId) {
    this.teilnehmerId = teilnehmerId;
  }

  public Long getBetreuerID() {
    return betreuerID;
  }

  public void setBetreuerID(Long betreuerID) {
    this.betreuerID = betreuerID;
  }

  public Long getZeltID() {
    return zeltID;
  }

  public void setZeltID(Long zeltID) {
    this.zeltID = zeltID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStrasse() {
    return strasse;
  }

  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getNotiz() {
    return notiz;
  }

  public void setNotiz(String notiz) {
    this.notiz = notiz;
  }


  
}
