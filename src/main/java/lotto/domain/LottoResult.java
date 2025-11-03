package lotto.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private final Map<Rank, Integer> results;
    private final int purchaseAmount;

    public LottoResult(int purchaseAmount) {
        this.results = new EnumMap<>(Rank.class);
        this.purchaseAmount = purchaseAmount;
        initializeResults();
    }

    private void initializeResults() {
        for (Rank rank : Rank.values()) {
            if (rank.isWinning()) {
                results.put(rank, 0);
            }
        }
    }

    public void addRank(Rank rank) {
        if (rank.isWinning()) {
            results.put(rank, results.get(rank) + 1);
        }
    }

    public int getTotalPrize() {
        return results.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrize() * entry.getValue())
                .sum();
    }

    public double calculateProfitRate() {
        return (double) getTotalPrize() / purchaseAmount * 100;
    }

    public Map<Rank, Integer> getResults() {
        return results;
    }
}