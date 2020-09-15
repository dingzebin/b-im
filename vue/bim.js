/**
 * 
 */
class Bim {
  // 连接实例
  socket = null;
  // 服务器地址
  server = '';
  // 连接成功回调函数
  onOpenCall = null;
  // 连接关闭回调函数
  onCloseCall = null;
  // 消息回调函数
  onMessageCall = null;

  constructor(params) {
    if (!window.WebSocket) {
      window.WebSocket = window.MozWebSocket;
    }
    if(window.WebSocket){
      alert("您的浏览器不支持WebSocket");
      return;
    }
    this.server = params.server;
    this.onOpenCall = params.onopen;
    this.onCloseCall = params.onclose;
    this.onMessageCall = params.onmessage;
  }
  /**
   * 连接
   */
  connect() {
    this.socket = new WebSocket(this.server);
    this.socket.onopen = function(event){
      this.onOpenCall('websocket connected')
    };    
    this.socket.onmessage = function(event){
      this.onMessageCall(event.data);
    };
    this.socket.onclose = function(event){
      this.onCloseCall('websocket closed')
    };
  }
}