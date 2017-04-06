package tvprogram.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class BroadcastInfo {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate broadcastDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate firstBroadcastDate;

    private String region;

    public BroadcastInfo() {
    }

    public BroadcastInfo(LocalDate broadcastDate, LocalDate firstBroadcastDate, String region) {
        this.broadcastDate = broadcastDate;
        this.firstBroadcastDate = firstBroadcastDate;
        this.region = region;
    }

    public LocalDate getBroadcastDate() {
        return broadcastDate;
    }

    public LocalDate getFirstBroadcastDate() {
        return firstBroadcastDate;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "BroadcastInfo{" +
                "broadcastDate=" + broadcastDate +
                ", firstBroadcastDate=" + firstBroadcastDate +
                ", region='" + region + '\'' +
                '}';
    }
}
