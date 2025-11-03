package lotto;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class LottoTest {
    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void createLottoByOverSize() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void createLottoByDuplicatedNumber() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("로또 번호 개수 검증")
    class ValidateSize {
        @Test
        @DisplayName("로또 번호가 6개 미만이면 예외가 발생한다.")
        void lessThanSix() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("6개");
        }

        @Test
        @DisplayName("로또 번호가 6개 초과면 예외가 발생한다.")
        void moreThanSix() {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("6개");
        }
    }

    @Nested
    @DisplayName("로또 번호 범위 검증")
    class ValidateRange {
        @ParameterizedTest
        @ValueSource(ints = {0, -1, -10})
        @DisplayName("로또 번호가 1보다 작으면 예외가 발생한다.")
        void lessThanOne(int number) {
            assertThatThrownBy(() -> new Lotto(List.of(number, 2, 3, 4, 5, 6)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45");
        }

        @ParameterizedTest
        @ValueSource(ints = {46, 50, 100})
        @DisplayName("로또 번호가 45보다 크면 예외가 발생한다.")
        void moreThanFortyFive(int number) {
            assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, number)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45");
        }
    }

    @Nested
    @DisplayName("로또 번호 일치 개수 확인")
    class CountMatch {
        @Test
        @DisplayName("일치하는 번호가 없으면 0을 반환한다.")
        void noMatch() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto other = new Lotto(List.of(7, 8, 9, 10, 11, 12));

            assertThat(lotto.countMatch(other)).isEqualTo(0);
        }

        @Test
        @DisplayName("3개 일치하면 3을 반환한다.")
        void matchThree() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto other = new Lotto(List.of(1, 2, 3, 7, 8, 9));

            assertThat(lotto.countMatch(other)).isEqualTo(3);
        }

        @Test
        @DisplayName("6개 모두 일치하면 6을 반환한다.")
        void matchAll() {
            Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            Lotto other = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            assertThat(lotto.countMatch(other)).isEqualTo(6);
        }
    }

    @Test
    @DisplayName("로또 번호에 특정 숫자가 포함되어 있는지 확인한다.")
    void contains() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.contains(1)).isTrue();
        assertThat(lotto.contains(7)).isFalse();
    }
}