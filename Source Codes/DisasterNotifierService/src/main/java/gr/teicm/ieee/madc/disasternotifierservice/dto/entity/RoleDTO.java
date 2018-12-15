package gr.teicm.ieee.madc.disasternotifierservice.dto.entity;

public class RoleDTO extends BaseEntityDTO {

    private String name;

    public RoleDTO() {
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
