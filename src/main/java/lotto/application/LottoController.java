package lotto.application;

import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.LottoResult;
import lotto.domain.Rank;
import lotto.domain.WinningNumbers;
import lotto.io.InputView;
import lotto.io.OutputView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        int purchaseAmount = readPurchaseAmountWithRetry();
        List<Lotto> purchasedLottos = purchaseLottos(purchaseAmount);
        printPurchasedLottos(purchasedLottos);

        WinningNumbers winningNumbers = readWinningNumbersWithRetry();
        LottoResult lottoResult = calculateResult(purchasedLottos, winningNumbers, purchaseAmount);

        printResult(lottoResult);
    }

    private int readPurchaseAmountWithRetry() {
        while (true) {
            try {
                return readPurchaseAmount();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private int readPurchaseAmount() {
        String input = inputView.readPurchaseAmount();
        validateNumeric(input);
        return Integer.parseInt(input);
    }

    private void validateNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해야 합니다.");
        }
    }

    private List<Lotto> purchaseLottos(int amount) {
        return lottoMachine.purchaseLottos(amount);
    }

    private void printPurchasedLottos(List<Lotto> lottos) {
        outputView.printPurchaseCount(lottos.size());
        List<List<Integer>> lottoNumbers = lottos.stream()
                .map(Lotto::getNumbers)
                .collect(Collectors.toList());
        outputView.printLottos(lottoNumbers);
    }

    private WinningNumbers readWinningNumbersWithRetry() {
        Lotto winningLotto = readWinningLottoWithRetry();
        int bonusNumber = readBonusNumberWithRetry(winningLotto);
        return new WinningNumbers(winningLotto, bonusNumber);
    }

    private Lotto readWinningLottoWithRetry() {
        while (true) {
            try {
                return readWinningLotto();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Lotto readWinningLotto() {
        String input = inputView.readWinningNumbers();
        List<Integer> numbers = parseWinningNumbers(input);
        return new Lotto(numbers);
    }

    private List<Integer> parseWinningNumbers(String input) {
        try {
            return Arrays.stream(input.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("당첨 번호는 숫자여야 합니다.");
        }
    }

    private int readBonusNumberWithRetry(Lotto winningLotto) {
        while (true) {
            try {
                return readBonusNumber(winningLotto);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private int readBonusNumber(Lotto winningLotto) {
        String input = inputView.readBonusNumber();
        validateNumeric(input);
        int bonusNumber = Integer.parseInt(input);
        validateBonusNumber(bonusNumber, winningLotto);
        return bonusNumber;
    }

    private void validateBonusNumber(int bonusNumber, Lotto winningLotto) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (winningLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    private LottoResult calculateResult(List<Lotto> lottos, WinningNumbers winningNumbers, int purchaseAmount) {
        LottoResult lottoResult = new LottoResult(purchaseAmount);
        for (Lotto lotto : lottos) {
            Rank rank = winningNumbers.calculateRank(lotto);
            lottoResult.addRank(rank);
        }
        return lottoResult;
    }

    private void printResult(LottoResult lottoResult) {
        List<String> statistics = formatStatistics(lottoResult);
        outputView.printWinningStatistics(statistics);
        outputView.printProfitRate(lottoResult.calculateProfitRate());
    }

    private List<String> formatStatistics(LottoResult lottoResult) {
        List<String> statistics = new ArrayList<>();
        Map<Rank, Integer> results = lottoResult.getResults();

        statistics.add(formatRankStatistic(Rank.FIFTH, results.get(Rank.FIFTH)));
        statistics.add(formatRankStatistic(Rank.FOURTH, results.get(Rank.FOURTH)));
        statistics.add(formatRankStatistic(Rank.THIRD, results.get(Rank.THIRD)));
        statistics.add(formatRankStatistic(Rank.SECOND, results.get(Rank.SECOND)));
        statistics.add(formatRankStatistic(Rank.FIRST, results.get(Rank.FIRST)));

        return statistics;
    }

    private String formatRankStatistic(Rank rank, int count) {
        return rank.getDescription() + " - " + count + "개";
    }
}