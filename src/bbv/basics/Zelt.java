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
@Table(name="ZELT")
public class Zelt {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ZELTID")
  private long zeltId;
  
  @Column(name = "NAME")
  private String name = "";
  
  @Column(name = "NOTIZ", columnDefinition="TEXT")
  private String notiz = "";
  
  @OneToMany(mappedBy = "zeltId")
  @Cascade({CascadeType.SAVE_UPDATE})
  private List<Betreuer> betreuerList = new ArrayList<Betreuer>();

  @OneToMany(mappedBy = "zeltId")
  @Cascade({CascadeType.SAVE_UPDATE})
  private List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
  
  public Zelt() {
    
  }
  
  public Zelt(String name) {
    this.name = name;
  }
  
  public long getZeltId() {
    return zeltId;
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
  
  public List<Betreuer> getBetreuerList() {
    return betreuerList;
  }
  
  public void insterBetreuer(Betreuer b, int i) {
    betreuerList.add(i, b);
  }

  public List<Teilnehmer> getTeilnehmerList() {
    return teilnehmerList;
  }
  
  public void insterTeilnehmer(Teilnehmer t, int i) {
    teilnehmerList.add(i, t);
  }
  
  public void removeTeilnehmer(Teilnehmer teilnehmer) {
    teilnehmerList.remove(teilnehmer);
  }
  
  public void removeBetreuer(Betreuer betreuer) {
    betreuerList.remove(betreuer);
  }

  public void removeBetreuerAt(int index0) {
    betreuerList.remove(index0);
  }
}
