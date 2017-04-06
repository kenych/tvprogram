package tvprogram.domain;

public class Series {
    private String name;
    private Season season;

    public Series() {
    }

    public Series(String name, Season season) {
        this.name = name;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    @Override
    public String toString() {
        return "Series{" +
                "name='" + name + '\'' +
                ", season=" + season +
                '}';
    }
}
