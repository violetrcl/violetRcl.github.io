package csu.lch.violetapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import csu.lch.violetapiclientsdk.model.User;
import csu.lch.violetapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用接口的客户端
 *
 * @author violetRcl
 */

public class VioletAPIClient {

    /**
     * 调用接口的标识
     */
    private String accessKey;

    /**
     * 密钥（复杂、无规律）
     */
    private String secretKey;

    public VioletAPIClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    //url传参
    public String getNameByPost(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    //Restful
    public String getNameByPost(@RequestBody User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                .charset(StandardCharsets.UTF_8)// TODO: 2023/4/3 这里有个中文乱码问题没有解决
                .addHeaders(setHeader(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }

    /**
     * 设置请求头
     *
     * @param body 用户参数
     * @return 请求头
     */
    public Map<String, String> setHeader(String body){
        Map<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);    //调用接口的标识
        headers.put("body", body);  //用户参数
        headers.put("sign", SignUtils.getSign(body, secretKey));       //签名，即加密密钥
        headers.put("nonce", RandomUtil.randomNumbers(4));     //临时数，用随机数生成
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));    //时间戳，定期清理临时数，减小存储压力
        return headers;
    }
}
