package lotto;

import lotto.application.LottoController;
import lotto.io.InputView;
import lotto.io.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        LottoController lottoController = new LottoController(inputView, outputView);
        lottoController.run();
    }
}
