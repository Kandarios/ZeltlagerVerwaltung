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
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
  private List<Betreuer> betreuerList;
  
  @OneToMany(mappedBy = "zeltId")
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
  private List<Teilnehmer> teilnehmerList;
  
  public Zelt() {
    
  }
  
  public Zelt(String name) {
    this.name = name;
  }
  
  public long getZeltId() {
    return zeltId;
  }

  public void setZeltId(long zeltId) {
    this.zeltId = zeltId;
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
}
