package org.dzmitry.kapachou.hibernate.model;

import lombok.Getter;

@Getter
public enum Status {

    PENDING_START("pending start"), IN_PROGRESS("in progress"), COMPLETED("completed");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
