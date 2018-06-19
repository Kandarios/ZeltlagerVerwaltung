package bbv.basics;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEILNEHMER")
public class Teilnehmer extends AbstractPerson {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "TEILNEHMERID")
  private long teilnehmerId;
  
  @Column(name = "GESCHLECHT")
  private String geschlecht = "";
  
  @Column(name = "WUNSCH")
  private String wunsch = "";

  @Column(name = "BESONDERHEIT")
  private String besonderheit = "";

  @Column(name = "BADEN")
  private String baden = "";
  
  @Column(name = "FOTO")
  private String foto = "";
  
  @Column(name = "UNVERTRÄGLICHKEIT")
  private String unverträglichkeit = "";
  
  @Column(name = "ALTER")
  private int alter = 0;

  @Column(name = "MEDIKAMENTE")
  private String medikamente = "";  
  
  public Teilnehmer() {}
  
  public Teilnehmer(String name, String geschlecht, int alter, String wunsch) {
    super(name);
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
  
  public int getAlter() {
    return this.alter;
  }
  
  public void setAlter(int alter) {
    this.alter = alter;
  }
  
  public String toString() {
    return super.getName();
  }

  public String getBesonderheit() {
    return besonderheit;
  }

  public void setBesonderheit(String besonderheit) {
    this.besonderheit = besonderheit;
  }

  public String getFoto() {
    return foto;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public String getUnverträglichkeit() {
    return unverträglichkeit;
  }

  public void setUnverträglichkeit(String unverträglichkeit) {
    this.unverträglichkeit = unverträglichkeit;
  }

  public void setTeilnehmerId(long teilnehmerId) {
    this.teilnehmerId = teilnehmerId;
  }

  public String getBaden() {
    return baden;
  }

  public void setBaden(String baden) {
    this.baden = baden;
  }

  public String getMedikamente() {
    return medikamente;
  }

  public void setMedikamente(String medikamente) {
    this.medikamente = medikamente;
  }
  
}
