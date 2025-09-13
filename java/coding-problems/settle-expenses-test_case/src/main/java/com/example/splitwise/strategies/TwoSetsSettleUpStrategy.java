package com.example.splitwise.strategies;

import com.example.splitwise.models.Transaction;
import com.example.splitwise.models.User;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TwoSetsSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUp(Map<User, Double> aggregateExpenses) {
        Queue<Pair<User, Double>> minHeap = new PriorityQueue<>(new Comparator<Pair<User, Double>>() {
            @Override
            public int compare(Pair<User, Double> u1, Pair<User, Double> u2) {
                return (int) (u1.getSecond() - u2.getSecond());
            }
        });

        Queue<Pair<User, Double>> maxHeap = new PriorityQueue<>(new Comparator<Pair<User, Double>>() {
            @Override
            public int compare(Pair<User, Double> u1, Pair<User, Double> u2) {
                return (int) (u2.getSecond() - u1.getSecond());
            }
        });

        for (Map.Entry<User, Double> entry : aggregateExpenses.entrySet()) {
            if(entry.getValue() > 0){
                maxHeap.add(Pair.of(entry.getKey(), entry.getValue()));
            } else {
                minHeap.add(Pair.of(entry.getKey(), entry.getValue()));
            }
        }
        List<Transaction> transactions = new ArrayList<>();
        while(!minHeap.isEmpty() && !maxHeap.isEmpty()){
            Pair<User, Double> userToPay = minHeap.poll();
            Pair<User, Double> userToGet = maxHeap.poll();

            double amountToBeTransferred = Math.min(userToGet.getSecond(), Math.abs(userToPay.getSecond()));

            Transaction transaction = new Transaction();
            transaction.setAmount(amountToBeTransferred);
            transaction.setPaidFrom(userToPay.getFirst());
            transaction.setPaidTo(userToGet.getFirst());
            transactions.add(transaction);

            if(userToPay.getSecond() + amountToBeTransferred < 0){
                minHeap.add(Pair.of(userToPay.getFirst(), userToPay.getSecond() + amountToBeTransferred));
            }

            if(userToGet.getSecond() - amountToBeTransferred > 0){
                maxHeap.add(Pair.of(userToGet.getFirst(), userToGet.getSecond() - amountToBeTransferred));
            }

        }
        return transactions;
    }
}
