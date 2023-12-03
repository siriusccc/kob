<template>
  <NavBar/>
  <router-view/>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

export default{
  components:{
    NavBar
  },
  setup(){
    // $.ajax({
    //   url: "http://localhost:3000/api/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "a",
    //     password: "pc",
    //     confirmedPassword: "pc",
    //   },
    //   success(resp){
    //     console.log(resp);
    //   },
    //   error(resp){
    //     console.log(resp);
    //   }
    // });
  }
}
</script>

<style>
body{
  background-image: url("@/assets/images/background.jpg");
  background-size: cover;
}
</style>


