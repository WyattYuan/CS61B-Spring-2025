package game2048logic;

import game2048rendering.Side;

import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author Josh Hug
 */
public class GameLogic {
    /**
     * Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c     -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return if there is a merge, returns the 1 + the row number where the merge occurred.
     * if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
//      我的答案
//      如果方格是0，直接ok
        if (board[r][c] == 0) return 0;
//      如果是最顶上的方格，直接ok
        if (r == 0) return 0;
//        对于所有当前方格上面的所有位置
        for (int rIndex = r - 1; rIndex >= 0; rIndex--) {
            if (rIndex < minR) {
                return 0;
            }
//          遇到相同数字的tile，需要合并
            else if (board[rIndex][c] == board[r][c]) {
                board[rIndex][c] = board[rIndex][c] + board[r][c];
                board[r][c] = 0;
                return 1 + rIndex;
            }
//          遇到不同数字的tile
            else if (board[rIndex][c] != 0 && board[rIndex][c] != board[r][c]) {
                board[rIndex + 1][c] = board[r][c];
                if (rIndex + 1 != r) board[r][c] = 0;
                return 0;
            } else if (rIndex == minR) {
                board[rIndex][c] = board[r][c];
                board[r][c] = 0;
                return 0;
            }
        }
        return 0;

//      From Gemini
//        int tileValue = board[r][c];
//        if (tileValue == 0) return 0;
//
//        int destinationR = r;
//
//        // 1. 向上扫描，寻找最远空位或可合并的目标
//        for (int i = r - 1; i >= minR; i--) {
//            if (board[i][c] == 0) {
//                // 当前是空位，暂定为目的地，继续向上看
//                destinationR = i;
//            } else if (board[i][c] == tileValue) {
//                // 发现值相同的方块，执行合并
//                board[i][c] = tileValue * 2;
//                board[r][c] = 0;
//                return 1 + i;
//            } else {
//                // 撞到了不同的方块，停止搜索
//                break;
//            }
//        }
//
//        // 2. 如果最终目的地不是起始位置，则进行移动
//        if (destinationR != r) {
//            board[destinationR][c] = tileValue;
//            board[r][c] = 0;
//        }
//
//        return 0;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board the current state of the board
     * @param c     the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        int nextMinR = 0;
        for (int i = 0; i < board[0].length; i++) {
            nextMinR = Math.max(nextMinR, moveTileUpAsFarAsPossible(board, i, c, nextMinR));
        }
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            tiltColumn(board, i);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
        } else if (side == Side.SOUTH) {
            rotateLeft(board);
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            rotateRight(board);
        } else {
            tiltUp(board);
        }
    }
}
