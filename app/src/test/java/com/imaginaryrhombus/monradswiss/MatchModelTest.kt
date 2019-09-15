package com.imaginaryrhombus.monradswiss

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import kotlin.math.max

class MatchModelTest {

    private lateinit var alice: PlayerModel
    private lateinit var bob: PlayerModel

    @Before
    fun setUp() {
        alice = PlayerModel("Alice")
        bob = PlayerModel("Bob")
    }

    @Test
    fun testCreate() {
        MatchModel(alice, bob)

        alice.run {
            Truth.assertThat(opponents)
                .isEmpty()
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
        }

        bob.run {
            Truth.assertThat(opponents)
                .isEmpty()
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
        }
    }

    @Test
    fun testPlayer1Win() {
        val matchModel = MatchModel(alice, bob)

        matchModel.setPlayer1Win()

        alice.run {
            Truth.assertThat(opponents)
                .containsExactly(bob)
            Truth.assertThat(matches)
                .isEqualTo(1)
            Truth.assertThat(wins)
                .isEqualTo(1)
            Truth.assertThat(loses)
                .isEqualTo(0)
            Truth.assertThat(draws)
                .isEqualTo(0)
            Truth.assertThat(winPoints)
                .isEqualTo(PlayerModel.WIN_POINT_WIN)
            Truth.assertThat(winRate)
                .isEqualTo(1.0f)
            Truth.assertThat(opponentWinPoints)
                .isEqualTo(0)
            Truth.assertThat(opponentWinRate)
                .isZero()
        }

        bob.run {
            Truth.assertThat(opponents)
                .containsExactly(alice)
            Truth.assertThat(matches)
                .isEqualTo(1)
            Truth.assertThat(wins)
                .isEqualTo(0)
            Truth.assertThat(loses)
                .isEqualTo(1)
            Truth.assertThat(draws)
                .isEqualTo(0)
            Truth.assertThat(winPoints)
                .isEqualTo(PlayerModel.WIN_POINT_LOSE)
            Truth.assertThat(winRate)
                .isZero()
            Truth.assertThat(opponentWinPoints)
                .isEqualTo(PlayerModel.WIN_POINT_WIN)
            Truth.assertThat(opponentWinRate)
                .isEqualTo(1.0f)
        }
    }

    @Test
    fun testPlayer2Win() {
        val matchModel = MatchModel(alice, bob)

        matchModel.setPlayer2Win()

        alice.run {
            Truth.assertThat(opponents)
                .containsExactly(bob)
            Truth.assertThat(matches)
                .isEqualTo(1)
            Truth.assertThat(wins)
                .isEqualTo(0)
            Truth.assertThat(loses)
                .isEqualTo(1)
            Truth.assertThat(draws)
                .isEqualTo(0)
            Truth.assertThat(winPoints)
                .isEqualTo(PlayerModel.WIN_POINT_LOSE)
            Truth.assertThat(winRate)
                .isZero()
            Truth.assertThat(opponentWinPoints)
                .isEqualTo(PlayerModel.WIN_POINT_WIN)
            Truth.assertThat(opponentWinRate)
                .isEqualTo(1.0f)
        }

        bob.run {
            Truth.assertThat(opponents)
                .containsExactly(alice)
            Truth.assertThat(matches)
                .isEqualTo(1)
            Truth.assertThat(wins)
                .isEqualTo(1)
            Truth.assertThat(loses)
                .isEqualTo(0)
            Truth.assertThat(draws)
                .isEqualTo(0)
            Truth.assertThat(winPoints)
                .isEqualTo(PlayerModel.WIN_POINT_WIN)
            Truth.assertThat(winRate)
                .isEqualTo(1.0f)
            Truth.assertThat(opponentWinPoints)
                .isEqualTo(0)
            Truth.assertThat(opponentWinRate)
                .isZero()
        }
    }


    @Test
    fun testDraw() {
        val matchModel = MatchModel(alice, bob)

        matchModel.setDraw()

        alice.run {
            Truth.assertThat(opponents)
                .containsExactly(bob)
            Truth.assertThat(matches)
                .isEqualTo(1)
            Truth.assertThat(wins)
                .isEqualTo(0)
            Truth.assertThat(loses)
                .isEqualTo(0)
            Truth.assertThat(draws)
                .isEqualTo(1)
            Truth.assertThat(winPoints)
                .isEqualTo(PlayerModel.WIN_POINT_DRAW)
            Truth.assertThat(winRate)
                .isEqualTo(PlayerModel.WIN_POINT_DRAW.toFloat()
                        / PlayerModel.WIN_RATE_MATCH_MAGNIFICATION)
            Truth.assertThat(opponentWinPoints)
                .isEqualTo(PlayerModel.WIN_POINT_DRAW)
            Truth.assertThat(opponentWinRate)
                .isEqualTo(
                    max(PlayerModel.WIN_POINT_DRAW.toFloat()
                            / PlayerModel.WIN_RATE_MATCH_MAGNIFICATION
                        , PlayerModel.leastWinRatePerPlayer)
                )
        }

        bob.run {
            Truth.assertThat(opponents)
                .containsExactly(alice)
            Truth.assertThat(matches)
                .isEqualTo(1)
            Truth.assertThat(wins)
                .isEqualTo(0)
            Truth.assertThat(loses)
                .isEqualTo(0)
            Truth.assertThat(draws)
                .isEqualTo(1)
            Truth.assertThat(winPoints)
                .isEqualTo(PlayerModel.WIN_POINT_DRAW)
            Truth.assertThat(winRate)
                .isEqualTo(PlayerModel.WIN_POINT_DRAW.toFloat()
                        / PlayerModel.WIN_RATE_MATCH_MAGNIFICATION)
            Truth.assertThat(opponentWinPoints)
                .isEqualTo(PlayerModel.WIN_POINT_DRAW)
            Truth.assertThat(opponentWinRate)
                .isEqualTo(
                    max(PlayerModel.WIN_POINT_DRAW.toFloat()
                            / PlayerModel.WIN_RATE_MATCH_MAGNIFICATION
                        , PlayerModel.leastWinRatePerPlayer)
                )
        }
    }
}