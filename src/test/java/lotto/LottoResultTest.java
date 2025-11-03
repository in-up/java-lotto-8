package lotto;

import lotto.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {
    @Nested
    @DisplayName("수익률 계산")
    class CalculateProfitRate {
        @Test
        @DisplayName("총 당첨금이 0원이면 수익률은 0%이다.")
        void zeroPrize() {
            LottoResult lottoResult = new LottoResult(8000);

            assertThat(lottoResult.calculateProfitRate()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("8000원으로 5000원 당첨되면 수익률은 62.5%이다.")
        void exampleCase() {
            LottoResult lottoResult = new LottoResult(8000);
            lottoResult.addRank(Rank.FIFTH);

            assertThat(lottoResult.calculateProfitRate()).isEqualTo(62.5);
        }

        @Test
        @DisplayName("1000원으로 5000원 당첨되면 수익률은 500.0%이다.")
        void fiveHundredPercent() {
            LottoResult lottoResult = new LottoResult(1000);
            lottoResult.addRank(Rank.FIFTH);

            assertThat(lottoResult.calculateProfitRate()).isEqualTo(500.0);
        }

        @Test
        @DisplayName("14000원으로 1등 당첨되면 수익률은 약 14285714.3%이다.")
        void firstPrize() {
            LottoResult lottoResult = new LottoResult(14000);
            lottoResult.addRank(Rank.FIRST);

            double profitRate = lottoResult.calculateProfitRate();
            assertThat(profitRate).isGreaterThan(14285714.0);
            assertThat(profitRate).isLessThan(14285715.0);
        }
    }

    @Nested
    @DisplayName("총 당첨금 계산")
    class CalculateTotalPrize {
        @Test
        @DisplayName("5등 1개 당첨되면 총 당첨금은 5000원이다.")
        void oneFifth() {
            LottoResult lottoResult = new LottoResult(8000);
            lottoResult.addRank(Rank.FIFTH);

            assertThat(lottoResult.getTotalPrize()).isEqualTo(5_000);
        }

        @Test
        @DisplayName("5등 2개, 4등 1개 당첨되면 총 당첨금은 60000원이다.")
        void multiplePrizes() {
            LottoResult lottoResult = new LottoResult(10000);
            lottoResult.addRank(Rank.FIFTH);
            lottoResult.addRank(Rank.FIFTH);
            lottoResult.addRank(Rank.FOURTH);

            assertThat(lottoResult.getTotalPrize()).isEqualTo(60_000);
        }

        @Test
        @DisplayName("당첨되지 않으면 총 당첨금은 0원이다.")
        void noWinning() {
            LottoResult lottoResult = new LottoResult(5000);
            lottoResult.addRank(Rank.NONE);
            lottoResult.addRank(Rank.NONE);

            assertThat(lottoResult.getTotalPrize()).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("당첨 등수별 개수를 집계한다.")
    void aggregateRanks() {
        LottoResult lottoResult = new LottoResult(10000);
        lottoResult.addRank(Rank.FIFTH);
        lottoResult.addRank(Rank.FIFTH);
        lottoResult.addRank(Rank.FOURTH);
        lottoResult.addRank(Rank.NONE);

        assertThat(lottoResult.getResults().get(Rank.FIFTH)).isEqualTo(2);
        assertThat(lottoResult.getResults().get(Rank.FOURTH)).isEqualTo(1);
        assertThat(lottoResult.getResults().get(Rank.THIRD)).isEqualTo(0);
    }
}