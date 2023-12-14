<template>
    <div>
      <!-- {{ days }}天{{ hours }}小时{{ minutes }}分{{ seconds }}秒 -->
      倒计时{{ seconds }}s
    </div>
  </template>
  
  <script>
  export default {
    name: 'CountDown',
    props: {
      targetTime: {
        type: Date, // 目标时间
        required: true
      }
    },
    data () {
      return {
        days: 0,
        hours: 0,
        minutes: 0,
        seconds: 0,
        timer: null
      }
    },
    methods: {
      updateTime () {
        let restTime = Date.parse(this.targetTime) - Date.now()
        if (restTime < 0) {
          clearInterval(this.timer)
          return
        }
        this.days = Math.floor(restTime / 1000 / 60 / 60 / 24)
        this.hours = Math.floor(restTime / 1000 / 60 / 60 % 24)
        this.minutes = Math.floor(restTime / 1000 / 60 % 60)
        this.seconds = Math.floor(restTime / 1000 % 60)
      },
      // 重设倒计时，若 autoStart 为 true，重设后会自动开始倒计时
      reset() {
        this.pause()
        this.remainTime = this.time
        this.setRemainTime(this.remainTime)
        if (this.autoStart) {
            this.start()
        }
      },
      // 暂停倒计时
      pause() {
        this.runing = false;
        this.clearTimeout()
      },
    },
    mounted () {
      this.updateTime()
      this.timer = setInterval(() => {
        this.updateTime()
      }, 1000)
    }
  }
  </script>