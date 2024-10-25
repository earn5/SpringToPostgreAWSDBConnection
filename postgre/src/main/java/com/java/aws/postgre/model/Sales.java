package com.java.aws.postgre.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sales")
public class Sales {

    @Id
    private long salesid;

    private long listid;
    private long sellerid;
    private long buyerid;
    private long eventid;
    private long dateid;
    private long qtysold;
    private long pricepaid;
    private long commission;

}
