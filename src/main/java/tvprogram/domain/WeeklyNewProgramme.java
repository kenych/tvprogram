package tvprogram.domain;

public class WeeklyNewProgramme {

    private Series series;

    public WeeklyNewProgramme() {
    }

    public WeeklyNewProgramme(Series series) {
        this.series = series;
    }

    public Series getSeries() {
        return series;
    }


    @Override
    public String toString() {
        return "WeeklyNewProgramme{" +
                "series=" + series +
                '}';
    }
}
