package com.kob.backend.consumer.utils;

import com.sun.corba.se.spi.activation.TCPPortHelper;

import java.util.Random;

public class Game {
    private final Integer cols;
    private final Integer rows;
    private final Integer inner_wall_count;
    private final int[][] g;
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public Game(Integer cols, Integer rows, Integer inner_wall_count){
        this.rows = rows;
        this.cols = cols;
        this.inner_wall_count = inner_wall_count;
        this.g = new int[rows][cols];
    }

    public int[][] getG() {
        return g;
    }

    public boolean check_connect(int sx, int sy, int tx, int ty){
        if(sx == tx && sy == ty) return true;
        g[sx][sy] = 1;

        for(int i = 0; i < 4; i ++){
            int next_sx = dx[i] + sx, next_sy = dy[i] + sy;
            if(next_sx >= 0 && next_sx < this.rows && next_sy >= 0 && next_sy < this.cols && g[next_sx][next_sy] == 0){
                if(check_connect(next_sx, next_sy, tx, ty)){
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        g[sx][sy] = 0;
        return false;
    }

    private boolean draw(){
        for(int i = 0; i < this.rows; i ++)
            for(int j = 0; j < this.cols; j ++)
                g[i][j] = 0;

        for(int i = 0; i < this.rows; i ++){
            g[i][0] = g[i][this.cols - 1] = 1;
        }
        for(int j = 0; j < this.cols; j ++){
            g[0][j] = g[this.rows - 1][j] = 1;
        }

        Random random = new Random();
        for(int i = 0; i <this.inner_wall_count / 2; i ++){
            for(int j = 0; j < 1000; j ++){
                int c = random.nextInt(this.cols);
                int r = random.nextInt(this.rows);
                if(g[r][c] == 1 || g[this.rows - 1 - r][this.cols - 1 - c] == 1) continue;
                if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2)
                    continue;

                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }
        return check_connect(this.rows - 2, 1, 1, this.cols - 2);
    }

    public void createMap(){
        for(int i = 0; i < 1000; i ++){
            if(draw()){
                break;
            }
        }
    }


}
