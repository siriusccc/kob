import { Game_Object } from "./GameObject";
import { Wall } from "./Wall";
import { Snake } from "./Snake";

export class GameMap extends Game_Object{
    constructor(ctx, parent, store){
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;
        this.rows = 13;
        this.cols = 14;
        this.inner_walls_nums = 30;
        this.walls = [];

        this.snakes = [
            new Snake({
                id: 0,
                color: "#4876EC",
                r: this.rows - 2,
                c: 1,
            }, this),
            new Snake({
                id: 1,
                color: "#F94848",
                r: 1,
                c: this.cols - 2,
            }, this),
        ];

    }

    // check_connect(g, sx, sy, tx, ty){
    //     if(sx == tx && sy == ty) return true;
    //     g[sx][sy] = true;

    //     let dx = [-1, 0, 1, 0], dy = [0, 1, 0, -1];
    //     for(let i = 0; i < 4; i ++){
    //         let next_sx = dx[i] + sx, next_sy = dy[i] + sy;
    //         if(!g[next_sx][next_sy] && this.check_connect(g, next_sx, next_sy, tx, ty))
    //             return true;
    //     }
    //     return false;
    // }

    create_walls(){
        const g = this.store.state.pk.gamemap;
        // for(let r = 0; r < this.rows; r ++){
        //     g[r] = [];
        //     for(let c = 0; c < this.cols; c ++){
        //         g[r][c] = false;
        //     }
        // }

        // for(let r = 0; r < this.rows; r ++){
        //     g[r][0] = g[r][this.cols - 1] = true;
        // }
        // for(let c = 0; c < this.cols; c ++){
        //     g[0][c] = g[this.rows - 1][c] = true;
        // }

        // for(let i = 0; i <this.inner_walls_nums / 2; i ++){
        //     for(let j = 0; j < 1000; j ++){
        //         let c = parseInt(Math.random() * this.cols);
        //         let r = parseInt(Math.random() * this.rows);
        //         if(g[r][c] || g[this.rows - 1 - r][this.cols - 1 - c]) continue;
        //         if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2)
        //             continue;

        //             g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = true;
        //         break;
        //     }
        // }
        // const copy_g = JSON.parse(JSON.stringify(g));
        // if(!this.check_connect(copy_g, this.rows - 2, 1, 1, this.cols - 2)) 
        //     return false;

        for (let r = 0; r < this.rows; r ++ ) {
            for (let c = 0; c < this.cols; c ++ ) {
                if(g[r][c]){
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }

    add_listening_events(){
        this.ctx.canvas.focus();
        this.ctx.canvas.addEventListener("keydown", e => {
            let d = -1;
            if (e.key === 'w') d = 0;
            else if (e.key === 'd') d = 1;
            else if (e.key === 's') d = 2;
            else if (e.key === 'a') d = 3;
            
            if(d >= 0) {
                this.store.state.pk.socket.send(JSON.stringify({    // 发送信息给后端
                    event: "move",
                    direction: d,
                }));
            }
        });
    }

    start(){
        this.create_walls();
        this.add_listening_events();
    }

    update_size(){
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    check_ready(){
        for(const snake of this.snakes){
            if (snake.status !== "idle") return false;
            if (snake.direction === -1) return false;
        }
        return true;
    }

    check_valid(cell){
        for(const wall of this.walls){
            if(wall.r === cell.r && wall.c === cell.c)
                return false;
        }

        for(const snake of this.snakes){
            let k = snake.cells.length;
            if(!snake.check_tail_increase()){
                k --;
            }
            for(let i = 0; i < k; i ++){
                if(snake.cells[i].r === cell.r && snake.cells[i].c === cell.c){
                    return false;
                }
            }
        }
        return true;
    }

    next_step(){
        for (const snake of this.snakes) {
            snake.next_step();
        }

    }

    update(){
        this.update_size();
        if (this.check_ready()) {
            this.next_step();
        }
        this.render();
    }

    render(){
        const color_even = "#AAD751", color_odd = "#A2D149";
        for (let r = 0; r < this.rows; r ++ ){
            for (let c = 0; c < this.cols; c ++ ){
                if((r + c) % 2 == 0){
                    this.ctx.fillStyle = color_even;
                }else{
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}