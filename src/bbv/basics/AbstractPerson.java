package bbv.basics;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractPerson {

  @Column()
  private boolean isAbgereist = false;
  
  @Column(nullable=true)
  private String abreiseDate = null;

  @Column()
  private boolean isAbwesend = false;
  
  @Column(nullable=true)
  private String abwesendZeit = null;

  @Column()
  private String name = "";
    
  @Column(nullable=true)
  private Long zeltId = null;
  
  @Column()
  private String telefon = "";
  
  public AbstractPerson() {  }
  
  public AbstractPerson(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public boolean isAbwesend() {
    return isAbwesend;
  }

  public void setAbwesend(boolean isAbwesend) {
    this.isAbwesend = isAbwesend;
  }

  public String getAbwesendZeit() {
    return abwesendZeit;
  }

  public void setAbwesendZeit(String abwesendZeit) {
    this.abwesendZeit = abwesendZeit;
  }

  public Long getZeltId() {
    return zeltId;
  }

  public void setZeltId(Long zeltId) {
    this.zeltId = zeltId;
  }

  public String getTelefon() {
    return telefon;
  }

  public void setTelefon(String telefon) {
    this.telefon = telefon;
  }
  
}
