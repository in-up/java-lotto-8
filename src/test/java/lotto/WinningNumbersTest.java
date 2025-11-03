package lotto;

import lotto.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningNumbersTest {
    @Nested
    @DisplayName("보너스 번호 검증")
    class ValidateBonusNumber {
        @ParameterizedTest
        @ValueSource(ints = {0, -1, -10})
        @DisplayName("보너스 번호가 1보다 작으면 예외가 발생한다.")
        void lessThanOne(int bonusNumber) {
            Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            assertThatThrownBy(() -> new WinningNumbers(winningLotto, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45");
        }

        @ParameterizedTest
        @ValueSource(ints = {46, 50, 100})
        @DisplayName("보너스 번호가 45보다 크면 예외가 발생한다.")
        void moreThanFortyFive(int bonusNumber) {
            Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            assertThatThrownBy(() -> new WinningNumbers(winningLotto, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45");
        }

        @Test
        @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
        void duplicateWithWinningNumbers() {
            Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            assertThatThrownBy(() -> new WinningNumbers(winningLotto, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("중복");
        }
    }
}