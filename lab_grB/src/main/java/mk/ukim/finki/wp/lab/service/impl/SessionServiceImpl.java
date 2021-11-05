package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.repository.ActiveSessionsRepository;
import mk.ukim.finki.wp.lab.service.SessionService;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    public final ActiveSessionsRepository activeSessionsRepository;

    public SessionServiceImpl(ActiveSessionsRepository activeSessionsRepository) {
        this.activeSessionsRepository = activeSessionsRepository;
    }

    @Override
    public void addNewSession() {
        activeSessionsRepository.addSession();
    }


    @Override
    public int getNumber() {
        return activeSessionsRepository.getAllSessions();
    }

    @Override
    public void deleteSession() {
        this.activeSessionsRepository.deleteSession();
    }
}
