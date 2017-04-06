package tvprogram.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static java.time.LocalDate.now;

public class Programme {
    private Series series;
    private BroadcastInfo broadcastInfo;

    public Programme() {
    }

    public Programme(Series series, BroadcastInfo broadcastInfo) {
        this.series = series;
        this.broadcastInfo = broadcastInfo;
    }

    public Series getSeries() {
        return series;
    }

    public BroadcastInfo getBroadcastInfo() {
        return broadcastInfo;
    }

    @JsonIgnore
    public boolean isNew() {
        return broadcastInfo.getFirstBroadcastDate().isEqual(broadcastInfo.getBroadcastDate());
    }

    @JsonIgnore
    public boolean isNotShown() {
        return broadcastInfo.getBroadcastDate().isAfter(now());
    }

    @Override
    public String toString() {
        return "Programme{" +
                "series=" + series +
                ", broadcastInfo=" + broadcastInfo +
                '}';
    }
}
