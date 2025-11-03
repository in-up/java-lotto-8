package lotto.application;

import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.io.InputView;
import lotto.io.OutputView;
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

        // TODO: 당첨번호 입력 및 결과처리
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
}