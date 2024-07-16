package com.example.assignment_1;

import java.util.concurrent.ThreadLocalRandom;

public class Category {

    private final String CATEGORY_ID;
    private String categoryName;
    private int  eventCount;
    private boolean isActive;

    public Category(String categoryName, int eventCount, boolean isActive) {
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.CATEGORY_ID = generateCategoryID();
    }

    public String getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private String generateCategoryID()
    {
        String categoryID;
        categoryID = "C";

        categoryID += Utility.getRandomLetter();
        categoryID += Utility.getRandomLetter();
        categoryID += "-";
        categoryID += ThreadLocalRandom.current().nextInt(1000,9999);

        return categoryID;

    }

}
