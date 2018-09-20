package com.effect.tdb.ms.delivery;

import com.effect.tdb.bs.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliverysRepository extends BaseRepository<DeliveryEntity,Integer> {
    public DeliveryEntity getDeliveryEntityByDeliveryNoteIdEquals(Integer id);

    @Query("SELECT max(r.deliveryNo) FROM DeliveryEntity r")
    public String getMaxDeliveryNo();

    public List<DeliveryEntity> getByDeliveryNo(String deliveryNo);

    public List<DeliveryEntity> getByInvoiceNo(String invoiceNo);

    public List<DeliveryEntity> findByDeliveryNoteIdAndStatus(Integer deliveryNoteId, byte status);
}