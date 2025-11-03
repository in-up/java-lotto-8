package lotto;

import lotto.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {
    @Nested
    @DisplayName("당첨 등수 판별")
    class DetermineRank {
        @Test
        @DisplayName("6개 일치하면 1등이다.")
        void first() {
            Rank rank = Rank.of(6, false);

            assertThat(rank).isEqualTo(Rank.FIRST);
            assertThat(rank.getPrize()).isEqualTo(2_000_000_000);
        }

        @Test
        @DisplayName("5개 일치하고 보너스 번호가 일치하면 2등이다.")
        void second() {
            Rank rank = Rank.of(5, true);

            assertThat(rank).isEqualTo(Rank.SECOND);
            assertThat(rank.getPrize()).isEqualTo(30_000_000);
        }

        @Test
        @DisplayName("5개 일치하고 보너스 번호가 일치하지 않으면 3등이다.")
        void third() {
            Rank rank = Rank.of(5, false);

            assertThat(rank).isEqualTo(Rank.THIRD);
            assertThat(rank.getPrize()).isEqualTo(1_500_000);
        }

        @Test
        @DisplayName("4개 일치하면 4등이다.")
        void fourth() {
            Rank rank = Rank.of(4, false);

            assertThat(rank).isEqualTo(Rank.FOURTH);
            assertThat(rank.getPrize()).isEqualTo(50_000);
        }

        @Test
        @DisplayName("3개 일치하면 5등이다.")
        void fifth() {
            Rank rank = Rank.of(3, false);

            assertThat(rank).isEqualTo(Rank.FIFTH);
            assertThat(rank.getPrize()).isEqualTo(5_000);
        }

        @Test
        @DisplayName("2개 이하 일치하면 당첨되지 않는다.")
        void none() {
            Rank rank = Rank.of(2, false);

            assertThat(rank).isEqualTo(Rank.NONE);
            assertThat(rank.getPrize()).isEqualTo(0);
            assertThat(rank.isWinning()).isFalse();
        }
    }
}