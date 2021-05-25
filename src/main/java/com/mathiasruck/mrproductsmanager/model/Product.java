package com.mathiasruck.mrproductsmanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Where(clause = "DELETED IS NULL")
@SQLDelete(sql = "UPDATE PRODUCT SET DELETED = id WHERE id = ?")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
    private Double price;
    private Date creationDate;
    private Long deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Size(max = 100, message = "sku_bigger_than_allowed")
    @NotNull(message = "sku_cannot_be_null")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Size(max = 100, message = "name_bigger_than_allowed")
    @NotNull(message = "name_cannot_be_null")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "price_cannot_be_null")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    @Column(name = "DELETED", insertable = false, updatable = false)
    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(final Long deleted) {
        this.deleted = deleted;
    }

}
