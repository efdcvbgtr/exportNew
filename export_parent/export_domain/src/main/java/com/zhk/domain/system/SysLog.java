package com.zhk.domain.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
public class SysLog implements Serializable {

    private String id;
    private String userName;
    private String ip;
    private Date time;
    private String method;
    private String action;
    private String companyId;
    private String companyName;

}
