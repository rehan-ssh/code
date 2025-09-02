package com.example.ecom.services;

import com.example.ecom.exceptions.GiftCardDoesntExistException;
import com.example.ecom.exceptions.GiftCardExpiredException;
import com.example.ecom.models.GiftCard;
import com.example.ecom.models.LedgerEntry;
import com.example.ecom.models.TransactionType;
import com.example.ecom.repositories.GiftCardRepository;
import com.example.ecom.repositories.LedgerEntryRepository;
import com.example.ecom.utils.GiftCardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCardServiceImpl implements GiftCardService{

    private GiftCardRepository giftCardRepository;

    private LedgerEntryRepository ledgerEntryRepository;
    @Autowired
    public GiftCardServiceImpl(GiftCardRepository giftCardRepository, LedgerEntryRepository ledgerEntryRepository) {
        this.giftCardRepository = giftCardRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Override
    public GiftCard createGiftCard(double amount) {
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setAmount(amount);
        ledgerEntry.setCreatedAt(new Date());
        ledgerEntry.setTransactionType(TransactionType.CREDIT);
        ledgerEntry = ledgerEntryRepository.save(ledgerEntry);

        String giftCardCode = GiftCardUtils.generateGiftCardCode();
        Date now = new Date();
        Date expiresAt = GiftCardUtils.getExpirationDate(now);
        GiftCard giftCard = new GiftCard();
        giftCard.setAmount(amount);
        giftCard.setCreatedAt(now);
        giftCard.setExpiresAt(expiresAt);
        giftCard.setGiftCardCode(giftCardCode);
        giftCard.setLedger(List.of(ledgerEntry));
        return giftCardRepository.save(giftCard);
    }

    @Override
    public GiftCard redeemGiftCard(int giftCardId, double amountToRedeem) throws GiftCardDoesntExistException, GiftCardExpiredException {
        Optional<GiftCard> optionalGiftCard = giftCardRepository.findById(giftCardId);
        if(optionalGiftCard.isEmpty()){
            throw new GiftCardDoesntExistException("Gift card doesn't exist");
        }
        GiftCard giftCard = optionalGiftCard.get();
        Date now = new Date();
        if(now.after(giftCard.getExpiresAt())){
            throw new GiftCardExpiredException("Gift card expired");
        }
        amountToRedeem = Math.min(amountToRedeem, giftCard.getAmount());
        double remainingAmount = giftCard.getAmount() - amountToRedeem;
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setAmount(amountToRedeem);
        ledgerEntry.setCreatedAt(new Date());
        ledgerEntry.setTransactionType(TransactionType.DEBIT);
        ledgerEntry = ledgerEntryRepository.save(ledgerEntry);

        giftCard.setAmount(remainingAmount);
        giftCard.getLedger().add(ledgerEntry);
        return giftCardRepository.save(giftCard);
    }
}
