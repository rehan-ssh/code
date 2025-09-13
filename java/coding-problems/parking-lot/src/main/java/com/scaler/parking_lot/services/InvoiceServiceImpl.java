package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidGateException;
import com.scaler.parking_lot.exceptions.TicketNotFoundException;
import com.scaler.parking_lot.models.AdditionalService;
import com.scaler.parking_lot.models.Gate;
import com.scaler.parking_lot.models.Invoice;
import com.scaler.parking_lot.models.Ticket;
import com.scaler.parking_lot.respositories.GateRepository;
import com.scaler.parking_lot.respositories.InvoiceRepository;
import com.scaler.parking_lot.respositories.TicketRepository;
import com.scaler.parking_lot.strategies.pricing.PricingStrategy;
import com.scaler.parking_lot.strategies.pricing.PricingStrategyFactory;

import java.util.Date;
import java.util.Optional;

public class InvoiceServiceImpl implements InvoiceService{

    private TicketRepository ticketRepository;
    private PricingStrategyFactory pricingStrategyFactory;

    private GateRepository gateRepository;
    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(TicketRepository ticketRepository, PricingStrategyFactory pricingStrategyFactory, GateRepository gateRepository, InvoiceRepository invoiceRepository) {
        this.ticketRepository = ticketRepository;
        this.pricingStrategyFactory = pricingStrategyFactory;
        this.gateRepository = gateRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice createInvoice(long ticketId, long gateId) throws TicketNotFoundException, InvalidGateException {
        Optional<Ticket> ticketOptional = ticketRepository.getTicketById(ticketId);

        if(ticketOptional.isEmpty()){
            throw new TicketNotFoundException("Ticket not found");
        }
        Ticket ticket = ticketOptional.get();
        Optional<Gate> optionalGate = this.gateRepository.findById(gateId);
        if(optionalGate.isEmpty()){
            throw new InvalidGateException("Invalid gate id");
        }
        Date exitTime = new Date();
        PricingStrategy pricingStrategy = pricingStrategyFactory.getPricingStrategy(exitTime);
        double costIncurredForParking = pricingStrategy.calculateAmount(ticket.getEntryTime(), exitTime, ticket.getVehicle().getVehicleType());
        double costIncurredForAdditionalServices = 0;
        for(AdditionalService additionalService: ticket.getAdditionalServices()){
            costIncurredForAdditionalServices += additionalService.getCost();
        }
        double totalCost = costIncurredForParking + costIncurredForAdditionalServices;
        Gate gate = optionalGate.get();
        Invoice invoice = new Invoice();
        invoice.setTicket(ticket);
        invoice.setGate(gate);
        invoice.setExitTime(exitTime);
        invoice.setAmount(totalCost);
        invoice = this.invoiceRepository.save(invoice);
        return invoice;
    }
}
