package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.models.GiftCard;
import com.example.ecom.models.LedgerEntry;
import com.example.ecom.models.TransactionType;
import com.example.ecom.repositories.GiftCardRepository;
import com.example.ecom.repositories.LedgerEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestGiftCardController {
    @Autowired
    private GiftCardController giftCardController;
    @Autowired
    private GiftCardRepository giftCardRepository;
    @Autowired
    private LedgerEntryRepository ledgerEntryRepository;

    @Test
    public void testCreateGiftCard(){
        CreateGiftCardRequestDto requestDto = new CreateGiftCardRequestDto();
        requestDto.setAmount(1000);
        CreateGiftCardResponseDto responseDto = giftCardController.createGiftCard(requestDto);
        assertNotNull(responseDto, "Response dto shouldn't be null");
        assertNotNull(responseDto.getGiftCard(), "Gift card shouldn't be null");
        GiftCard giftCard = responseDto.getGiftCard();
        assertNotNull(giftCard.getGiftCardCode(), "Gift card code shouldn't be null");
        assertNotNull(giftCard.getLedger(), "Ledger shouldn't be null");
        assertEquals(1, giftCard.getLedger().size(), "Ledger should have at one entry");
        LedgerEntry ledgerEntry = giftCard.getLedger().get(0);
        assertEquals(ledgerEntry.getAmount(), 1000, "Ledger entry amount should be 1000");
        assertEquals(ledgerEntry.getTransactionType(), TransactionType.CREDIT, "Ledger entry transaction type should be INITIAL_CREDIT");
        assertTrue(giftCardRepository.findById(giftCard.getId()).isPresent(), "Gift card should be present in the database");
    }

    @Test
    public void testRedeemGiftCard_GiftCardDoesntExist(){
        RedeemGiftCardRequestDto requestDto = new RedeemGiftCardRequestDto();
        requestDto.setGiftCardId(100);
        requestDto.setAmountToRedeem(100);
        RedeemGiftCardResponseDto responseDto = giftCardController.redeemGiftCard(requestDto);
        assertNotNull(responseDto, "Response dto shouldn't be null");
        assertEquals(responseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be FAILURE");
        assertNull(responseDto.getGiftCard(), "Gift card should be null");
    }

    @Test
    public void testRedeemGiftCard_Success(){
        CreateGiftCardRequestDto requestDto = new CreateGiftCardRequestDto();
        requestDto.setAmount(1000);
        CreateGiftCardResponseDto createGiftCardResponseDto = giftCardController.createGiftCard(requestDto);
        GiftCard giftCard = createGiftCardResponseDto.getGiftCard();

        RedeemGiftCardRequestDto redeemGiftCardRequestDto = new RedeemGiftCardRequestDto();
        redeemGiftCardRequestDto.setGiftCardId(giftCard.getId());
        redeemGiftCardRequestDto.setAmountToRedeem(100);
        RedeemGiftCardResponseDto redeemGiftCardResponseDto = giftCardController.redeemGiftCard(redeemGiftCardRequestDto);
        assertNotNull(redeemGiftCardResponseDto, "Response dto shouldn't be null");
        assertEquals(redeemGiftCardResponseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Response status should be SUCCESS");
        assertNotNull(redeemGiftCardResponseDto.getGiftCard(), "Gift card shouldn't be null");
        giftCard = redeemGiftCardResponseDto.getGiftCard();
        assertEquals(giftCard.getAmount(), 900, "Gift card amount should be 900");
        assertEquals(giftCard.getLedger().size(), 2, "Ledger should have two entries");
        LedgerEntry ledgerEntry = giftCard.getLedger().get(1);
        assertEquals(ledgerEntry.getAmount(), 100, "Ledger entry amount should be 100");
        assertEquals(ledgerEntry.getTransactionType(), TransactionType.DEBIT, "Ledger entry transaction type should be DEBIT");

        redeemGiftCardRequestDto = new RedeemGiftCardRequestDto();
        redeemGiftCardRequestDto.setGiftCardId(giftCard.getId());
        redeemGiftCardRequestDto.setAmountToRedeem(1000);
        redeemGiftCardResponseDto = giftCardController.redeemGiftCard(redeemGiftCardRequestDto);
        assertNotNull(redeemGiftCardResponseDto, "Response dto shouldn't be null");
        assertEquals(redeemGiftCardResponseDto.getResponseStatus(), ResponseStatus.SUCCESS, "Response status should be SUCCESS");
        giftCard = redeemGiftCardResponseDto.getGiftCard();
        assertEquals(giftCard.getAmount(), 0, "Gift card amount should be 0");
        assertEquals(giftCard.getLedger().size(), 3, "Ledger should have three entries");
        ledgerEntry = giftCard.getLedger().get(2);
        assertEquals(ledgerEntry.getAmount(), 900, "Ledger entry amount should be 900");
        assertEquals(ledgerEntry.getTransactionType(), TransactionType.DEBIT, "Ledger entry transaction type should be DEBIT");
    }

    @Test
    public void testRedeemGiftCard_GiftCardExpired() {
        CreateGiftCardRequestDto requestDto = new CreateGiftCardRequestDto();
        requestDto.setAmount(1000);
        CreateGiftCardResponseDto createGiftCardResponseDto = giftCardController.createGiftCard(requestDto);
        GiftCard giftCard = createGiftCardResponseDto.getGiftCard();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        giftCard.setExpiresAt(calendar.getTime());
        giftCard = giftCardRepository.save(giftCard); //Setting gift card to expire 1 day before


        RedeemGiftCardRequestDto redeemGiftCardRequestDto = new RedeemGiftCardRequestDto();
        redeemGiftCardRequestDto.setGiftCardId(giftCard.getId());
        redeemGiftCardRequestDto.setAmountToRedeem(100);
        RedeemGiftCardResponseDto redeemGiftCardResponseDto = giftCardController.redeemGiftCard(redeemGiftCardRequestDto);
        assertNotNull(redeemGiftCardResponseDto, "Response dto shouldn't be null");
        assertEquals(redeemGiftCardResponseDto.getResponseStatus(), ResponseStatus.FAILURE, "Response status should be FAILURE");
        assertNull(redeemGiftCardResponseDto.getGiftCard(), "Gift card should be null");
    }
}
