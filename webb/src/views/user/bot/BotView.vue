<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top:20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%; ">
                        <!-- <el-form-item>
                            <el-upload action="http://localhost:3000/api/user/account/uploadpic/" 
                            :on-success="successUpload"
                            :headers="headersobj"
                            >
                                <el-button size="big" type="primary" style="margin:0 auto; margin-top: 0px;">
                                    更换头像
                                </el-button>
                            </el-upload>
                        </el-form-item> -->
                    </div>
                    <!-- <div class="card-body"> -->
                    
                        <!-- <form id="form_submit_photo" role="form" action="http://localhost:3000/api/user/account/uploadpic" method="post" enctype="multipart/form-data">
                            <input type="file" id="fileInput" name="file"><br>
                            <input type="submit" value="提交">
                        </form> -->
                    
                        <!-- <form id="uploadForm" enctype="multipart/form-data">
                            <input type="file" id="fileInput" name="file" @change="handleFileChange">
                            <button type="button" @click="getFileName">Uploadname</button>
                            <p v-if="selectedFileName">Selected File111: {{ selectedFileName }}</p>
                        </form> -->
                        
                        <!-- <form id="form_submit_photo" class="form-horizontal" role="form" action="/api/user/account/uploadpic/" method="post" enctype="multipart/form-data" @click="change_photo">
                            <input type="hidden" name="csrfmiddlewaretoken" value="VS0yeFlgqBXH8256Dy7YGIVOsFEhkCWuTEBW3P5GUjoSBc1aTbAtvmdyzgmVdBtZ">
                            <label class="btn btn-default btn-file" style="width: 100%;">
                                更新头像
                            <input id="ingredient_file" type="file" accept="image/*" style="display: none;" name="photo" required>
                            </label>
                            <button type="button" @click="getFileName">Upload</button>
                        </form> -->
                    <!-- </div> -->
                </div>

                <div class="card" style="margin-top:10px;">
                    <!-- <div class="card-body">
                        <button type="button" class="btn btn-success float-end" style="width: 40%; margin-right:80px;">
                            更换头像
                        </button>   
                    </div> -->
                    <el-form-item  style="margin:0 auto; margin-top: 0px;">
                        <el-upload action="https://www.jeflee.xyz/api/user/account/uploadpic/" 
                        :on-success="successUpload"
                        :headers="headersobj"
                        >
                        <!-- <vue-cropper autoCrop img="https://shnhz.github.io/shn-ui/img/Koala.jpg" ref="cropper" centerBox :fixedNumber="[2,1]"/> -->
                        <!-- <VueCropper
                            ref="cropperAvatar"
                            class="avatar-cropper"
                            style="height: 250px"
                            :img="imgData"
                            :output-size="outputSize"
                            :output-type="'outputType'"
                            :fixed-number="fixedNumber"
                            :auto-crop="autoCrop"
                            :fixed="fixed"
                            :center-box="true"
                            :info="false"
                            :info-true="true"
                            :fixed-box="true"
                            :can-move-box="false"
                            @touchmove.prevent
                        ></VueCropper> -->
                            <el-button size="big" type="primary" style="width: 100%; height:100%;">
                                更换头像
                            </el-button><br>
                        </el-upload>
                    </el-form-item>
                </div>            
            </div>
            
            <div class="col-9">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size: 120%">my bot</span>
                        <button type="button" class="btn btn-success float-end" data-bs-toggle="modal" data-bs-target="#bot_add_btn">
                            创建
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="bot_add_btn" tabindex="-1">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">创建bot</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add_bot_title" class="form-label">名称</label>
                                            <input v-model="botadd.title" type="text" class="form-control" id="add_bot_title" placeholder="请输入bot名称">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add_bot_description" class="form-label">简介</label>
                                            <textarea v-model="botadd.description" class="form-control" id="add_bot_description" rows="1" placeholder="请输入bot简介"></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="add_bot_code" class="form-label">代码</label>
                                            <VAceEditor
                                                v-model:value="botadd.content"
                                                @init="editorInit"
                                                lang="c_cpp"
                                                theme="textmate"
                                                style="height: 250px" 
                                                :options="{
                                                    enableBasicAutocompletion: true, // 启用基本自动完成
                                                    enableSnippets: true,            // 启用代码段
                                                    enableLiveAutocompletion: true,  // 启用实时自动完成
                                                    fontSize: 18,                    // 设置字号
                                                    tabSize: 4,                      // 标签大小
                                                    showPrintMargin: false,          // 去除编辑器里的竖线
                                                    highlightActiveLine: true,
                                                }" />
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error_message">{{ botadd.error_message }}</div>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                        <button type="button" class="btn btn-primary" @click="add_bot">创建</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-header">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>描述</th>
                                    <th>操作</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createtime }}</td>
                                    <td>{{ bot.description }}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right:10px;" data-bs-toggle="modal" :data-bs-target="'#update_bot_modal_' + bot.id">修改</button>
                                        
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update_bot_modal_' + bot.id" tabindex="-1">
                                            <div class="modal-dialog modal-xl">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">创建bot</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add_bot_title" class="form-label">名称</label>
                                                            <input v-model="bot.title" type="text" class="form-control" id="add_bot_title" placeholder="请输入bot名称">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add_bot_description" class="form-label">简介</label>
                                                            <textarea v-model="bot.description" class="form-control" id="add_bot_description" rows="3" placeholder="请输入Bot简介"></textarea>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add_bot_code" class="form-label">代码</label>
                                                            <v-ace-editor
                                                                v-model:value="bot.content"
                                                                @init="editorInit"
                                                                lang="c_cpp"
                                                                theme="textmate"
                                                                style="height: 300px" 
                                                                :options="{
                                                                    enableBasicAutocompletion: true, // 启用基本自动完成
                                                                    enableSnippets: true,            // 启用代码段
                                                                    enableLiveAutocompletion: true,  // 启用实时自动完成
                                                                    fontSize: 18,                    // 设置字号
                                                                    tabSize: 4,                      // 标签大小
                                                                    showPrintMargin: false,          // 去除编辑器里的竖线
                                                                    highlightActiveLine: true,
                                                            }" />
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ bot.error_message }}</div>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                                        <button type="button" class="btn btn-primary" @click="update_bot(bot)">保存修改</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <button type="button" class="btn btn-danger" style="margin-right:10px;" @click="remove_bot(bot)">删除</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// 获取bot列表
import { ref, reactive } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';

import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-chrome';
import 'ace-builds/src-noconflict/ext-language_tools';

// import 'vue-cropper/dist/index.css'
// import { VueCropper }  from "vue-cropper";

export default{
    components: {
        VAceEditor,
        // VueCropper,
    },
    methods: {
        handleFileChange(event) {
            this.selectedFile = event.target.files[0];
            const filename = this.selectedFile.name;
            const store = useStore();
            console.log("handle file:", filename);
            $.ajax({
                url: "https://www.jeflee.xyz/api/user/account/uploadpic/",
                type: "post",
                data:{
                    filename: filename,
                    photo: filename,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        console.log("111111111");
                    } else {
                        console.log("000000000");
                    }
                }
            })
        },
        successUpload(res) {
            console.log(res);
            console.log("1111111111111");
        },  
    },       
    data() {
        return {
            selectedFile: null,
        };
    },

    setup(){
        ace.config.set(
            "basePath", 
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")
        const store = useStore();
        const botadd = reactive({
            title: "",
            description: "",
            content: "",
            error_message: "",
        });

        let bots = ref([]);

        const refresh_bots = () => {
            $.ajax({
                url: "https://www.jeflee.xyz/api/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp;
                }
            })
        }
        refresh_bots();

        const add_bot = () => {
            botadd.error_message = "",
            $.ajax({
                url: "https://www.jeflee.xyz/api/user/bot/add/",
                type: "post",
                data:{
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    if(resp.error_message == "success") {
                        botadd.title = "";
                        botadd.description= "";
                        botadd.content="";
                        Modal.getInstance("#bot_add_btn").hide();
                        refresh_bots();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }
            })
        }

        const update_bot = (bot) => {
            bot.error_message = "",
            $.ajax({
                url: "https://www.jeflee.xyz/api/user/bot/update/",
                type: "post",
                data:{
                    bot_id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance('#update_bot_modal_' + bot.id).hide();
                        refresh_bots();
                    } else {
                        bot.error_message = resp.error_message;
                    }
                }
            })
        }

        const remove_bot = (bot) => {
            $.ajax({
                url: "https://www.jeflee.xyz/api/user/bot/remove/",
                type: "post",
                data:{
                    bot_id: bot.id,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    if(resp.error_message == "success"){
                        refresh_bots();
                    }
                }
            })
        }

        const change_photo = (bot) => {
            alert("更换头像");
            bot.error_message = "",
            $.ajax({
                url: "https://www.jeflee.xyz/api/user/account/uploadpic/",
                type: "post",
                data:{
                    // photo: photoname,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_bots();
                    } else {
                        bot.error_message = resp.error_message;
                    }
                }
            })
        }

        const getFileName = () => {
            const fileInput = document.getElementById('fileInput');
            const fileName = fileInput.files[0].name;
            // const fileinfo = fileInput.files;

            console.log('Selected File:', fileName);

            $.ajax({
                url: "https://www.jeflee.xyz/api/user/account/uploadpic/",
                type: "post",
                data:{
                    filename: fileName,
                    photo: fileName,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        console.log("111111111");
                    } else {
                        console.log("000000000");
                    }
                }
            })
        }

        const headersobj = {
            "Authorization": 'Bearer ' + store.state.user.token,
        }

        return {
            bots,
            botadd,
            add_bot,
            update_bot,
            remove_bot,
            change_photo,
            getFileName,
            headersobj,
        }
    },


}
</script>

<style scoped>
div.error_message {
    color: red;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
