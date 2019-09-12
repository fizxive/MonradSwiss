package com.imaginaryrhombus.monradswiss

import com.google.common.truth.Truth
import org.junit.Test
import kotlin.math.max

class PlayerModelTest {

    @Test
    fun testCreate() {
        val playerModel = PlayerModel("Alice")

        playerModel.run {
            Truth.assertThat(name)
                .isEqualTo("Alice")
            Truth.assertThat(matches)
                .isEqualTo(0)
            Truth.assertThat(wins)
                .isEqualTo(0)
            Truth.assertThat(loses)
                .isEqualTo(0)
            Truth.assertThat(draws)
                .isEqualTo(0)
            Truth.assertThat(winPoints)
                .isEqualTo(0)
            Truth.assertThat(winRate)
                .isNaN()
            Truth.assertThat(opponentWinPoints)
                .isEqualTo(0)
            Truth.assertThat(opponentWinRate)
                .isZero()
            Truth.assertThat(opponents)
                .isEmpty()
        }
    }

    @Test
    fun testMatchResults() {
        val playerModel = PlayerModel("Alice")

        fun winPointTest(wins: Int, loses: Int, draws: Int) {
            playerModel.wins = wins
            playerModel.loses = loses
            playerModel.draws = draws

            Truth.assertThat(playerModel.winPoints)
                .isEqualTo(wins * 3 + loses * 0 + draws * 1)
        }

        winPointTest(0, 0, 0)
        winPointTest(1, 0, 0)
        winPointTest(0, 1, 0)
        winPointTest(0, 0, 1)
        winPointTest(1, 1, 0)
        winPointTest(1, 0, 1)
        winPointTest(0, 1, 1)
        winPointTest(1, 1, 1)
        winPointTest(3, 2, 0)
        winPointTest(0, 5, 3)
        winPointTest(7, 0, 12)
        winPointTest(9, 99, 999)
    }
    
    @Test
    fun testMatchesCount() {
        val playerModel = PlayerModel("Alice")

        fun matchesCountTest(wins: Int, loses: Int, draws: Int) {
            playerModel.wins = wins
            playerModel.loses = loses
            playerModel.draws = draws

            Truth.assertThat(playerModel.matches)
                .isEqualTo(wins + loses + draws)
        }

        matchesCountTest(0, 0, 0)
        matchesCountTest(1, 0, 0)
        matchesCountTest(0, 1, 0)
        matchesCountTest(0, 0, 1)
        matchesCountTest(1, 1, 0)
        matchesCountTest(1, 0, 1)
        matchesCountTest(0, 1, 1)
        matchesCountTest(1, 1, 1)
        matchesCountTest(3, 2, 0)
        matchesCountTest(0, 5, 3)
        matchesCountTest(7, 0, 12)
        matchesCountTest(9, 99, 999)
    }

    @Test
    fun testWinRate() {
        val playerModel = PlayerModel("Alice")

        fun winRateTest(wins: Int, loses: Int, draws: Int) {
            playerModel.wins = wins
            playerModel.loses = loses
            playerModel.draws = draws

            val matches = wins + loses + draws

            Truth.assertThat(playerModel.winRate)
                .isEqualTo(
                    if (matches == 0) Float.NaN
                    else (wins * 3.0f + loses * 0.0f + draws * 1.0f) / ((matches) * 3)
                )
        }

        winRateTest(0, 0, 0)
        winRateTest(1, 0, 0)
        winRateTest(0, 1, 0)
        winRateTest(0, 0, 1)
        winRateTest(1, 1, 0)
        winRateTest(1, 0, 1)
        winRateTest(0, 1, 1)
        winRateTest(1, 1, 1)
        winRateTest(3, 2, 0)
        winRateTest(0, 5, 3)
        winRateTest(7, 0, 12)
        winRateTest(9, 99, 999)
    }

    @Test
    fun testOpponent() {
        val alice = PlayerModel("Alice")

        fun createPlayerModel(name: String, wins: Int, loses: Int, draws: Int): PlayerModel {
            return PlayerModel(name).apply {
                this.wins = wins; this.loses = loses; this.draws = draws
            }
        }

        fun testOpponentRecord(vararg opponents: PlayerModel) {

            alice.resetRecords()

            alice.opponents.addAll(opponents)

            var totalWins = 0
            var totalLoses = 0
            var totalDraws = 0
            var totalWinRate = 0.0f

            opponents.forEach {
                totalWins += it.wins
                totalLoses += it.loses
                totalDraws += it.draws

                totalWinRate += max(it.winRate, PlayerModel.leastWinRatePerPlayer)
            }

            alice.run {
                Truth.assertThat(opponentWinPoints)
                    .isEqualTo(totalWins * 3 + totalLoses * 0 + totalDraws * 1)
                Truth.assertThat(opponentWinRate)
                    .isEqualTo(
                        if (opponents.isEmpty()) Float.NaN
                        else totalWinRate / opponents.size
                    )
            }
        }

        val bob = createPlayerModel("Bob", 1, 0, 0)

        testOpponentRecord(bob)

        val charlie = createPlayerModel("Charlie", 0, 1, 0)

        testOpponentRecord(charlie)

        val dave = createPlayerModel("dave", 0, 0, 1)

        testOpponentRecord(dave)

        testOpponentRecord(bob, charlie)
        testOpponentRecord(bob, dave)
        testOpponentRecord(charlie, dave)

        val ellen = createPlayerModel("Ellen", 3, 2, 0)

        testOpponentRecord(ellen)

        val frank = createPlayerModel("frank", 0, 6, 3)

        testOpponentRecord(frank)

        val grace = createPlayerModel("Grace", 99, 0, 11)

        testOpponentRecord(grace)

        val heidi = createPlayerModel("Heidi", 200, 550, 97)

        testOpponentRecord(heidi)

        testOpponentRecord(ellen, frank)
        testOpponentRecord(ellen, grace)
        testOpponentRecord(ellen, heidi)
        testOpponentRecord(frank, grace)
        testOpponentRecord(frank, heidi)
        testOpponentRecord(grace, heidi)
        testOpponentRecord(ellen, frank, grace)
        testOpponentRecord(ellen, frank, heidi)
        testOpponentRecord(ellen, grace, heidi)
        testOpponentRecord(frank, grace, heidi)
        testOpponentRecord(ellen, frank, grace, heidi)
    }
}