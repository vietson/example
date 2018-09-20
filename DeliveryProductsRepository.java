package com.effect.tdb.ms.delivery;

import com.effect.tdb.bs.common.BaseRepository;

import java.util.List;

public interface DeliveryProductsRepository extends BaseRepository<DeliveryProductEntity,Integer> {

    public List<DeliveryProductEntity> getDeliveryProductEntitiesByDeliveryNoteProductId(Integer deliveryNoteProductId);
}