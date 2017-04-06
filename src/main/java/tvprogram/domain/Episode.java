package tvprogram.domain;

public class Episode {
    private Integer number;
    private String name;

    public Episode(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public Episode() {
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }


    @Override
    public String toString() {
        return "Episode{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
