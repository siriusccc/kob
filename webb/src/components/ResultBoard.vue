<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
            平局
        </div>
        <div class="result-board-text" v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id == $store.state.user.id">
            lose
        </div>
        <div class="result-board-text" v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id == $store.state.user.id">
            lose
        </div>
        <div class="result-board-text" v-else>
            win
        </div>
        <div class="result-board-btn">
        <button type="button" class="btn btn-info btn-lg" @click="restart">
            重开    
        </button>
    </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();
        const restart = () => {
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })
        }
        return {
            restart
        };
    }
}
</script>

<style scoped>
div.result-board {
    height: 30vh;
    width: 30vw;
    background-color: azure;
    position: absolute;
    top:30vh;
    left: 35vw;
}
div.result-board-text{
    text-align: center;
    color: blue;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 5vh;
}
div.result-board-btn{
    text-align: center;
    padding-top: 4vh;
}
</style>