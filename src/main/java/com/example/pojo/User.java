package com.example.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/11 18:32
 */

@Data
@Entity
@Table(name = "test")
public class User implements Serializable {
    @Id
    private Integer id;


    private String name;
    private String password;
}
