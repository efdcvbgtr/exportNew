package com.zhk.commons;

public enum MethodList {

    m_findById("findById"),
    m_save("save"),
    m_delete("delete"),
    m_update("update"),
    m_findAll("findAll");

    private String methods;

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    MethodList(String methods) {
        this.methods = methods;
    }
}
