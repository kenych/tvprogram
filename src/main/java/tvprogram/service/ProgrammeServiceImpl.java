package tvprogram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tvprogram.domain.BroadcastFiler;
import tvprogram.domain.Programme;
import tvprogram.domain.WeeklyNewProgramme;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static tvprogram.domain.BroadcastFiler.NOT_SHOWN;

public class ProgrammeServiceImpl implements ProgrammeService {
    private static final Logger logger = LoggerFactory.getLogger(ProgrammeServiceImpl.class);

    private BroadcastService broadcastService;

    public ProgrammeServiceImpl(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @Override
    public List<WeeklyNewProgramme> weeklyNewProgrammes(BroadcastFiler broadcastFiler) {
        List<Programme> programmes = broadcastService.weeklyProgrammes();
        logger.debug("weekly programs: {}", programmes);

        Stream<Programme> filteredStream = programmes.stream()
                .filter(weeklyProgramme -> weeklyProgramme.isNew());

        if (broadcastFiler == NOT_SHOWN) {
            filteredStream = filteredStream.filter(weeklyNewProgramme -> weeklyNewProgramme.isNotShown());
        }

        List<WeeklyNewProgramme> weeklyNewProgrammes = filteredStream
                .map(weeklyProgramme -> new WeeklyNewProgramme(weeklyProgramme.getSeries()))
                .collect(toList());

        logger.debug("weekly new programs: {}", weeklyNewProgrammes);

        return weeklyNewProgrammes;
    }

}
