package com.example.ganesh.hotelmanageapp.OrderFood;

public class Food {


    private String fileName;
    private int price, quantity;
    private boolean availability;
    private String name;
    private String size;
    private String orderDateTime,dateTime;

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public Food(){
    }

    public Food(String name, String size, int price, boolean availability, String filePath) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.availability = availability;
        this.fileName = filePath;
    }

    public Food(String name, String size, int price, boolean availability) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.availability = availability;

    }


    public String getFilePath() {
        return fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
