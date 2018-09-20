package com.effect.tdb.ms.delivery;

import com.effect.tdb.bs.common.*;
import com.effect.tdb.ms.common.*;
import com.effect.tdb.ms.dictionary.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = Constants.DELIVERY_BASE_URL)
public class DeliverysController {

    @Autowired
    private DeliverysService deliverysService;

    private static final String GET_ONE_BY_DELIVERY_NOTE_ID_URL = "/getoneByDeliveryNoteId/{deliveryNoteId}";

    @RequestMapping(value = Constants.CREATE_URL, method = RequestMethod.POST)
    public @ResponseBody
    String create(@RequestBody DeliveryEntity delivery, ModelMap model) {
        return deliverysService.create(delivery);
    }

    @RequestMapping(value = Constants.DELIVERY_URL, method = RequestMethod.PUT)
    public @ResponseBody
    DeliveryEntity delivery(@RequestBody DeliveryEntity deliveryEntity, ModelMap model) {
        return deliverysService.delivery(deliveryEntity);
    }

    @RequestMapping(value = Constants.DELIVERY_FOR_SALE_URL, method = RequestMethod.PUT)
    public @ResponseBody
    DeliveryEntity deliveryForSale(@RequestBody DeliveryEntity deliveryEntity, ModelMap model) {
        return deliverysService.deliveryForSale(deliveryEntity);
    }

    @RequestMapping(value = Constants.DELETE_LOGICAL_URL, method = RequestMethod.PUT)
    public @ResponseBody
    ResponseResult deleteLogicalDelivery(@RequestBody DeliveryEntity deliveryEntity) {
        return deliverysService.deleteLogicalDelivery(deliveryEntity);
    }

    @RequestMapping(value = Constants.UPDATE_URL, method = RequestMethod.PUT)
    public @ResponseBody
    String update(@RequestBody DeliveryEntity deliveryEntity, ModelMap model) {
        return deliverysService.update(deliveryEntity);
    }

    @RequestMapping(value = Constants.DELETE_URL, method = RequestMethod.DELETE)
    public @ResponseBody
    String delete(@PathVariable("id") Integer id, ModelMap model) {
        return deliverysService.delete(id);
    }

    @RequestMapping(value = Constants.GET_ALL_URL, method = RequestMethod.GET)
    public @ResponseBody
    List<DeliveryEntity> getAll(ModelMap model) {
        return deliverysService.getAll();
    }

    @RequestMapping(value = Constants.GET_ONE_URL, method = RequestMethod.GET)
    public @ResponseBody
    DeliveryEntity getOne(@PathVariable("id") Integer id, ModelMap model) {
        return deliverysService.getOne(id);
    }

    @RequestMapping(value = GET_ONE_BY_DELIVERY_NOTE_ID_URL, method = RequestMethod.GET)
    public @ResponseBody
    DeliveryEntity getOneByDeliveryNoteId(@PathVariable("deliveryNoteId") Integer deliveryNoteId, ModelMap model) {
        return deliverysService.getOneByDeliveryNoteId(deliveryNoteId);
    }


    @RequestMapping(value = Constants.DELIVERY_BASE_URL_GETMAXDELIVERY, method = RequestMethod.GET)
    public @ResponseBody
    DeliveryEntity getMaxDeliveyNo(ModelMap model) {
        return deliverysService.getMaxDeliveyNo();
    }

    @RequestMapping(value = Constants.GET_DELIVERY_PRODUCT_BY_DELIVERY_ID_URL, method = RequestMethod.GET)
    public @ResponseBody
    List<DeliveryProductEntity> getDeliveryProductFromDeliveryId(@PathVariable("id") Integer id, ModelMap model) {
        return deliverysService.getDeliveryProductFromDeliveryId(id);
    }

    @RequestMapping(value = Constants.GET_DELIVERY_PRODUCT_BY_DELIVERY_ID_PRINT_URL, method = RequestMethod.GET)
    public @ResponseBody
    List<DeliveryProductPrint> getDeliveryProductEntitiesByDeliveryIdPrint(@PathVariable("id") int id, ModelMap model) {

        return deliverysService.getDeliveryProductEntitiesByDeliveryIdPrint(id);
    }

    @RequestMapping(value = Constants.GET_DETAIL_URL, method = RequestMethod.GET)
    public @ResponseBody
    DeliveryEntity getDetail(@PathVariable("id") int id, ModelMap model) {
        return deliverysService.getDetail(id);
    }

    @RequestMapping(value = Constants.GET_PAGE_URL, method = RequestMethod.GET)
    public @ResponseBody
    DataTablePaginationResponse dataTablePagination(@RequestParam MultiValueMap<String, String> params) {
        return deliverysService.dataTablePagination(params);
    }

    @RequestMapping(value = Constants.GET_CUSTOMER_BY_ID_BASE_URL, method = RequestMethod.GET)
    public @ResponseBody
    CustomerEntity getCustomerById(@PathVariable("id") int id) {
        return deliverysService.getCustomerById(id);
    }

    /**
     * Lấy số lượng còn lại của sản phẩm theo kho
     *
     * @param storeId   id kho
     * @param productId id sản phẩm
     * @param model
     * @return số lượng còn lại
     */
    @RequestMapping(value = Constants.GET_REMAIN_QUANTITY_BY_STOREID_AND_PRODUCTID, method = RequestMethod.GET)
    public @ResponseBody
    Integer getRemainQuantityByStoreIdAndProductId(@PathVariable("storeId") int storeId, @PathVariable("productId") int productId, ModelMap model) {
        return deliverysService.getRemainQuantityByStoreIdAndProductId(storeId, productId);
    }

    /**
     * Lấy số lượng còn lại của sản phẩm theo kho
     *
     * @param storeId       kho
     * @param listProductId list id sản phẩm
     * @param model
     * @return trả về 1 list sản phẩm + số lượng còn tồn theo kho
     */
    @RequestMapping(value = Constants.GET_REMAIN_QUANTITY_BY_STOREID_AND_LISTPRODUCTID, method = RequestMethod.GET)
    public @ResponseBody
    List<ProductQuantityEntity> getRemainQuantityByStoreIdAndListProductId(@PathVariable("storeId") int storeId, @PathVariable("listProductId") List<Integer> listProductId, ModelMap model) {

        return deliverysService.getRemainQuantityByStoreIdAndListProductId(storeId, listProductId);
    }
}