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
  
  @Column(name = "ZELTID", nullable=true)
  private Long zeltId = null;

  @Column(name = "NOTIZ", columnDefinition="TEXT")
  private String notiz = "";
  
  @Column(name = "ABGEFAHREN")
  private boolean isAbgereist = false;
  
  @Column(name = "ABREISEDATE", nullable=true)
  private String abreiseDate = null;
  
  public Teilnehmer() {
    
  }
  
  public Teilnehmer(String name, String geschlecht, int alter, String wunsch) {
    this.name = name;
    this.geschlecht = geschlecht;
    this.wunsch = wunsch;
    this.alter = alter;
  }
   
  public Long getZeltId() {
    return zeltId;
  }

  public void setZeltId(Long zeltId) {
    this.zeltId = zeltId;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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
  
  public boolean isAbgereist() {
    return isAbgereist;
  }

  public void setAbgereist(boolean isAbgereist) {
    this.isAbgereist = isAbgereist;
  }

  public String getAbreiseDate() {
    return abreiseDate;
  }

  public void setAbreiseDate(String abreiseDate) {
    this.abreiseDate = abreiseDate;
  }
  
}
