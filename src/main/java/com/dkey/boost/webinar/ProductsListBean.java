package com.dkey.boost.webinar;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductsListBean implements Serializable{
    @EJB
    private ProductsManagerBean productsManagerBean;
    private String name;
    private long price;
    private long idForDelete;

    public List<Product> getProducts(){
//        return products;
        return productsManagerBean.readList(0,100);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getIdForDelete() {
        return idForDelete;
    }

    public void setIdForDelete(long idForDelete) {
        this.idForDelete = idForDelete;
    }

    public Product createProduct(){
        return productsManagerBean.createProduct(name,price);
    }
    public void deleteProduct(){
        productsManagerBean.delete(idForDelete);
    }
}
