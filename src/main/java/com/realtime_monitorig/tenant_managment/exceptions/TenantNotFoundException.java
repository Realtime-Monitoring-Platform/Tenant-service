package com.realtime_monitorig.tenant_managment.exceptions;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(String message){
        super(message);
    }
}
