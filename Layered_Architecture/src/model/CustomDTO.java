package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomDTO {
    private String CustomerId;
    private String CustomerName;
    private String address;
    private String ItemCode;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
    private String orderId;
    private LocalDate orderDate;
    private BigDecimal orderTotal;
    private int qty;

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getAddress() {
        return address;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public int getQty() {
        return qty;
    }
}
