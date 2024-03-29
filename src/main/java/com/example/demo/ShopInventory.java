package com.example.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class ShopInventory {

  @Id
  @GeneratedValue
  private Long id;

  private String productName;
  private String productDescription;
  private Double productPrice;
  private Double productQuantity;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getProductName() {
    return productName;
  }
  public void setProductName(String productName) {
    this.productName = productName;
  }
  public String getProductDescription() {
    return productDescription;
  }
  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }
  public Double getProductPrice() {
    return productPrice;
  }
  public void setProductPrice(Double productPrice) {
    this.productPrice = productPrice;
  }
  public Double getProductQuantity() {
    return productQuantity;
  }
  public void setProductQuantity(Double productQuantity) {
    this.productQuantity = productQuantity;
  }

  
  
}
