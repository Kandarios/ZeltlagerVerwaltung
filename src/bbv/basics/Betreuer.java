package bbv.basics;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BETREUER")
public class Betreuer {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "BETREUERID")
  private long betreuertId;
  
  @Column(name = "BETREUERNAME")
  private String name = "";
  
  @Column(name = "BETREUERZELT")
  private String zelt = "";
  
  @Column(name = "BETREUERBILD")
  private String picturePath = ""; 
  
  public Betreuer(String name, String zelt, String bild) {
    this.name = name;
    this.zelt = zelt;
    this.picturePath = bild;
  }
  
  public Betreuer() {
    
  }
  
  public void setBetrteuerId(long betreuertId) {
    this.betreuertId = betreuertId;
  }
  
  public long getBetrteuerId() {
    return betreuertId;
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
}
