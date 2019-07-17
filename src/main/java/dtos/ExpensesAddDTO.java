package dtos;

import com.example.moneytracker.models.Category;
import com.example.moneytracker.models.Users;

public class ExpensesAddDTO {
    private String name;

    private Double ammount;

    private String userId;

    private Long categoryId;

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
}
