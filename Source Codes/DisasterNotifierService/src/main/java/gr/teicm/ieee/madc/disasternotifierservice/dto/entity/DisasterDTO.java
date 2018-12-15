package gr.teicm.ieee.madc.disasternotifierservice.dto.entity;

import gr.teicm.ieee.madc.disasternotifierservice.dto.embeddable.LocationDTO;

public class DisasterDTO extends BaseEntityDTO {

    private String disasterType;

    private LocationDTO disasterLocation;

    private LocationDTO safeLocation;

    private boolean active;

    private Long redRadius;
    private Long yellowRadius;
    private Long greenRadius;

    private UserDTO creator;

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public LocationDTO getDisasterLocation() {
        return disasterLocation;
    }

    public void setDisasterLocation(LocationDTO disasterLocation) {
        this.disasterLocation = disasterLocation;
    }

    public LocationDTO getSafeLocation() {
        return safeLocation;
    }

    public void setSafeLocation(LocationDTO safeLocation) {
        this.safeLocation = safeLocation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getRedRadius() {
        return redRadius;
    }

    public void setRedRadius(Long redRadius) {
        this.redRadius = redRadius;
    }

    public Long getYellowRadius() {
        return yellowRadius;
    }

    public void setYellowRadius(Long yellowRadius) {
        this.yellowRadius = yellowRadius;
    }

    public Long getGreenRadius() {
        return greenRadius;
    }

    public void setGreenRadius(Long greenRadius) {
        this.greenRadius = greenRadius;
    }

    public UserDTO getCreator() {
        return creator;
    }

    public void setCreator(UserDTO creator) {
        this.creator = creator;
    }
}
