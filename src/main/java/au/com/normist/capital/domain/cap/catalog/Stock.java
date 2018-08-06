package au.com.normist.capital.domain.cap.catalog;

import au.com.normist.capital.domain.cap.CapBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "STOCK")
public class Stock extends CapBaseModel {

    /**
     * product code
     */
    @Column(name = "NAME")
    private String productCode;

    /**
     * product short description - 44 characters
     */
    @Column(name = "TITLE")
    private String shortDescription;

    /**
     * product secondary name
     */
    @Column(name = "NAME2")
    private String name2;

    /**
     * current stock level
     */
    @Column(name = "STOCK_IN")
    private String stockIn;

    /**
     * bin location
     */
    @Column(name = "BIN")
    private String bin;

    /**
     * On order stock level
     */
    @Column(name = "ON_ORDER")
    private String onOrderStock;

    /**
     * Allocated sotck
     */
    @Column(name = "ALLOCATED")
    private String allocatedStock;

    /**
     * Product standard cost price Ex.
     */
    @Column(name = "A")
    private BigDecimal cost;

    /**
     * Product standard sell price Ex. GST
     */
    @Column(name = "C")
    private BigDecimal sellPrice;

    /**
     * Product group
     */
    @Column(name = "TYPE")
    private String productGroup;

    /**
     * Tax number
     */
    @Column(name = "TAXRATE")
    private String taxNumber;

    /**
     * stock id
     */
    @Column(name = "NUMBER", unique = true)
    @Id
    private String stockId;

    /**
     * product unit measure
     */
    @Column(name = "MEASURE")
    private String unitMeasure;

    /**
     * product location
     */
    @Column(name = "LOCATION")
    private String location;

    /**
     * Indicates if a product is currently on hold.
     */
    @Column(name = "HOLD")
    private Boolean isOnHold;

    /**
     * Product weight
     */
    @Column(name = "WEIGHT")
    private BigDecimal weight;

    /**
     * Indicate if the product is diminishing.
     */
    @Column(name = "DIMINISH")
    private Boolean isDiminishing;

    /**
     * Volume
     */
    @Column(name = "VOLUME")
    private BigDecimal volume;

    /**
     * Primary supplier code - System field
     */
    @Column(name = "SUPPLIER")
    private String primarySupplierCode;


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getStockIn() {
        return stockIn;
    }

    public void setStockIn(String stockIn) {
        this.stockIn = stockIn;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getOnOrderStock() {
        return onOrderStock;
    }

    public void setOnOrderStock(String onOrderStock) {
        this.onOrderStock = onOrderStock;
    }

    public String getAllocatedStock() {
        return allocatedStock;
    }

    public void setAllocatedStock(String allocatedStock) {
        this.allocatedStock = allocatedStock;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getOnHold() {
        return isOnHold;
    }

    public void setOnHold(Boolean onHold) {
        isOnHold = onHold;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getDiminishing() {
        return isDiminishing;
    }

    public void setDiminishing(Boolean diminishing) {
        isDiminishing = diminishing;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getPrimarySupplierCode() {
        return primarySupplierCode;
    }

    public void setPrimarySupplierCode(String primarySupplierCode) {
        this.primarySupplierCode = primarySupplierCode;
    }
}
