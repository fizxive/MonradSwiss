package com.imaginaryrhombus.monradswiss

import com.google.common.truth.Truth
import org.junit.Test

class PlayerModelTest {

    @Test
    fun testCreate() {
        val playerModel = PlayerModel("Alice")

        playerModel.apply {
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
        val playerModel = PlayerModel("Alice")

        playerModel.opponents.add(PlayerModel("Bob").apply {
            wins = 1; loses = 0; draws = 0
        })

        Truth.assertThat(playerModel.opponentWinRate)
            .isEqualTo(1.0f)

        // TODO : Opponent Test
    }
}