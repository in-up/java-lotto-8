package lotto;

import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoMachineTest {
    private final LottoMachine lottoMachine = new LottoMachine();

    @Nested
    @DisplayName("로또 구매 금액 검증")
    class ValidatePurchaseAmount {
        @ParameterizedTest
        @ValueSource(ints = {1500, 2300, 999, 1001})
        @DisplayName("구입 금액이 1000원 단위가 아니면 예외가 발생한다.")
        void notThousandUnit(int amount) {
            assertThatThrownBy(() -> lottoMachine.purchaseLottos(amount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1,000원 단위");
        }

        @Test
        @DisplayName("구입 금액이 0원이면 예외가 발생한다.")
        void zeroAmount() {
            assertThatThrownBy(() -> lottoMachine.purchaseLottos(0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("로또 구매")
    class PurchaseLottos {
        @Test
        @DisplayName("1000원으로 1개의 로또를 구매한다.")
        void purchaseOneLotto() {
            List<Lotto> lottos = lottoMachine.purchaseLottos(1000);

            assertThat(lottos).hasSize(1);
        }

        @Test
        @DisplayName("5000원으로 5개의 로또를 구매한다.")
        void purchaseFiveLottos() {
            List<Lotto> lottos = lottoMachine.purchaseLottos(5000);

            assertThat(lottos).hasSize(5);
        }

        @Test
        @DisplayName("구매한 로또는 정상적으로 생성된다.")
        void purchasedLottosAreValid() {
            List<Lotto> lottos = lottoMachine.purchaseLottos(3000);

            assertThat(lottos).hasSize(3);
            for (Lotto lotto : lottos) {
                assertThat(lotto.getNumbers()).hasSize(6);
            }
        }
    }
}