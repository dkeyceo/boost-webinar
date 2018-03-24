package com.dkey.boost.webinar;

import org.apache.commons.lang3.StringUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@LocalBean
@Stateless
public class ProductsManagerBean {
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    /*public boolean create(Product product){
        if(!checkValid(product))
            return false;

        Product existsProduct = entityManager.find(Product.class, product.getId());
        if(existsProduct != null)
            return false;
        entityManager.persist(product);
        return true;
    }*/
    public Product createProduct(String name, long price){
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        entityManager.persist(product);
        return product;
    }
    public Product read(long id){
        return entityManager.find(Product.class, id);
    }
    public boolean update(Product product){
        if(!checkValid(product))
            return false;
        Product existsProduct = entityManager.find(Product.class, product.getId());
        if(existsProduct == null)
            return false;
        entityManager.merge(product);
        return true;
    }
    public boolean delete(long id){
        Product existsProduct = entityManager.find(Product.class, id);
        if(existsProduct == null)
            return false;
        entityManager.remove(existsProduct);
        return true;
    }
    public List<Product> readList(int offset, int limit){
        if(offset < 0 || limit < 1){
            return Collections.emptyList();
        }
        TypedQuery<Product> query =
                entityManager.createQuery(
                        "select p from Product p",Product.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();

    }
    private boolean checkValid(Product product){
        return product != null &&
                !StringUtils.isEmpty(product.getName()) &&
                product.getPrice() > 0;
    }
}
