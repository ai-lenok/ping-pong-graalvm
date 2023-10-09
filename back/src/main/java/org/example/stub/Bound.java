package org.example.stub;

public class Bound {
    private final long lower;
    private final long upper;

    public Bound(long low, long upper) {
        this.lower = low;
        this.upper = upper;
    }

    public long getLower() {
        return lower;
    }

    public long getUpper() {
        return upper;
    }
}
