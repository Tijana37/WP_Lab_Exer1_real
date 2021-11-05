package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;

@Repository
public class ActiveSessionsRepository {
    public ActiveSessionsRepository() {
    }
    public int getAllSessions(){
        return DataHolder.activeSessions;
    }
   public void addSession(){
        DataHolder.activeSessions++;
   }
   public void deleteSession(){
        DataHolder.activeSessions--;
   }
}
