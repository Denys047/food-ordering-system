package com.food.ordering.system.order.service.domain.vo;

import com.food.ordering.system.domain.vo.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {

    protected TrackingId(UUID value) {
        super(value);
    }

}