package lotto.application;

import lotto.io.InputView;
import lotto.io.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // TODO: 로또 게임 전체 흐름 구현
        String purchaseAmount = inputView.readPurchaseAmount();
        String winningNumbers = inputView.readWinningNumbers();
        String bonusNumber = inputView.readBonusNumber();
    }
}