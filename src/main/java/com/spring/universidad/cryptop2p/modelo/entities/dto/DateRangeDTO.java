package com.spring.universidad.cryptop2p.modelo.entities.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class DateRangeDTO {

    private Date startDate;
    private Date endDate;

    public DateRangeDTO() {
    }

    public DateRangeDTO(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
