package com.graduation_project.street2shelter.DTO;


import java.math.BigDecimal;

public class LongLatRequest {

    private BigDecimal longitude;
    private BigDecimal  latitude;

    public LongLatRequest(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
