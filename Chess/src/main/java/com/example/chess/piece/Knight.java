package com.example.chess.piece;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    final int[][] CANDIDATE_MOVE_COORDINATES = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {2, -1}, {2, 1}, {1, -2}, {1, 2}};

    public Knight(int pos_i, int pos_j, PiecesType type) {
        super(pos_i, pos_j, type);
        pieceKind = PieceKind.KNIGHT;
    }

    /**
     * Calculate all the legal moves for a piece
     * @param board The board in which the piece is placed
     * @return All the legal moves of the piece
     */
    @Override
    public List<Move> legalMoves(Board board) {
        int candidateDestinationCoordinateI;
        int candidateDestinationCoordinateJ;
        Tile currentTile = board.getTile(piecePosI, piecePosJ);
        List<Move> legalMoves = new ArrayList<>();

        // for every possible move this piece can make
        for (int[] current : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinateI = piecePosI + current[0];
            candidateDestinationCoordinateJ = piecePosJ + current[1];

            // if the tile has valid coordinates
            if (Board.isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
                Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinateI, candidateDestinationCoordinateJ);

                // if tile is not occupied
                if (candidateDestinationTile.isEmpty()) {
                    legalMoves.add(new RegularMove(board, currentTile, candidateDestinationTile, this)); // regular move
                } else {
                    Piece pieceAtDestination = candidateDestinationTile.getPiece();

                    // if it is an enemy piece where we want to go
                    if (pieceAtDestination.getType() != this.type) {
                        legalMoves.add(new CaptureMove(board, currentTile, candidateDestinationTile, this, candidateDestinationTile.getPiece())); // capture move
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        if (type.equals(PiecesType.WHITE))
            return PieceKind.KNIGHT.toString();
        else
            return PieceKind.KNIGHT.toString().toLowerCase();
    }
}
