package lotto.io;

import java.util.List;

public class OutputView {
    private static final String PURCHASE_COUNT_MESSAGE = "%d개를 구매했습니다.";
    private static final String WINNING_STATISTICS_HEADER = "당첨 통계";
    private static final String WINNING_STATISTICS_DIVIDER = "---";
    private static final String WINNING_STATISTICS_FORMAT = "%s - %d개";
    private static final String PROFIT_RATE_MESSAGE = "총 수익률은 %.1f%%입니다.";
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    public void printPurchaseCount(int count) {
        System.out.println();
        System.out.println(String.format(PURCHASE_COUNT_MESSAGE, count));
    }

    public void printLottos(List<List<Integer>> lottos) {
        for (List<Integer> lotto : lottos) {
            System.out.println(lotto);
        }
    }

    public void printWinningStatistics(List<String> statistics) {
        System.out.println();
        System.out.println(WINNING_STATISTICS_HEADER);
        System.out.println(WINNING_STATISTICS_DIVIDER);
        for (String statistic : statistics) {
            System.out.println(statistic);
        }
    }

    public void printProfitRate(double profitRate) {
        System.out.println(String.format(PROFIT_RATE_MESSAGE, profitRate));
    }

    public void printErrorMessage(String message) {
        System.out.println(ERROR_MESSAGE_PREFIX + message);
    }
}