package bbv.basics;
import java.util.ArrayList;
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
  
  @Column(name = "BETREUERNAME")
  private String name = "";
  
  @Column(name = "BETREUERBILD")
  private String picturePath = ""; 
  
  @OneToMany(mappedBy = "betreuerID")
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
  private List<JournalEntry> jornalEntries = new ArrayList<JournalEntry>();
  
  @Column(name = "ZELTID", nullable=true)
  private Long zeltId = null;
  
  public Long getZeltId() {
    return zeltId;
  }

  public void setZeltId(Long zeltId) {
    this.zeltId = zeltId;
  }

  public Betreuer(String name, String bild) {
    this.name = name;
    this.picturePath = bild;
  }
  
  public Betreuer() {}
  
  public void addPicture(String path) {
    this.picturePath = path;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name =  name;
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
  
  public Long getBetreuerId() {
    return betreuerId;
  }
}
