package com.example.generateinvoices.models;

import java.util.List;

public class Customer extends BaseModel {
    private String name;
    private List<Invoice> invoices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
