/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

/**
 *
 * @author yib5063
 */
public class Player {
    String name;
    int score;

    public Player(String name, int Score) {
        this.name = name;
        this.score = Score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int Score) {
        this.score = Score;
    }
    
    
    
    
}
