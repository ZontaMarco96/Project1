package com.Zonta.Gioco;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TrisController {

    private char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private String winnerMessage = null;

    public TrisController() {
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
        currentPlayer = 'X';
        winnerMessage = null;
    }

    private String checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0] + " ha vinto!";
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[0][i] + " ha vinto!";
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0] + " ha vinto!";
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2] + " ha vinto!";

        return null;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("board", board);
        model.addAttribute("currentPlayer", currentPlayer);
        model.addAttribute("winnerMessage", winnerMessage);
        return "index";
    }

    @PostMapping("/move")
    public String move(@RequestParam int row, @RequestParam int col, Model model) {
        if (winnerMessage == null && board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            winnerMessage = checkWinner();
            if (winnerMessage == null) {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
        model.addAttribute("board", board);
        model.addAttribute("currentPlayer", currentPlayer);
        model.addAttribute("winnerMessage", winnerMessage);
        return "index";
    }

    @PostMapping("/reset")
    public String reset(Model model) {
        resetBoard();
        model.addAttribute("board", board);
        model.addAttribute("currentPlayer", currentPlayer);
        model.addAttribute("winnerMessage", winnerMessage);
        return "index";
    }
}
