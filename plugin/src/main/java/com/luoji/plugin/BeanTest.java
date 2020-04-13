package com.luoji.plugin;

import com.luoji.pluginlibary.IBean;

public class BeanTest implements IBean {

    private String testName = "哈哈哈";
    @Override
    public void setName(String name) {
        this.testName = name;
    }

    @Override
    public String getName() {
        return testName;
    }
}
