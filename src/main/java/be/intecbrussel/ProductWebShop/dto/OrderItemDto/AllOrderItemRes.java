package be.intecbrussel.ProductWebShop.dto.OrderItemDto;

public class AllOrderItemRes {

    private String useremail;
    private String productName;
    private double productPrice;
    private double quantity;

    public AllOrderItemRes(String useremail, String productName, double productPrice, double quantity) {
        this.useremail = useremail;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }
}
