package com.zhk.domain.system;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class User implements Serializable {

    private String id;
    private String deptId;
    private String email;
    private String userName;
    private String station;
    private String password;
    private String state;
    private String companyId;
    private String companyName;
    private String deptName;
    private String managerId;
    private String gender;
    private String telephone;
    private String birthday;
    private Integer degree;
    private Double salary;
    private String joinDate;
    private Integer orderNo;
    private String createBy;
    private String createDept;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String remark;

}
