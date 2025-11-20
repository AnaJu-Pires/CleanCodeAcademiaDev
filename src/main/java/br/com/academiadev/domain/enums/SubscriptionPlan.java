package br.com.academiadev.domain.enums;

public enum SubscriptionPlan {
    
    BASIC(3),
    PREMIUM(Integer.MAX_VALUE);

    private final int maxActiveCourses;

    SubscriptionPlan(int maxActiveCourses) {
        this.maxActiveCourses = maxActiveCourses;
    }

    public int getMaxActiveCourses() {
        return maxActiveCourses;
    }
}