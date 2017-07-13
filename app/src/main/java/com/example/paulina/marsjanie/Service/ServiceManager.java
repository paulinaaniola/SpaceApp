package com.example.paulina.marsjanie.Service;

public class ServiceManager {

    private static ServiceRequest serviceRequest;

    public static ServiceRequest getInstance(){
        if (serviceRequest == null) {
            serviceRequest = new ServiceRequest();
        }
        return serviceRequest;
    }
}
