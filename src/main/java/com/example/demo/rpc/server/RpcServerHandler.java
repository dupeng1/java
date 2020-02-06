package com.example.demo.rpc.server;

import com.example.demo.rpc.RpcRequest;
import com.example.demo.rpc.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class RpcServerHandler implements Runnable{
    //客户端网络请求socket，可以从中获得网络请求信息
    private Socket clientSocket;
    //服务端提供处理请求的类集合
    private Map<String, Object> serviceMap;

    /**
     *
     * @param clientSocket  客户端socket
     * @param serviceMap    所有服务
     */
    public RpcServerHandler(Socket clientSocket, Map<String, Object> serviceMap) {
        this.clientSocket = clientSocket;
        this.serviceMap = serviceMap;
    }

    public void run(){
        ObjectInputStream oin = null;
        ObjectOutputStream oout = null;
        RpcResponse response = new RpcResponse();
        try{
            //1、获取流以待操作
            oin = new ObjectInputStream(clientSocket.getInputStream());
            oout = new ObjectOutputStream(clientSocket.getOutputStream());
            //2、从网络io输入流中获取请求数据，强转参数类型
            Object param = oin.readObject();
            RpcRequest request = null;
            if(!(param instanceof RpcRequest)){
                response.setError(new Exception("参数错误"));
                oout.writeObject(response);
                oout.flush();
                return;
            } else{
                request = (RpcRequest) param;
            }
            //3、查找并执行服务
            Object service = serviceMap.get(request.getClassName());
            Class<?> clazz = service.getClass();
            Method method = clazz.getMethod(request.getMethodName(),request.getParamTypes());
            Object result = method.invoke(service,request.getParams());
            //4、返回RPC响应，序列化RpcResponse
            response.setResult(result);
            //序列化结果
            oout.writeObject(response);
            oout.flush();
            return;
        } catch (Exception e){
            try{
                if(oout != null){
                    response.setError(e);
                    oout.writeObject(response);
                    oout.flush();
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
            return;
        } finally {
            try {	// 回收资源，关闭流
                if(oin != null) oin.close();
                if(oout != null) oout.close();
                if(clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
