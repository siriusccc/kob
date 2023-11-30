const GAME_OBJECTS = [];

export class Game_Object{
    constructor(){
        GAME_OBJECTS.push(this);
        this.timedelta = 0;
        this.has_called = false;
    }

    start(){
        
    }

    update(){

    }

    on_destory(){

    }

    destory(){
        this.on_destory();

        for(let i in GAME_OBJECTS){
            const obj = Game_Object[i];
            if(obj === this){
                GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}

let last_timestamp;
const step = timestamp => {
    for(let obj of GAME_OBJECTS){
        if(!obj.has_called){
            obj.has_called = true;
            obj.start();
        }else{
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)