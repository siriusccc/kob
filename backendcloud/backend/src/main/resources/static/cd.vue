<template>
  <div>
    <input type="text" v-model="code" placeholder="请输入验证码" />
    <button class="notGainBox" :class="{ 'yesGainBox': isCounting }" @click="verification">{{ buttonText }}</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      code: "", //输入框model绑定
      isCounting: false, //控制显示'获取按钮'还是'倒计时'
      countdown: 0, //计数器
    };
  },
  computed: {
    buttonText() {
      return this.isCounting ? `${this.countdown}秒后重新获取` : "获取验证码";
    },
  },
  methods: {
    verification() {
      if (this.isCounting) {
        return;
      }
      this.isCounting = true;
      this.countdown = 3; //赋值3秒
      const times = setInterval(() => {
        this.countdown--; //递减
        if (this.countdown === 0) {
          // === 0 变成获取按钮
          clearInterval(times);
          this.isCounting = false;
        }
      }, 1000);
    },
  },
};
</script>

<style scoped>
/* input输入框样式 */
input {
  width: 200px;
  padding: 0px 10px;
  height: 36px;
  border: none;
  border-radius: 4px;
  border: 1px solid #ccc;
  margin-right: 8px;
}

/* 默认获取验证码按钮样式 */
.notGainBox {
  border: none;
  background: #ff5000;
  color: #fff;
  height: 36px;
  padding: 0px 14px;
  border-radius: 4px;
  cursor: pointer;
}
/* 点击后获取验证码按钮样式 */
.yesGainBox {
  background: #ccc;
  cursor: not-allowed;
}
</style>
