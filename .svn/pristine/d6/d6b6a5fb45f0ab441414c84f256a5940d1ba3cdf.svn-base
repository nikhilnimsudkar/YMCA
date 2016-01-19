package com.ymca.core.service;

import org.hibernate.dialect.MySQL5Dialect;

public class CustomMySql5Dialect extends MySQL5Dialect {

    public CustomMySql5Dialect() {
        super();
        registerFunction("group_concat", new GroupConcatFunction());
    }
}
