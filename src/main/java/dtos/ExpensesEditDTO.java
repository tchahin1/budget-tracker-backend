package dtos;

public class ExpensesEditDTO {
    private String name;

    private Double ammount;

    private String userId;

    private Long categoryId;

    private Long id;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
