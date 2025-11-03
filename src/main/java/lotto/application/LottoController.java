package lotto.application;

import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.WinningNumbers;
import lotto.io.InputView;
import lotto.io.OutputView;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoMachine = new LottoMachine();
    }

    public void run() {
        List<Lotto> purchasedLottos = purchaseLottos();
        printPurchasedLottos(purchasedLottos);

        WinningNumbers winningNumbers = readWinningNumbers();

        // TODO: 당첨 통계 계산 및 출력
    }

    private List<Lotto> purchaseLottos() {
        String input = inputView.readPurchaseAmount();
        int amount = Integer.parseInt(input);
        return lottoMachine.purchaseLottos(amount);
    }

    private void printPurchasedLottos(List<Lotto> lottos) {
        outputView.printPurchaseCount(lottos.size());
        List<List<Integer>> lottoNumbers = lottos.stream()
                .map(Lotto::getNumbers)
                .collect(Collectors.toList());
        outputView.printLottos(lottoNumbers);
    }

    private WinningNumbers readWinningNumbers() {
        Lotto winningLotto = readWinningLotto();
        int bonusNumber = readBonusNumber();
        return new WinningNumbers(winningLotto, bonusNumber);
    }

    private Lotto readWinningLotto() {
        String input = inputView.readWinningNumbers();
        List<Integer> numbers = parseWinningNumbers(input);
        return new Lotto(numbers);
    }

    private List<Integer> parseWinningNumbers(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int readBonusNumber() {
        String input = inputView.readBonusNumber();
        return Integer.parseInt(input);
    }
}