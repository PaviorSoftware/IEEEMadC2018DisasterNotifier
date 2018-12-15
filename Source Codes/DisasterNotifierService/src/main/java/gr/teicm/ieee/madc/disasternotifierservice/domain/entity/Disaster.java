package gr.teicm.ieee.madc.disasternotifierservice.domain.entity;

import gr.teicm.ieee.madc.disasternotifierservice.domain.embeddable.Location;
import gr.teicm.ieee.madc.disasternotifierservice.domain.enumerator.DisasterType;
import io.micrometer.core.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "disasters")
public class Disaster extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private DisasterType disasterType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "disaster_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "disaster_longitude"))
    })
    private Location disasterLocation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "safe_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "safe_longitude"))
    })
    private Location safeLocation;

    private Boolean active;

    @NonNull
    private Long redRadius;
    private Long yellowRadius;
    private Long greenRadius;

    @ManyToOne(
            cascade = CascadeType.ALL,
            targetEntity = User.class,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(name = "creator_id")
    private User creator;

    public Disaster() {
    }

    public Disaster(DisasterType disasterType, Location disasterLocation, Location safeLocation, Boolean active, Long redRadius, Long yellowRadius, Long greenRadius, User creator) {
        this.disasterType = disasterType;
        this.disasterLocation = disasterLocation;
        this.safeLocation = safeLocation;
        this.active = active;
        this.redRadius = redRadius;
        this.yellowRadius = yellowRadius;
        this.greenRadius = greenRadius;
        this.creator = creator;
    }

    public DisasterType getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(DisasterType disasterType) {
        this.disasterType = disasterType;
    }

    public Location getDisasterLocation() {
        return disasterLocation;
    }

    public void setDisasterLocation(Location disasterLocation) {
        this.disasterLocation = disasterLocation;
    }

    public Location getSafeLocation() {
        return safeLocation;
    }

    public void setSafeLocation(Location safeLocation) {
        this.safeLocation = safeLocation;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Disaster)) {
            return false;
        }

        Disaster tmp = (Disaster) obj;

        return this.getId().equals(tmp.getId());
    }
}
