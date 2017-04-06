package tvprogram.service;

import tvprogram.domain.CommunicationException;
import tvprogram.domain.Programme;

import java.util.List;

public interface BroadcastService {
    List<Programme> weeklyProgrammes() throws CommunicationException;
}
