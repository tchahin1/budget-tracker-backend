package dtos;

public class IncomesAddDTO {
    private String name;

    private Double ammount;

    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
