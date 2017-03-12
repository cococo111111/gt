package com.fu.database.dao.impl;

import com.fu.common.constant.KeyConstant;
import com.fu.database.dao.CategoryDao;
import com.fu.database.dao.ProductDao;
import com.fu.database.entity.Area;
import com.fu.database.entity.Category;
import com.fu.database.entity.Product;
import com.fu.database.model.ProductApi;
import com.fu.database.model.RelatingModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
@Repository
public class ProductDaoImpl extends GenericDaoImpl<Product, Long> implements ProductDao {

    private static final Logger LOG = Logger.getLogger(ProductDaoImpl.class);

    private final CategoryDao categoryDao;

    public ProductDaoImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Product getProductById(long productId) {
        LOG.info("[getProductById] Start: productId = " + productId);
        LOG.info("[getProductById] End");
        return getEntityManager().createQuery("SELECT c FROM Product c WHERE c.id=:id ", Product.class)
                .setParameter("id", productId)
                .getSingleResult();
    }

    @Override
    public List<Product> getProductBySearchName(String name, int positionInResult, int maxShowResult) {
        LOG.info("[getProductBySearchName] Start: name = " + name);
        List<Product> product = getEntityManager().createNativeQuery("SELECT * FROM product WHERE LOWER(name) REGEXP :name1 AND LOWER(name) NOT REGEXP :name2 AND LOWER(name) NOT REGEXP :name3 AND status=TRUE ORDER BY areaWeight desc, areaName desc", Product.class)
                .setParameter("name1", name.toLowerCase())
                .setParameter("name2", " " + name.toLowerCase())
                .setParameter("name3", name.toLowerCase() + " ")
                .setFirstResult(positionInResult)
                .setMaxResults(maxShowResult)
                .getResultList();
        if (!product.isEmpty()) {
            LOG.info("[getProductBySearchName] End");
            return product;
        }
        List<Category> category = categoryDao.getCategoryByName(name);
        if (!category.isEmpty()) {
            product = getEntityManager().createQuery("SELECT p FROM Product p WHERE p.status=true AND p.id  IN (SELECT cp.categoryProductKey.productId FROM CategoryProduct cp WHERE cp.categoryProductKey.categoryId = :categoryId) ORDER BY p.areaWeight desc, p.areaName desc")
                    .setParameter("categoryId", category.get(0).getId())
                    .setFirstResult(positionInResult)
                    .setMaxResults(maxShowResult)
                    .getResultList();
            if (!product.isEmpty()) {
                LOG.info("[getProductBySearchName] End");
                return product;
            }
        }

        LOG.info("[getProductBySearchName] End");
        return getEntityManager().createNativeQuery("SELECT * FROM product WHERE (LOWER(name) REGEXP :name1 OR LOWER(name) REGEXP :name2) AND status=TRUE ORDER BY areaWeight desc, areaName desc", Product.class)
                .setParameter("name1", " " + name.toLowerCase())
                .setParameter("name2", name.toLowerCase() + " ")
                .setFirstResult(positionInResult)
                .setMaxResults(maxShowResult)
                .getResultList();
    }

    @Override
    public List<Product> getProductInCart(List<Long> listProductId) {
        LOG.info("[getProductInCart] Start: listProductId size = " + listProductId.size());

        String strId = "p.id=:";
        String strOr = " OR ";
        StringBuilder hql = new StringBuilder("FROM Product p WHERE ");

        hql.append(strId).append("id0");

        for (int i = 1; i < listProductId.size(); i++) {
            hql.append(strOr).append(strId).append("id").append(i);
        }
        hql.append(" ORDER BY p.floorName ASC, p.areaWeight DESC");
        Query query = getEntityManager().createQuery(String.valueOf(hql));

        for (int i = 0; i < listProductId.size(); i++) {
            query.setParameter("id" + i, listProductId.get(i));
        }

        List<Product> listProduct=query.getResultList();
        List<Product> listTangTret=new ArrayList<>();
        Iterator<Product> iterator= listProduct.iterator();
        Product tmpProduct;
        while(iterator.hasNext()){
            tmpProduct=iterator.next();
            if("tầng trệt".equals(tmpProduct.getAreaLocation())){
                listTangTret.add(tmpProduct);
                iterator.remove();
            }
        }
        for(int i=0;i<listProduct.size();i++){
            listTangTret.add(listProduct.get(i));
        }
        return listTangTret;
    }

    @Override
    public List<Product> getPromotionBaseOnWeightHistory(String userId, int positionInResult, int maxShowResult) {
        LOG.info("[getPromotionBaseOnWeightHistory] Start");
        LOG.info("[getPromotionBaseOnWeightHistory] End");
        List<Product> list = getEntityManager().createQuery("SELECT p FROM WeightedHistory w inner join Product p ON p.id=w.productId WHERE w.botFbId=:userId AND p.status=true AND p.id IN (SELECT pp.promotionProductKey.productId FROM PromotionProduct pp) ORDER BY w.weight desc ")
                .setParameter("userId", userId)
                .getResultList();
        List<Product> listNewestPromotion;
        listNewestPromotion = getProductInNewestPromotion();
        for (int i = 0; i < listNewestPromotion.size(); i++) {
            if (!list.contains(listNewestPromotion.get(i))) {
                list.add(listNewestPromotion.get(i));
            }
        }
        List<Product> listShow;
        if (positionInResult + maxShowResult + 1 < list.size()) {
            listShow = list.subList(positionInResult, positionInResult + maxShowResult + 1);
        } else {
            listShow = list.subList(positionInResult, list.size());
        }

        return listShow;
    }

    @Override
    public List<ProductApi> getProductForApi() {
        LOG.info("[getProductForApi] Start");

        List<Product> productList = getEntityManager().createQuery("SELECT p FROM Product p WHERE p.status=TRUE", Product.class)
                .getResultList();

        List<ProductApi> productApiList = new ArrayList<>();
        ProductApi productApi;
        for (Product product : productList) {
            productApi = new ProductApi();
            productApi.setId(Math.toIntExact(product.getId()));
            productApi.setName(product.getName());
            productApi.setAreaName(product.getAreaName());
            productApi.setAreaLocation(product.getAreaLocation());
            productApi.setAreaWeight(product.getAreaWeight());
            productApiList.add(productApi);
        }

        LOG.info("[getProductForApi] End");
        return productApiList;
    }

    @Override
    public List<Product> getSuggestProductById(long productId) {
        LOG.info("[getSuggestProductById] Start:");
        LOG.info("[getSuggestProductById] End");
        return getEntityManager().createQuery("SELECT p FROM RelatingProduct rp join Product p ON p.id= rp.relatingProductKey.productId2 WHERE p.status=true AND p.id IN (SELECT r.relatingProductKey.productId2 FROM RelatingProduct r WHERE r.relatingProductKey.productId1=:productId) ORDER BY rp.weight desc, p.areaWeight desc, p.areaName desc", Product.class)
                .setParameter("productId", productId).setMaxResults(KeyConstant.MAX_SHOW).getResultList();
    }

    @Override
    public List<RelatingModel> getSuggestProductAndWeight(long productId) {
        LOG.info("[getSuggestProductAndWeight] Start:");
        List<RelatingModel> listRelatingModel = new ArrayList<>();
        List<Object[]> list = getEntityManager().createQuery("SELECT p.id,p.name,p.imgUrl,rp.weight FROM RelatingProduct rp join Product p ON p.id= rp.relatingProductKey.productId2 WHERE  p.status=true AND p.id IN(SELECT r.relatingProductKey.productId2 FROM RelatingProduct r WHERE r.relatingProductKey.productId1=:productId) ORDER BY p.areaWeight desc, p.areaName desc")
                .setParameter("productId", productId).setMaxResults(KeyConstant.MAX_SHOW).getResultList();
        for (Object[] result : list) {
            listRelatingModel.add(new RelatingModel(Long.parseLong(result[0].toString()), result[1].toString(), result[2].toString(), Long.parseLong(result[3].toString())));
        }
        LOG.info("[getSuggestProductAndWeight] End");
        return listRelatingModel;
    }

    @Override
    public boolean checkSuggestProductById(long productId) {
        LOG.info("[checkSuggestProductById] Start:");
        List<Long> check = getEntityManager().createQuery("SELECT r.relatingProductKey.productId2 FROM RelatingProduct r WHERE r.relatingProductKey.productId1=:productId", Long.class)
                .setParameter("productId", productId).getResultList();
        LOG.info("[checkSuggestProductById] End");
        return !check.isEmpty();
    }

    @Override
    public List<Product> getAllProduct() {
        LOG.info("[getAllProduct] Start:");
        LOG.info("[getAllProduct] End");
        return getEntityManager().createQuery("SELECT c FROM Product c ORDER BY c.name", Product.class)
                .getResultList();
    }

    @Override
    public List<Product> checkDuplicateCode(String code) {
        LOG.info("[checkDuplicateCode] Start:");
        LOG.info("[checkDuplicateCode] End");
        return getEntityManager().createQuery("SELECT p FROM Product p WHERE p.code=:code", Product.class)
                .setParameter("code", code)
                .getResultList();
    }

    @Override
    public long getIdByCode(String code) {
        LOG.info("[getIdByCode] Start:");
        LOG.info("[getIdByCode] End");
        Product product = getEntityManager().createQuery("SELECT p FROM Product p WHERE p.code=:code", Product.class)
                .setParameter("code", code)
                .getSingleResult();
        return product.getId();
    }

    @Override
    public List<Product> getNewProduct() {
        LOG.info("[getNewProduct] Start:");
        LOG.info("[getNewProduct] End");
        return getEntityManager().createQuery("SELECT p FROM Product p WHERE p.imgUrl=:imgUrl", Product.class)
                .setParameter("imgUrl", "")
                .getResultList();
    }

    @Override
    public List<Product> getProductNotSuggestById(long productId) {
        return getEntityManager().createQuery("SELECT p FROM Product p WHERE p.status=true AND p.id NOT IN (SELECT r.relatingProductKey.productId2 FROM RelatingProduct r WHERE r.relatingProductKey.productId1=:productId)", Product.class)
                .setParameter("productId", productId).getResultList();
    }

    @Override
    public boolean deleteSuggest(long productId, long productSuggestId) {
        getEntityManager().createQuery("DELETE FROM RelatingProduct r WHERE r.relatingProductKey.productId1=:productId AND r.relatingProductKey.productId2=:productSuggestId")
                .setParameter("productId", productId)
                .setParameter("productSuggestId", productSuggestId)
                .executeUpdate();
        return true;
    }

    @Override
    public List<Product> getAll(int firstResult, int maxResult) {
        LOG.info(new StringBuilder("[getAll] Start: firstResult = ").append(firstResult)
                .append(", maxResult = ").append(maxResult));
        LOG.info("[getAll] End");
        return getEntityManager()
                .createQuery("SELECT c FROM Product c ", Product.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

    @Override
    public void updateAreaInProduct(Area area, String floorName) {
        LOG.info("[updateAreaInProduct] Start:");
        getEntityManager().createQuery("UPDATE Product p SET p.areaName=:areaName, p.areaWeight=:areaWeight, " +
                "p.floorName=:floorName WHERE p.areaId=:areaId")
                .setParameter("areaId", area.getId())
                .setParameter("areaName", area.getName())
                .setParameter("floorName", floorName)
                .setParameter("areaWeight", area.getWeight())
                .executeUpdate();
        LOG.info("[updateAreaInProduct] End:");
    }

    @Override
    public void deleteAreaInProduct(Area area) {
        LOG.info("[deleteAreaInProduct] Start:");
        getEntityManager().createQuery("UPDATE Product p SET p.areaId=0, p.areaName=:areaName, p.areaWeight=0, " +
                "p.floorName=:floorName WHERE p.areaId=:areaId")
                .setParameter("areaId", area.getId())
                .setParameter("areaName", "")
                .setParameter("floorName", "")
                .executeUpdate();
        LOG.info("[deleteAreaInProduct] End:");
    }

    @Override
    public void updateFloorInProduct(Area area, String floorName) {
        LOG.info("[updateFloorInProduct] Start:");
        getEntityManager().createQuery("UPDATE Product p SET p.floorName=:floorNewName WHERE p.areaId=:areaId")
                .setParameter("areaId", area.getId())
                .setParameter("floorNewName", floorName)
                .executeUpdate();
        LOG.info("[updateFloorInProduct] End");
    }


    @Override
    public List<Product> getProductInNewestPromotion() {
        LOG.info("[getProductInNewPromotion] Start: ");
        LOG.info("[getProductInNewPromotion] End");
        return getEntityManager().createQuery("SELECT p FROM  PromotionProduct pp join Product p  ON  p.id=pp.promotionProductKey.productId join  Promotion pr ON pp.promotionProductKey.promotionId=pr.id WHERE   p.status=true AND pr.endDate>:currentTime   ORDER By pr.endDate DESC")
                .setParameter("currentTime", System.currentTimeMillis())
                .getResultList();

    }

    @Override
    public List<Product> checkDuplicateName(String name) {
        LOG.info("[checkDuplicateCode] Start:");
        LOG.info("[checkDuplicateCode] End");
        return getEntityManager().createQuery("SELECT p FROM Product p WHERE p.name=:name", Product.class)
                .setParameter("name", name)
                .getResultList();
    }
}
