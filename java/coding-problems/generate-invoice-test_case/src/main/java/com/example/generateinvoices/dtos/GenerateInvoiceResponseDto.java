package com.example.generateinvoices.dtos;

import com.example.generateinvoices.models.Invoice;

public class GenerateInvoiceResponseDto {
    private ResponseStatus responseStatus;
    private Invoice invoice;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
