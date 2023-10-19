package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Record;
import com.sun.corba.se.spi.activation.TCPPortHelper;
import com.sun.jmx.remote.security.JMXSubjectDomainCombiner;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread{
    private final Integer cols;
    private final Integer rows;
    private final Integer inner_wall_count;
    private final int[][] g;
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    private final Player playerA, playerB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;
    private ReentrantLock lock = new ReentrantLock();
    private String status = "playing";    // playing -> finished
    private String loser = "";

    public Game(Integer cols, Integer rows, Integer inner_wall_count, Integer idA, Integer idB){
        this.rows = rows;
        this.cols = cols;
        this.inner_wall_count = inner_wall_count;
        this.g = new int[rows][cols];
        playerA = new Player(idA, rows - 2, 1, new ArrayList<>());
        playerB = new Player(idB, 1, cols - 2, new ArrayList<>());
    }

    public Player getPlayerA(){
        return playerA;
    }

    public Player getPlayerB(){
        return playerB;
    }

    public void setNextStepA(Integer nextStepA){
        lock.lock();
        try{
            this.nextStepA = nextStepA;
        } finally {
            lock.unlock();
        }
    }

    public void setNextStepB(Integer nextStepB){
        lock.lock();
        try{
            this.nextStepB = nextStepB;
        } finally {
            lock.unlock();
        }
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
                if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) continue;
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

    private boolean nextStep(){          // 等待下一步的操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 50; i ++){
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if (nextStepA != null && nextStepB != null) {
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean check_valid(List<Cell> cellsA, List<Cell> cellsB) {
        int n = cellsA.size();
        Cell cell = cellsA.get(n - 1);
        if (g[cell.x][cell.y] == 1) return false;

        for (int i = 0; i < n - 1; i ++ ) {
            if (cellsA.get(i).x == cell.x && cellsA.get(i).y == cell.y)
                return false;
        }


        for (int i = 0; i < n - 1; i ++ ) {
            if (cellsB.get(i).x == cell.x && cellsB.get(i).y == cell.y)
                return false;
        }

        return true;
    }

    private void judge(){        // 判断下一步操作是否合法
        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();

        boolean validA = check_valid(cellsA, cellsB);
        boolean validB = check_valid(cellsB, cellsA);

        if (!validA || !validB) {
            status = "finished";
            if (!validA && !validB) {
                loser = "all";
            } else if (!validA) {
                loser = "A";
            } else {
                loser = "B";
            }
        }

    }

    private void sendResult(){    // 向client公布结果
        JSONObject resp = new JSONObject();
        resp.put("event", "result");         // 表示当前传播的是什么信息: result
        resp.put("loser", loser);            // 传递结果
        saveToDatabase();
        sendAllMessage(resp.toJSONString());
    }

    private void sendMove(){
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event", "move");          // 表示当前传播的是什么信息: move
            resp.put("a_direction", nextStepA);
            resp.put("b_direction", nextStepB);
            sendAllMessage(resp.toJSONString());
            nextStepA = nextStepB = null;
        } finally {
            lock.unlock();
        }

    }

    public String getMapString() {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < rows; i ++) {
            for (int j = 0; j < cols; j ++) {
                res.append(g[i][j]);
            }
        }
        return res.toString();
    }

    private void saveToDatabase(){
        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepString(),
                playerB.getStepString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }

    private void sendAllMessage(String message){
        if (WebSocketServer.users.get(playerA.getId()) != null) {
            WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        }
        if (WebSocketServer.users.get(playerB.getId()) != null) {
            WebSocketServer.users.get(playerB.getId()).sendMessage(message);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i ++){
            if(nextStep()) {              // 进行下一步操作
                judge();
                if(status.equals("playing")){
                    sendMove();
                } else {
                    sendResult();
                    break;
                }
            } else {                      // 没有下一步时, 判断输赢方
                status = "finished";
                lock.lock();
                try {
                    if (nextStepA == null && nextStepB == null){
                        loser = "all";
                    } else if (nextStepA == null){
                        loser = "A";
                    } else if (nextStepB == null){
                        loser = "B";
                    }
                } finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }
}
