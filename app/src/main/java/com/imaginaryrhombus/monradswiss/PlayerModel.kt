package com.imaginaryrhombus.monradswiss

import androidx.annotation.VisibleForTesting
import kotlin.math.max

/**
 * 各プレイヤーの記録するべきデータ.
 * @param name プレイヤー名.
 */
data class PlayerModel(val name: String) {

    /**
     * これまでの対戦相手.
     */
    var opponents = mutableListOf<PlayerModel>()

    /**
     * 勝利数.
     */
    var wins = 0

    /**
     * 敗北数.
     */
    var loses = 0

    /**
     * 引き分け数.
     */
    var draws = 0

    /**
     * マッチによるポイント.
     */
    val winPoints: Int
    get() {
        return wins * WIN_POINT_WIN + loses * WIN_POINT_LOSE + draws * WIN_POINT_DRAW
    }

    /**
     * マッチ数.
     */
    val matches : Int
    get() {
        return wins + draws + loses
    }

    /**
     * 勝率.
     * マッチ数が 0 の場合は不定な値を返す.
     */
    val winRate : Float
    get() {
        return if (matches <= 0) {
            Float.NaN
        } else {
            winPoints.toFloat() / (matches * WIN_RATE_MATCH_MAGNIFICATION)
        }
    }

    /**
     * 対戦相手の勝利点平均.
     */
    val opponentWinPoints : Int
    get() {
        return if (opponents.isNotEmpty()) {
            var sumPoint = 0
            opponents.forEach {
                sumPoint += it.winPoints
            }
            sumPoint
        } else {
            0
        }
    }

    /**
     * プレイヤー名以外をリセットする.
     */
    fun resetRecords() {
        opponents.clear()
        wins = 0
        loses = 0
        draws = 0
    }

    /**
     * 対戦相手の勝利率平均.
     */
    val opponentWinRate : Float
    get() {
        return if (opponents.isNotEmpty()) {
            var sumWinRate = 0.0f
            
            opponents.forEach {
                sumWinRate += max(it.winRate, leastWinRatePerPlayer)
            }
            sumWinRate / opponents.size
        } else {
            0.0f
        }
    }

    companion object {
        /**
         * 勝利時のポイント増加量.
         */
        const val WIN_POINT_WIN = 3

        /**
         * 敗北のポイント増加量.
         */
        const val WIN_POINT_LOSE = 0

        /**
         * 引き分けのポイント増加量.
         */
        const val WIN_POINT_DRAW = 1

        /**
         * 勝率計算時のマッチ数に乗ずる値.
         */
        @VisibleForTesting
        const val WIN_RATE_MATCH_MAGNIFICATION = 3

        /**
         * オポ計算時のプレイヤーごとの最小勝率.
         */
        var leastWinRatePerPlayer = 0.0f
    }
}