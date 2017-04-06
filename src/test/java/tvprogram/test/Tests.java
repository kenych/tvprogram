package tvprogram.test;

import tvprogram.domain.BroadcastInfo;
import tvprogram.domain.Episode;
import tvprogram.domain.Season;
import tvprogram.domain.Series;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public class Tests {
    public static final Series SERIES_1 = new Series("House M.D.", new Season(1, new Episode("Pilot", 1)));
    public static final Series SERIES_2 = new Series("House M.D.", new Season(1, new Episode("Paternity", 2)));
    public static final Series SERIES_3 = new Series("House M.D.", new Season(1, new Episode("Xxx", 2)));

    public static final LocalDate TOMORROW = now().plusDays(1);
    public static final LocalDate YESTERDAY = now().minusDays(1);
    public static final LocalDate AGES_AGO = now().minusDays(100);

    public static final BroadcastInfo BROADCASTING_TOMORROW_FIRST_BROADCASTING_TOMORROW = new BroadcastInfo(TOMORROW, TOMORROW, "UK");
    public static final BroadcastInfo BROADCASTING_YESTERDAY_FIRST_BROADCASTING_YESTERDAY = new BroadcastInfo(YESTERDAY, YESTERDAY, "UK");
    public static final BroadcastInfo BROADCASTING_TOMORROW_FIRST_BROADCASTING_AGES_AGO = new BroadcastInfo(TOMORROW, AGES_AGO, "UK");

}
