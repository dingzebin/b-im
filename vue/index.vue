<template>
  <div class="im">
    <el-col offset="6">
    <div class="mid">
      <!-- 头部 -->
      <div class="mid-header">
        名字
      </div>
      <!-- 聊天内容 -->
      <div class="mid-content">
        <div class="msg" v-for="item in messages" :key="item.id">
          <div :class="(item.ot == 1 ? 'otr' : 'm') + ' avt'">
            <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"/>
          </div>
          <div :class="(item.ot == 1 ? 'otr' : 'm') + ' ctn'">
            {{item.msg}}
          </div>
        </div>
      </div>
      <!-- 输入框 -->
      <div class="mid-imput">
        <el-input v-model="input1">
          <template slot="append">
            <el-button>发送</el-button>
          </template>
        </el-input>
      </div>
    </div>
    </el-col>
  </div>
</template>
<script>
export default {
  data() {
    return {
      messages: [
        {
          id: '1',
          msg: '在？',
          ot: 1
        },
        {
          id: '2',
          msg: '在',
          ot: 0
        },
        {
          id: '2',
          msg: '你是？',
          ot: 0
        },
        {
          id: '1',
          msg: '你爹',
          ot: 1
        }
      ],
      input1: ''
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.im {
  .mid {
    border: 1px solid #ECE4DE;
    width: 550px;
    height: 550px;
    .mid-header {
      width: 100%;
      height: 50px;
      border-bottom: 1px solid #ECE4DE;
      text-align: center;
      line-height: 50px;
    }
    .mid-content {
      height: 466px;
      .msg {
        min-height: 40px;
        margin-top: 2px;
        .otr {
          float: left;
          margin-left: 6px;
        }
        .m {
          float: right;
          margin-right: 6px;
        }
        .ctn {
          min-width: 10px;
          min-height: 38px;
          max-width: 250px;
          border: 1px solid #ECE4DE;
          word-wrap: break-word;
          padding: 2px 2px 2px 2px;
          border-radius: 6px;
          min-width: 40px;
          text-align: center;
        }
        img {
          height: 40px;
          width: 40px;
          border-radius: 50%;
        }
      }
    }
    .mid-imput {
      margin-bottom: 0px;
    }
  }
}
</style>
