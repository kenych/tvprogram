package tvprogram.domain;

public class Season {
    private Integer number;
    private Episode episode;

    public Season() {
    }

    public Season(Integer number, Episode episode) {
        this.number = number;
        this.episode = episode;
    }

    public Integer getNumber() {
        return number;
    }

    public Episode getEpisode() {
        return episode;
    }

    @Override
    public String toString() {
        return "Season{" +
                "number=" + number +
                ", episode=" + episode +
                '}';
    }
}
