package com.example.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/12 15:41
 */
@Data
@Entity
@Table(name = "job")
public class Job implements Serializable {

    @Id
    private Long id;
    private String name;
    private String type;
    private String typeValue;
    private Long closeTime;
    private Date createTime;
    private Date updateTime;
    private Integer groupId;






}
