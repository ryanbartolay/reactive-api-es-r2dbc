/**
 * Copyright (c) 2020 Peplink, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Peplink, Inc.
 * Use is subject to license terms.
 */
package com.peplink.ecommerce.workflow.reactive.api.config.security;

public enum Authorities {

    ACCESS_MAIN_API_SFA_MEMBERSHIP("AS"), 
    ACCESS_MAIN_API_CELLULAR_MODULE("AC");

    private String m_code;

    private Authorities(String code) {
        m_code = code;
    }

    public String getCode() {
        return m_code;
    }

    public void setCode(String code) {
        m_code = code;
    }

}
