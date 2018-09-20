package com.effect.tdb.ms.delivery;

import com.effect.tdb.bs.common.*;
import com.effect.tdb.ms.common.Constants;
import com.effect.tdb.ms.common.Utils;
import com.effect.tdb.ms.deliverynote.DeliveryNoteEntity;
import com.effect.tdb.ms.deliverynote.DeliveryNoteProductEntity;
import com.effect.tdb.ms.deliverynote.DeliveryNotesRepository;
import com.effect.tdb.ms.dictionary.CustomerEntity;
import com.effect.tdb.ms.receipt.ReceiptsRepository;
import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.effect.tdb.ms.common.Constants.*;

/**
 * Created by datha on 7/3/2018.
 */
@Component
public class DeliverysService {
    private static Logger logger = Logger.getLogger(DeliverysService.class);

    @Autowired
    protected DeliverysRepository deliverysRepository;

    @Autowired
    protected DeliveryNotesRepository deliveryNotesRepository;

    private ControllerUtils controllerUtils = new ControllerUtils<DeliveryEntity>();

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected ReceiptsRepository receiptsRepository;

    @Autowired
    private Environment env;

    @Autowired
    protected DeliveryProductsRepository deliveryProductsRepository;

    public String create(DeliveryEntity delivery) {
        try {
            DeliveryEntity a = deliverysRepository.save(delivery);
            if (a != null) {
                return String.valueOf(a.getId());
            }
        } catch (Exception e) {
            BaseUtils.except(logger, e);
            throw e;
        }
        return null;

    }

    public DeliveryEntity getOne(Integer id) {
        return deliverysRepository.findOne(id);
    }

    public List<DeliveryEntity> getAll() {
        List<DeliveryEntity> a = (List<DeliveryEntity>) deliverysRepository.findAll();
        return a;
    }

    /*public DataTablePaginationResponse dataTablePagination(MultiValueMap<String, String> params) {
        return this.controllerUtils.getPaginationDataWithStatusFilter(deliverysRepository, params);
    }*/


    @Transactional
    public DeliveryEntity delivery(DeliveryEntity deliveryEntity) {
        List<DeliveryEntity> deliveryEntityCheckExistList = deliverysRepository.getByDeliveryNo(deliveryEntity.getDeliveryNo());
        if (deliveryEntityCheckExistList.size() > 0) {
            deliveryEntity = new DeliveryEntity();
            deliveryEntity.setId(Constants.ID_EXIST_FIELD_NO);

            // If exist DeliveryNo
            return deliveryEntity;
        }


        deliveryEntity.setStatus(Byte.parseByte(BaseStatus.DB_ROW_ACTIVE.getValue()));
        deliveryEntity.setCustomerId(Integer.parseInt(deliveryEntity.getDeliveryNoteByDeliveryNoteId().getCustomerId()));
        //deliveryEntity.setDeliveryTypeId(Integer.parseInt(deliveryEntity.getDeliveryNoteByDeliveryNoteId().getDe .getDe()));
        if (deliveryEntity.getId() > 0) {
            Date currentDateTime = new Date();
            deliveryEntity.setUpdatedAt(new Timestamp(currentDateTime.getTime()));
            deliverysRepository.save(deliveryEntity);
        } else {
            Date currentDateTime = new Date();
            deliveryEntity.setCreatedAt(new Timestamp(currentDateTime.getTime()));
            deliverysRepository.save(deliveryEntity);
        }

        System.out.println("deliveryEntity Id: " + deliveryEntity.getId());
        for (DeliveryProductEntity deliveryProductEntity : deliveryEntity.getDeliveryProductsById()) {
            deliveryProductEntity.setDeliveryByDeliveryId(deliveryEntity);
            deliveryProductsRepository.save(deliveryProductEntity);
        }

        List<DeliveryProductEntity> listDeliveryProductEntity = deliveryEntity.getDeliveryProductsById();
        List<DeliveryNoteProductEntity> listDeliveryNoteProductEntity = deliveryEntity.getDeliveryNoteByDeliveryNoteId().getDeliveryNoteProductsById();
        Map<Integer, Integer> mapProductId = listDeliveryNoteProductEntity.stream().collect(Collectors.toMap(DeliveryNoteProductEntity::getId, DeliveryNoteProductEntity::getProductId));

        int iCount = 0;
        DeliveryNoteEntity dne = deliveryNotesRepository.findOne(deliveryEntity.getDeliveryNoteId());
        List<DeliveryEntity> listde = dne.getDeliverysById().stream().distinct().collect(Collectors.toList());
        for (DeliveryEntity de : listde) {
            for (DeliveryProductEntity temp : de.getDeliveryProductsById()) {
                if (temp.getRemainConvertQty() == 0) {
                    iCount++;
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : mapProductId.entrySet()) {
            List<DeliveryProductEntity> listDeliveryProductEntityTemp = listDeliveryProductEntity.stream().filter(a -> a.getDeliveryNoteProductId().equals(entry.getKey()) && a.getProductId().equals(entry.getValue())).collect(Collectors.toList());
            if (listDeliveryProductEntityTemp.stream().anyMatch(a -> a.getRemainConvertQty() == 0)) {
                iCount++;
            }
        }


        if (iCount >= listDeliveryNoteProductEntity.size()) {
            dne.setDeliveryNoteStatusId(Byte.parseByte(STATUS_DELIVERY_DELIVERED + ""));
        } else {
            dne.setDeliveryNoteStatusId(Byte.parseByte(STATUS_DELIVERY_DELIVERING + ""));
        }

        Date currentDateTime = new Date();
        dne.setUpdatedAt(new Timestamp(currentDateTime.getTime()));
        deliveryNotesRepository.save(dne);

        return deliveryEntity;
    }

    @Transactional
    public DeliveryEntity deliveryForSale(DeliveryEntity deliveryEntity) {
        deliveryEntity.setStatus(Byte.parseByte(BaseStatus.DB_ROW_ACTIVE.getValue()));
        deliveryEntity.setDeliveryTypeId(String.valueOf(TYPE_DELIVERY_FOR_SALE));
        deliveryEntity.setDeliveryNo(getDeliveryNo());
        Date currentDateTime = new Date();
        //String date = new SimpleDateFormat(DATE_TIME_FORMART_DB).format(currentDateTime);
        deliveryEntity.setDeliveryDate(new Timestamp(currentDateTime.getTime()));
        deliveryEntity.setCreatedAt(new Timestamp(currentDateTime.getTime()));
        deliveryEntity.setCreatedBy(deliveryEntity.getDeliveryPersonId());
        deliveryEntity.setDeliveryNoteId(Constants.STORE_ID_NOT_SELECTED);
        deliverysRepository.save(deliveryEntity);
        List<Long> productIds = new ArrayList<>();
        deliveryEntity.getDeliveryProductsById().forEach(a -> productIds.add(Long.parseLong(a.getProductId() + "")));
        ListProductEntity listProductEntity = getProductList(productIds);
        for (DeliveryProductEntity deliveryProductEntity : deliveryEntity.getDeliveryProductsById()) {
            deliveryProductEntity.setRequestQuanty(deliveryProductEntity.getRealQty());
            deliveryProductEntity.setProductUnitId(deliveryProductEntity.getProductUnitDeliveryId());
            Collection<ProductPackageEntity> listProductPackageEntity = listProductEntity.getListProductEntity().stream().filter(a -> a.getId() == deliveryProductEntity.getProductId()).findFirst().get().getProductPackagesById();
            int realConvertQty = convertQty(deliveryProductEntity.getProductUnitDeliveryId(), deliveryProductEntity.getRealQty(), listProductPackageEntity);
            deliveryProductEntity.setRealConvertQty(realConvertQty);
            deliveryProductEntity.setRequestConvertQty(realConvertQty);
            deliveryProductEntity.setRemainConvertQty(0);
            deliveryProductEntity.setDeliveryByDeliveryId(deliveryEntity);
            deliveryProductsRepository.save(deliveryProductEntity);
        }
        System.out.println("create delivery complete !");
        return deliveryEntity;
    }

    @Transactional
    private ResponseResult dld(DeliveryEntity deliveryEntity) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(true);
        try {
            System.out.println("invoice no: " + deliveryEntity.getInvoiceNo());
            List<DeliveryEntity> deliveryEntities = deliverysRepository.getByInvoiceNo(deliveryEntity.getInvoiceNo());

            if (deliveryEntities == null || deliveryEntities.isEmpty()) {
                responseResult.setSuccess(false);
                System.out.println("Khong ton tai xuat kho voi invoiceNo nay!");
                return responseResult;
            }

            for (DeliveryEntity de : deliveryEntities) {
                de.setStatus(Byte.parseByte(BaseStatus.DB_ROW_INACTIVE.getValue()));
                entityManager.detach(de.getDeliveryNoteByDeliveryNoteId());
                deliverysRepository.save(de);

            }
            System.out.println("Huy xuat kho ok!");
        } catch (Exception e) {
            responseResult.setSuccess(false);
            responseResult.setExceptionMessage(e.getMessage());
            e.printStackTrace();
        }
        return responseResult;
    }


    @Transactional
    public ResponseResult deleteLogicalDelivery(@RequestBody DeliveryEntity deliveryEntity) {
        return dld(deliveryEntity);
    }


    private static ListProductEntity getProductList(List<Long> productIds) {
        final String uri = PRODUCT_SERVICE_BASE + GET_PRODUCT_LIST_BY_IDS_FOR_SALE_URL;
        ProductListRequest productListRequest = new ProductListRequest();
        productListRequest.setIds(productIds);

        RestTemplate restTemplate = new RestTemplate();
        ListProductEntity listProductEntity = restTemplate.postForObject(uri, productListRequest, ListProductEntity.class);
        System.out.println("get list product complete !");
        return listProductEntity;
    }

    private static int convertQty(int idUnitChoose, int qtyForConvert, Collection<ProductPackageEntity> listProductPackageEntity) {
        int convertQty;
        convertQty = qtyForConvert * listProductPackageEntity.stream().filter(packageUnit -> packageUnit.getUnitByIdUnit().getId() == idUnitChoose).findFirst().get().getStandardQuantity();
        return convertQty;
    }

    private String getDeliveryNo() {
        try {
            String deliveryNo = Utils.generateCodeNo(deliverysRepository.getMaxDeliveryNo(), Constants.PREFIX_CODE_DELIVERY);
            return deliveryNo;
        } catch (Exception e) {
            BaseUtils.except(logger, e);
            return null;
        }
    }

    @Transactional
    public String update(DeliveryEntity deliveryEntity) {

        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(true);

        Date currentDateTime = new Date();

        deliveryEntity.setStatus(Byte.parseByte(BaseStatus.DB_ROW_ACTIVE.getValue()));
        deliveryEntity.setUpdatedAt(new Timestamp(currentDateTime.getTime()));
        deliverysRepository.save(deliveryEntity);

        DeliveryNoteEntity dne = deliveryNotesRepository.findOne(deliveryEntity.getDeliveryNoteId());
        dne.setDeliveryNoteStatusId(Byte.parseByte(STATUS_DELIVERY_DELIVERED + ""));
        dne.setUpdatedAt(new Timestamp(currentDateTime.getTime()));
        deliveryNotesRepository.save(dne);
        return "update ok";
    }

    @Transactional
    public String delete(Integer id) {
        try {
            deliverysRepository.delete(id);

        } catch (Exception e) {
            BaseUtils.except(logger, e);
            return null;
        }

        return "delete ok";
    }

    public DeliveryEntity getOneByDeliveryNoteId(Integer deliveryNoteId) {
        try {
            System.out.println("get by deliveryNoteId: " + deliveryNoteId);
            //DeliveryEntity deliveryEntity = deliverysRepository.getDeliveryEntityByDeliveryNoteIdEquals(deliveryNoteId);

            DeliveryNoteEntity deliveryNote = deliveryNotesRepository.findOne(deliveryNoteId);
            List<DeliveryEntity> listde = deliveryNote.getDeliverysById();
            List<DeliveryProductEntity> listdpe = new ArrayList<DeliveryProductEntity>();
            for (DeliveryEntity de : listde) {
                listdpe.addAll(de.getDeliveryProductsById());
            }
            List<DeliveryNoteProductEntity> listdnpe = deliveryNote.getDeliveryNoteProductsById();
            if (deliveryNote.getDeliveryNoteStatusId().equals(Byte.parseByte(STATUS_DELIVERY_DELIVERING + ""))) {
                DeliveryEntity deliveryEntity = new DeliveryEntity();
                deliveryEntity.setDeliveryNoteByDeliveryNoteId(deliveryNote);
                deliveryEntity.setDeliveryNoteId(deliveryNote.getId());
                Date currentDateTime = new Date();
                //String date = new SimpleDateFormat(DATE_TIME_FORMART_DB).format(currentDateTime);
                deliveryEntity.setDeliveryDate(new Timestamp(currentDateTime.getTime()));
                //deliveryEntity.setDeliveryPersonId("Admin");

                List<DeliveryProductEntity> listdpeTemp = new ArrayList<DeliveryProductEntity>();
                for (DeliveryNoteProductEntity dnpe : listdnpe) {
                    DeliveryProductEntity entity = new DeliveryProductEntity();
                    entity.setId(dnpe.getId());
                    entity.setProductId(dnpe.getProductId());
                    entity.setProductUnitId(dnpe.getProductUnitId());
                    entity.setRequestQuanty(dnpe.getRequestQuanty());
                    entity.setExpireDate(dnpe.getExpireDate());
                    entity.setLotNumber(dnpe.getLotNumber());
                    if (listdpe.stream().filter(a -> a.getDeliveryNoteProductId().equals(dnpe.getId())).findFirst().orElse(null) != null) {
                        entity.setRemainConvertQty(listdpe.stream().filter(a -> a.getDeliveryNoteProductId().equals(dnpe.getId())).min(Comparator.comparing(DeliveryProductEntity::getRemainConvertQty)).get().getRemainConvertQty());
                    } else {
                        entity.setRemainConvertQty(-1);
                    }
                    listdpeTemp.add(entity);
                }
                deliveryEntity.setDeliveryProductsById(listdpeTemp);
                return deliveryEntity;
            } else {
                DeliveryEntity deliveryEntity = new DeliveryEntity();
                deliveryEntity.setDeliveryNoteByDeliveryNoteId(deliveryNote);
                deliveryEntity.setDeliveryNoteId(deliveryNote.getId());
                Date currentDateTime = new Date();
                //String date = new SimpleDateFormat(DATE_TIME_FORMART_DB).format(currentDateTime);
                deliveryEntity.setDeliveryDate(new Timestamp(currentDateTime.getTime()));
                //deliveryEntity.setDeliveryPersonId("Admin");
                return deliveryEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public DeliveryEntity getMaxDeliveyNo() {
        try {
            DeliveryEntity deliveryEntity = new DeliveryEntity();
            String codeNo = Utils.generateCodeNo(deliverysRepository.getMaxDeliveryNo(), Constants.PREFIX_CODE_DELIVERY);
            deliveryEntity.setDeliveryNo(codeNo);
            return deliveryEntity;
        } catch (Exception e) {
            System.out.print("Đã xảy ra lỗi khi get max delivery no !");
            e.printStackTrace();
            throw e;
        }
    }

    public List<DeliveryProductEntity> getDeliveryProductFromDeliveryId(Integer id) {
        try {
            DeliveryEntity entity = deliverysRepository.findOne(id);
            List<DeliveryProductEntity> listDeliveryEntity = entity.getDeliveryProductsById();
            return listDeliveryEntity;
        } catch (Exception e) {
            BaseUtils.except(logger, e);
            return null;
        }
    }

    public List<DeliveryProductPrint> getDeliveryProductEntitiesByDeliveryIdPrint(int id) {

        try {

            String maxRow = env.getProperty("product.load.maxrow", "100");
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("proc_get_delivery_product_print", DeliveryProductPrint.class);

            query.registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN).setParameter("id", id);
            query.registerStoredProcedureParameter("maxrow", Integer.class, ParameterMode.IN).setParameter("maxrow", Integer.parseInt(maxRow));

            query.execute();

            List<DeliveryProductPrint> deliveryProductPrintList = (List<DeliveryProductPrint>) query.getResultList();

            return deliveryProductPrintList;


        } catch (Exception ex) {
            BaseUtils.except(logger, ex);
            return null;
        } finally {
            Utils.closeEN(entityManager);
        }
    }

    public DeliveryEntity getDetail(int id) {
        try {
            return deliverysRepository.findOne(id);
        } catch (Exception e) {
            BaseUtils.except(logger, e);
            return null;
        }
    }

    public DataTablePaginationResponse dataTablePagination(MultiValueMap<String, String> params) {
        return this.controllerUtils.getPaginationDataWithStatusFilter(deliverysRepository, params);
    }

    public CustomerEntity getCustomerById(@PathVariable("id") int id) {
        CustomerEntity ce = receiptsRepository.getCustomerById(id);

        return ce;
    }

    /**
     * Lấy số lượng còn lại của sản phẩm theo kho
     *
     * @param storeId   id kho
     * @param productId id sản phẩm
     * @return số lượng còn lại
     */

    public Integer getRemainQuantityByStoreIdAndProductId(int storeId, int productId) {

        try {
            Integer remainQuantityConvert = (Integer) entityManager
                    .createNativeQuery(
                            "SELECT func_get_remain_quantity_by_store_id_and_product_id(:storeId,:productId) FROM DUAL"
                    )
                    .setParameter("storeId", storeId)
                    .setParameter("productId", productId)
                    .getSingleResult();

            return remainQuantityConvert;
        } catch (Exception ex) {
            BaseUtils.except(logger, ex);
            return null;
        } finally {
            Utils.closeEN(entityManager);
        }
    }

    /**
     * Lấy số lượng còn lại của sản phẩm theo kho
     *
     * @param storeId       kho
     * @param listProductId list id sản phẩm
     * @return trả về 1 list sản phẩm + số lượng còn tồn theo kho
     */
    public List<ProductQuantityEntity> getRemainQuantityByStoreIdAndListProductId(int storeId,List<Integer> listProductId) {

        try {
            List<ProductQuantityEntity> listResult = new ArrayList<ProductQuantityEntity>();
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("proc_get_remain_quantity_by_store_id_and_product_id", ProductQuantityEntity.class);
            String strListKey = Joiner.on("','").join(listProductId);//String.join("','", listProductId);
            String sWhere = " Where product_id in ( '" + strListKey + "' )  ";
            query.registerStoredProcedureParameter("sWhere", String.class, ParameterMode.IN).setParameter("sWhere", sWhere);
            query.registerStoredProcedureParameter("storeId", Integer.class, ParameterMode.IN).setParameter("storeId", storeId);
            query.execute();
            List<ProductQuantityEntity> productQuantitys = (List<ProductQuantityEntity>) query.getResultList();

            for (int productId : listProductId) {
                ProductQuantityEntity productQuantity = productQuantitys.stream().filter(a -> a.getProductId() == productId).findFirst().orElse(null);
                if (productQuantity != null) {
                    listResult.add(productQuantity);
                } else {
                    ProductQuantityEntity productQuantityEntity = new ProductQuantityEntity();
                    productQuantityEntity.setProductId(productId);
                    productQuantityEntity.setRemainQuantityConvert(0);
                    listResult.add(productQuantityEntity);
                }
            }
            return listResult;
        } catch (Exception ex) {
            BaseUtils.except(logger, ex);
            return null;
        } finally {
            Utils.closeEN(entityManager);
        }
    }
}
