package bbv.basics;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JOURNALENTRY")
public class JournalEntry {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ENTRYID")
  private long entryId;
  
  @Column(name = "BETREUERID")
  private Long betreuerID = 0L;
  
  @Column(name = "DATE")
  private String date = "";
  
  @Column(name = "TEXT", columnDefinition="TEXT")
  private String text = "";
  
  public JournalEntry() {
    
  }
  
  public JournalEntry(Long betreuerID, String date, String text) {
    this.betreuerID = betreuerID;
    this.text = text;
    this.date = date;
  } 
  
  public long getEntryId() {
    return entryId;
  }

  public void setEntryId(long entryId) {
    this.entryId = entryId;
  }

  public Long getBetreuerID() {
    return betreuerID;
  }

  public void setBetreuerID(Long betreuerID) {
    this.betreuerID = betreuerID;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
