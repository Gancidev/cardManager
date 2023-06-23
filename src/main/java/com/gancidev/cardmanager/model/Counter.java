package com.gancidev.cardmanager.model;

import java.util.List;

import com.gancidev.cardmanager.Enum.TypeEnum;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Counter {

    private String reference;

    private Integer counter;
    private Double amountAccredit;
    private Double amountPayments;

    private Integer counterAdmin;
    private Integer counterCustomers;
    private Integer counterMerchants;

    public Counter(){
        this.reference = TypeEnum.TRANSACTION.toString();
        this.counter = 0;
        this.amountAccredit = 0.0;
        this.amountPayments = 0.0;
    }


    public Counter(List<Double> counterAmount, Integer counter){
        this.reference = TypeEnum.TRANSACTION.toString();
        this.counter = counter;
        try {
            if(counterAmount.get(0)>0){
                this.amountAccredit = counterAmount.get(0);
                this.amountPayments = counterAmount.get(1);
            }else{
                this.amountPayments = counterAmount.get(0);
                this.amountAccredit = counterAmount.get(1);
            }
        } catch (IndexOutOfBoundsException e) {
        }
    }

    public Counter(Integer counter){
        this.reference = TypeEnum.CARD.toString();
        this.counter = counter;
    }

    public Counter(Integer counterAdmin, Integer counterMerchants, Integer counterCustomers){
        this.reference = TypeEnum.USER.toString();
        this.counterAdmin = counterAdmin;
        this.counterMerchants = counterMerchants;
        this.counterCustomers = counterCustomers;
    }
}