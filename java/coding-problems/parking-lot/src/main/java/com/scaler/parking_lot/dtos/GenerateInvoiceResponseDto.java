package com.scaler.parking_lot.dtos;

import com.scaler.parking_lot.models.Invoice;

public class GenerateInvoiceResponseDto {
    private ResponseStatus status;
    private Invoice invoice;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
