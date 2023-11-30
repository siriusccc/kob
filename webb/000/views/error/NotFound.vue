<template>
    <ContentField>
        404 This page could not be found.
    </ContentField>
    <div>
        <ul class="me-auto mb-lg-0">
        <router-link to="/pk/">返回首页</router-link>   
        </ul>
    </div>
</template>

<script>
import ContentField from '../../components/ContentField.vue'

export default{
    components:{
        ContentField
    }
}
</script>

<style scoped>
</style>