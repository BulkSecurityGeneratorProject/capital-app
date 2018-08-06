package au.com.normist.capital.domain.cap.catalog;

import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "STAUX000")
public class Staux000 {


    /// <summary>
    /// The product number (the length of string is 7)
    /// </summary>
    @Column(name = "AUXKEY", unique = true)
    private String auxKey;

    /// <summary>
    /// Product key = product code + location
    /// </summary>
    @Column(name = "KEY")
    @Id
    private String key;

    /// <summary>
    /// Primary supplier code
    /// </summary>
    @Column(name = "S1CODE")
    private String supp1Code;

    /// <summary>
    /// Product primary supplier part #
    /// </summary>
    @Column(name = "S1PARTNO")
    private String supp1PartNo;

    /// <summary>
    /// Primary supplier part description
    /// </summary>
    @Column(name = "S1DESC")
    private String supp1PartDescription;

    /// <summary>
    /// Pack Qty
    /// </summary>
    @Column(name = "S1PACKQTY")
    private Integer packQty;

    /// <summary>
    /// 2nd supplier code
    /// </summary>
    @Column(name = "S2CODE")
    private String supp2Code;

    /// <summary>
    /// 3rd supplier code
    /// </summary>
    @Column(name = "S3CODE")
    private String supp3Code;

    /// <summary>
    /// 4th supplier code
    /// </summary>
    @Column(name = "S4CODE")
    private String supp4Code;

    /// <summary>
    /// 2nd supplier part #
    /// </summary>
    @Column(name = "S2PARTNO")
    private String supp2PartNo;

    /// <summary>
    /// 3rd supplier part #
    /// </summary>
    @Column(name = "S3PARTNO")
    private String supp3PartNo;

    /// <summary>
    /// 4th supplier part #
    /// </summary>
    @Column(name = "S4PARTNO")
    private String supp4PartNo;

    /// <summary>
    /// Product full description
    /// </summary>
    @Column(name = "SALESNOTE")
    private String fullDescription;

    /// <summary>
    /// Product image location
    /// </summary>
    @Column(name = "IMAGELOC")
    private String imageLocation;

    /// <summary>
    /// Freight cost
    /// </summary>
    @Column(name = "FRTCST")
    private BigDecimal freight;


    // Catalogue section

    /// <summary>
    /// First Catelogue Code
    /// </summary>
    @Column(name = "CATCODE")
    private String catalogue1Code;

    /// <summary>
    /// Catalogue 1 name
    /// </summary>
    @Column(name = "CATNAME")
    private String catalogue1Name;

    /// <summary>
    /// Catalogue 1 Start Date
    /// </summary>
    @Column(name = "SPECIALON")
    private LocalDate catalogue1StartDate;

    /// <summary>
    /// Catalogue 1 End Date
    /// </summary>
    @Column(name = "SPECIALOFF")
    private LocalDate catalogue1EndDate;

    /// <summary>
    /// Catelogue 1 Sell price
    /// </summary>
    @Column(name = "SPC")
    private BigDecimal catalogue1SellPrice;

    /// <summary>
    /// Catalogue 1 Cost price
    /// </summary>
    @Column(name = "SPA")
    private BigDecimal catalogue1Cost;

    /// <summary>
    /// 2nd Catelogue Code
    /// </summary>
    @Column(name = "SCATCODE")
    private String catalogue2Code;

    /// <summary>
    /// Catalogue 2 name
    /// </summary>
    @Column(name = "SCATNAME")
    private String catalogue2Name;

    /// <summary>
    /// Catalogue 2 Start Date
    /// </summary>
    @Column(name = "SSPECIALON")
    private LocalDate catalogue2StartDate;

    /// <summary>
    /// Catalogue 2 End Date
    /// </summary>
    @Column(name = "SSPECIALOF")
    private LocalDate catalogue2EndDate;

    /// <summary>
    /// Catalogue 2 Sell price
    /// </summary>
    @Column(name = "SSPC")
    private BigDecimal catalogue2SellPrice;

    /// <summary>
    /// Catalogue 2 Cost price
    /// </summary>
    @Column(name = "SSPA")
    private BigDecimal catalogue2Cost;

    // end catalogue section


    public String getAuxKey() {
        return auxKey;
    }

    public void setAuxKey(String auxKey) {
        this.auxKey = auxKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSupp1Code() {
        return supp1Code;
    }

    public void setSupp1Code(String supp1Code) {
        this.supp1Code = supp1Code;
    }

    public String getSupp1PartNo() {
        return supp1PartNo;
    }

    public void setSupp1PartNo(String supp1PartNo) {
        this.supp1PartNo = supp1PartNo;
    }

    public String getSupp1PartDescription() {
        return supp1PartDescription;
    }

    public void setSupp1PartDescription(String supp1PartDescription) {
        this.supp1PartDescription = supp1PartDescription;
    }

    public Integer getPackQty() {
        return packQty;
    }

    public void setPackQty(Integer packQty) {
        this.packQty = packQty;
    }

    public String getSupp2Code() {
        return supp2Code;
    }

    public void setSupp2Code(String supp2Code) {
        this.supp2Code = supp2Code;
    }

    public String getSupp3Code() {
        return supp3Code;
    }

    public void setSupp3Code(String supp3Code) {
        this.supp3Code = supp3Code;
    }

    public String getSupp4Code() {
        return supp4Code;
    }

    public void setSupp4Code(String supp4Code) {
        this.supp4Code = supp4Code;
    }

    public String getSupp2PartNo() {
        return supp2PartNo;
    }

    public void setSupp2PartNo(String supp2PartNo) {
        this.supp2PartNo = supp2PartNo;
    }

    public String getSupp3PartNo() {
        return supp3PartNo;
    }

    public void setSupp3PartNo(String supp3PartNo) {
        this.supp3PartNo = supp3PartNo;
    }

    public String getSupp4PartNo() {
        return supp4PartNo;
    }

    public void setSupp4PartNo(String supp4PartNo) {
        this.supp4PartNo = supp4PartNo;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getCatalogue1Code() {
        return catalogue1Code;
    }

    public void setCatalogue1Code(String catalogue1Code) {
        this.catalogue1Code = catalogue1Code;
    }

    public String getCatalogue1Name() {
        return catalogue1Name;
    }

    public void setCatalogue1Name(String catalogue1Name) {
        this.catalogue1Name = catalogue1Name;
    }

    public LocalDate getCatalogue1StartDate() {
        return catalogue1StartDate;
    }

    public void setCatalogue1StartDate(LocalDate catalogue1StartDate) {
        this.catalogue1StartDate = catalogue1StartDate;
    }

    public LocalDate getCatalogue1EndDate() {
        return catalogue1EndDate;
    }

    public void setCatalogue1EndDate(LocalDate catalogue1EndDate) {
        this.catalogue1EndDate = catalogue1EndDate;
    }

    public BigDecimal getCatalogue1SellPrice() {
        return catalogue1SellPrice;
    }

    public void setCatalogue1SellPrice(BigDecimal catalogue1SellPrice) {
        this.catalogue1SellPrice = catalogue1SellPrice;
    }

    public BigDecimal getCatalogue1Cost() {
        return catalogue1Cost;
    }

    public void setCatalogue1Cost(BigDecimal catalogue1Cost) {
        this.catalogue1Cost = catalogue1Cost;
    }

    public String getCatalogue2Code() {
        return catalogue2Code;
    }

    public void setCatalogue2Code(String catalogue2Code) {
        this.catalogue2Code = catalogue2Code;
    }

    public String getCatalogue2Name() {
        return catalogue2Name;
    }

    public void setCatalogue2Name(String catalogue2Name) {
        this.catalogue2Name = catalogue2Name;
    }

    public LocalDate getCatalogue2StartDate() {
        return catalogue2StartDate;
    }

    public void setCatalogue2StartDate(LocalDate catalogue2StartDate) {
        this.catalogue2StartDate = catalogue2StartDate;
    }

    public LocalDate getCatalogue2EndDate() {
        return catalogue2EndDate;
    }

    public void setCatalogue2EndDate(LocalDate catalogue2EndDate) {
        this.catalogue2EndDate = catalogue2EndDate;
    }

    public BigDecimal getCatalogue2SellPrice() {
        return catalogue2SellPrice;
    }

    public void setCatalogue2SellPrice(BigDecimal catalogue2SellPrice) {
        this.catalogue2SellPrice = catalogue2SellPrice;
    }

    public BigDecimal getCatalogue2Cost() {
        return catalogue2Cost;
    }

    public void setCatalogue2Cost(BigDecimal catalogue2Cost) {
        this.catalogue2Cost = catalogue2Cost;
    }
}
