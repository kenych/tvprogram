package tvprogram.service;

import tvprogram.domain.BroadcastFiler;
import tvprogram.domain.WeeklyNewProgramme;

import java.util.List;

public interface ProgrammeService {
    List<WeeklyNewProgramme> weeklyNewProgrammes(BroadcastFiler broadcastFiler);
}
