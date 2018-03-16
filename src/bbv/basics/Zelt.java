package bbv.basics;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Zelt {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ZELTID")
  private long zeltId;
  
  @Column(name = "NAME")
  private String name = "";
  
  @Column(name = "NOTIZ", columnDefinition="TEXT")
  private String notiz = "";
  
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
