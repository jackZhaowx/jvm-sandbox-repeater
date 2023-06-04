package com.alibaba.jvm.sandbox.repeater.plugin.core.serialize;

/**
 * {@link HessianSerializeDomainSub}
 * <p>
 *
 * @author zhaowanxin
 */
public class HessianSerializeDomainSub extends HessianSerializeDomain {

    private String name = "son";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
