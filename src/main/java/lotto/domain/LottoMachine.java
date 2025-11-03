package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class LottoMachine {
    private static final int LOTTO_PRICE = 1000;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    public List<Lotto> purchaseLottos(int amount) {
        validateAmount(amount);
        int count = calculateLottoCount(amount);
        return generateLottos(count);
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("구입 금액은 0보다 커야 합니다.");
        }
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    private int calculateLottoCount(int amount) {
        return amount / LOTTO_PRICE;
    }

    private List<Lotto> generateLottos(int count) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                    MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER, LOTTO_NUMBER_COUNT
            );
            lottos.add(new Lotto(numbers));
        }
        return lottos;
    }
}