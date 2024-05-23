package model.order;

public enum OrderStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    DELIVERED;

    public String toString() {
        return switch (this) {
            case PENDING -> "PENDING";
            case ACCEPTED -> "ACCEPTED";
            case REJECTED -> "REJECTED";
            case DELIVERED -> "DELIVERED";
        };
    }
}

