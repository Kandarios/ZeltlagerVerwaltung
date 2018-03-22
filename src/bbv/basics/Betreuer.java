package bbv.basics;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="BETREUER")
public class Betreuer {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "BETREUERID")
  private long betreuerId;
  
  public long getBetreuerId() {
    return betreuerId;
  }

  @Column(name = "BETREUERNAME")
  private String name = "";
  
  @Column(name = "BETREUERZELT")
  private String zelt = "";
  
  @Column(name = "BETREUERBILD")
  private String picturePath = ""; 
  
  @OneToMany(mappedBy = "betreuerID")
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
  private List<JournalEntry> jornalEntries;
  
  @Column(name = "ZELTID", nullable=true)
  private Long zeltId = 0L;
  
  public Long getZeltId() {
    return zeltId;
  }

  public void setZeltId(Long zeltId) {
    this.zeltId = zeltId;
  }

  public Betreuer(String name, String zelt, String bild) {
    this.name = name;
    this.zelt = zelt;
    this.picturePath = bild;
  }
  
  public Betreuer() {
    
  }
  
  public void addPicture(String path) {
    this.picturePath = path;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name =  name;
  }
  
  public String getZelt() {
    return zelt;
  }
  
  public void setZelt(String zelt) {
    this.zelt =  zelt;
  }
  
  public String getPicture() {
    return picturePath;
  }
  
  public void setPicture(String path) {
    this.picturePath = path;
  }
  
  public List<JournalEntry> getJournalEntries() {
    return jornalEntries;
  }
  
  public String toString() {
    return name;
  }
}
