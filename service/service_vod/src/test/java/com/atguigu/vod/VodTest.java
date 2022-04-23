package com.atguigu.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/12-10:29
 * @VERSION: 1.0
 */
public class VodTest {
    // 一、视频文件上传
    // 视频标题(必选)
    public String title = "测试标题";
    // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
    // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
    // 3.流式上传时，文件名称为源文件名，如文件名称.mp4(必选)。
    // 任何上传方式文件名必须包含扩展名
    public String fileName = "C:\\Users\\dewan\\Desktop\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";

    public String accessKeyId = "LTAI5t7sBzLXQxmYvTmDm6Se";
    public String accessKeySecret = "ftuwXxJNG0o4Y7O25C8zT3ARTkNd1Z";


    public static void main(String[] args) throws ClientException {
        //初始化
        DefaultAcsClient client = VodInit.initVodClient("LTAI5t7sBzLXQxmYvTmDm6Se", "ftuwXxJNG0o4Y7O25C8zT3ARTkNd1Z");
        //创建请求响应
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //设置请求
        request.setVideoId("7046dff92478433488fbb8c096256c48");
        //发送请求得到响应
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo: playInfoList){
            System.out.println(playInfo.getPlayURL());
        }
        System.out.println(response.getVideoBase().getTitle());
    }
    @Test
    public void getVideoKey() throws ClientException {
        DefaultAcsClient client = VodInit.initVodClient("LTAI5t7sBzLXQxmYvTmDm6Se", "ftuwXxJNG0o4Y7O25C8zT3ARTkNd1Z");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("7046dff92478433488fbb8c096256c48");
        response =client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());


    }
    @Test
    public void testUpload(){
        this.testUploadVideo(accessKeyId,accessKeySecret,title,fileName);

    }

    @Test
    public void test(){
        List list = Arrays.asList(1, 2, 3);
        String videoIds= StringUtils.join( list, ",");
        System.out.println(videoIds);

    }
    @Test
    public void testString(){
        String s = new String("233");
        s.intern();
        String s3 = "233";
        System.out.println(s == s3);

        String s4 = new String("233")+new String(("1"));
        s4.intern();
        String s5 = "2331";
        System.out.println(s4 == s5);
//        char[] chars = new char[]{'1','2','3'};
        char[] chars = new char[]{49,50,51};

        String s6 = new String(chars,0,3);
        System.out.println(s6);
        String s8 = new String("1")+new String(("23"));

        s8.intern();
        String s7 = "123";
        System.out.println(s8 == s7);


//        new StringBuilder();
//        new StringBuffer(); 安全的


    }

        public  void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
