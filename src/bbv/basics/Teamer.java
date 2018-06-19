package bbv.basics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEAMER")
public class Teamer extends AbstractPerson {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "TEAMERID")
  private long teamerId;
  
  @Column(name = "Position")
  private String position = "";  
  
  @Column(name = "TEAMERBILD")
  private String teamerBild = ""; 

  public Teamer() {
    
  }
  
  public Teamer(String name, String picture) {
    this.setName(name);
    this.teamerBild = picture;
  }
  
  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public long getTeamerId() {
    return teamerId;
  }

  public void setTeamerId(long teamerId) {
    this.teamerId = teamerId;
  }

  public String getPicture() {
    return this.teamerBild;
  } 
  
  public void setPicture(String bild){
    this.teamerBild = bild;
  }
  
}
