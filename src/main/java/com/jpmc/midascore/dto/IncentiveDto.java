package com.jpmc.midascore.dto;

public class IncentiveDto {

    private float amount;

    protected IncentiveDto() {}

    public IncentiveDto(float amount) {
        this.amount = amount;
    }

    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount; }

    @Override
    public String toString() { return "IncentiveDto{" + "amount=" + amount + '}'; }
}
