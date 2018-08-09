package au.com.normist.capital.domain.cap.salesforce;

import au.com.normist.capital.core.annotation.cap.PersistEntity;
import au.com.normist.capital.domain.cap.CapBaseModel;

import javax.persistence.Table;
import java.time.LocalDate;

@PersistEntity
@Table(name = "CRMHISTORY")
public class CrmHistory extends CapBaseModel {

    /// <summary>
    /// Product number (the length of string is 7)
    /// </summary>
    private String stockId;

    private String name;

    private String location;

    private String title;

    private String type;

    private String supplier;

    private String invoiceNo;

    private String lineRef;

    private LocalDate date;

    private String account;

    private String category;

    private String salesman;

    private String state;

    private String depsales;

    private String promo;

    private String origin;

    private String measure;

    private String qty;

    private Double cost;

    private Double soldFor;

    private Double taxCharge;

    private Double taxIncl;


    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getLineRef() {
        return lineRef;
    }

    public void setLineRef(String lineRef) {
        this.lineRef = lineRef;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDepsales() {
        return depsales;
    }

    public void setDepsales(String depsales) {
        this.depsales = depsales;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSoldFor() {
        return soldFor;
    }

    public void setSoldFor(Double soldFor) {
        this.soldFor = soldFor;
    }

    public Double getTaxCharge() {
        return taxCharge;
    }

    public void setTaxCharge(Double taxCharge) {
        this.taxCharge = taxCharge;
    }

    public Double getTaxIncl() {
        return taxIncl;
    }

    public void setTaxIncl(Double taxIncl) {
        this.taxIncl = taxIncl;
    }
}
