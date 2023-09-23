<template>
  <div>
    <div>bot_name:{{ bot_name }}</div>
    <div>bot_fig:{{ bot_rating }}</div>
  </div>
  <router-view></router-view>
</template>

<script>
import $ from 'jquery';
import {ref} from 'vue';

export default{
  name:"app",
  setup:() => {
    let bot_name = ref("");
    let bot_rating = ref("");

    $.ajax({
      url: "http://localhost:3000/pk/bot/",
      type: "get",
      success: resp => {
        bot_name.value = resp.name;
      }
    } 
    );

    return{
      bot_name,
      bot_rating
    }
  }
}
</script>

<style>
</style>
