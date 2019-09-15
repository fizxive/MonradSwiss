package com.imaginaryrhombus.monradswiss

/**
 * 対戦そのものを表すModel.
 * @param player1 プレイヤー1.
 * @param player2 プレイヤー2.
 * player1 != player2 を守ること.
 */
class MatchModel(private val player1: PlayerModel, private val player2: PlayerModel) {

    /**
     * 対戦が終了したかのフラグ.
     * 二重で結果を適用しないために使用する.
     */
    private var ended = false

    /**
     * この対戦に対して、プレイヤー1が勝利したと情報を設定する.
     */
    fun setPlayer1Win() {
        if (!ended) {
            player1.wins += 1
            player2.loses += 1
            processMatchEnd()
        }
    }

    /**
     * この対戦に対して、プレイヤー2が勝利したと情報を設定する.
     */
    fun setPlayer2Win() {
        if (!ended) {
            player1.loses += 1
            player2.wins += 1
            processMatchEnd()
        }
    }

    /**
     * この対戦に対して、引き分けとなったことを設定する.
     */
    fun setDraw() {
        if (!ended) {
            player1.draws += 1
            player2.draws += 1
            processMatchEnd()
        }
    }

    /**
     * 対戦結果設定時の胸痛処理.
     */
    private fun processMatchEnd() {
        ended = true
        player1.opponents.add(player2)
        player2.opponents.add(player1)
    }

}