package org.easyarch.netcat.mvc.router;

import org.easyarch.netcat.kits.StringKits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-12
 * 下午2:45
 * description:
 */

public class Router {

    private static final String SEPARATOR = "/";
    private static final String QUESTION = "?";

    private static final String LEFT = "{";
    private static final String RIGHT = "}";

    private String path;

    private List<String> segements;

    private Map<Integer,String> parameterizeUrl;

    private Map<String,String> pathParams;

    private boolean parameterize;

    private int level;

    /**
     * 地址最后一个不带分隔符
     * @param router
     */
    public Router(String router){
        this.path = checkURL(router);
        this.parameterizeUrl = new HashMap<>();
        this.pathParams = new HashMap<>();
        this.segements = new ArrayList<>();
        parse(router);
    }

    private String checkURL(String url){
        if (StringKits.isEmpty(url)){
            return null;
        }
        String path = null;
        if (url.endsWith(SEPARATOR)){
            path = url.substring(0,url.length() - 1);
        }else{
            path = url;
        }
        int index = url.lastIndexOf(QUESTION);
        if (index == -1){
            return path;
        }
        return path.substring(0,index);
    }

    private void parse(String path){
        if (StringKits.isEmpty(path)){
            return;
        }
        String[] segements = path.split(SEPARATOR);
        for (String block:segements){
            if (block.startsWith(LEFT)&&block.endsWith(RIGHT)){
                this.parameterize = true;
                parameterizeUrl.put(level,block);
            }
            if (!StringKits.isEmpty(block)){
                this.level++;
                this.segements.add(block);
            }
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getParameterizeSize(){
        return parameterizeUrl.size();
    }

    public boolean isParameterize() {
        return parameterize;
    }

    public void setParameterize(boolean parameterize) {
        this.parameterize = parameterize;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<String,String> getPathParams(){
        return this.pathParams;
    }

    public static int getLevel(String path){
        if (StringKits.isEmpty(path)){
            return 0;
        }
        String[] segements = path.split(SEPARATOR);
        int level = 0;
        for (String block:segements){
            if (!StringKits.isEmpty(block)){
                level++;
            }
        }
        return level;
    }

    /**
     * 1.路由层级不同，不相等
     * 2.路由地址相同必定相等
     * 3.路由包括参数化片段，则进一步匹配;如果不包括，不相等
     * 4.匹配每个片段，如果除了参数化片段都相等，则相等
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Router)) {
            return false;
        }

        Router router = (Router) o;
        return eq(router);
    }

    private boolean eq(Router router){
        if (this.level != router.level){
            return false;
        }
        if (this.path.equals(router.path)){
            return true;
        }else if (this.isParameterize()||router.isParameterize()){
            for (int index=0;index<level;index++){
                String seg1 = segements.get(index);
                String seg2 = router.segements.get(index);
                String paramSeg1 = parameterizeUrl.get(index);
                String paramSeg2 = router.parameterizeUrl.get(index);
                //两个片段不相等，或当前片段不是参数化的,则不相等
                if (!seg1.equals(seg2)&&(paramSeg1 == null&&paramSeg2 == null)){
                    return false;
                }
                if (paramSeg1 != null){
                    String name = StringKits.strip(paramSeg1,LEFT,RIGHT);
                    this.pathParams.put(name,seg2);
                    router.pathParams.put(name,seg2);
                }else if (paramSeg2 != null){
                    String name = StringKits.strip(paramSeg2,LEFT,RIGHT);
                    this.pathParams.put(name,seg1);
                    router.pathParams.put(name,seg1);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return level;
    }

    @Override
    public String toString() {
        return "Router{" +
                "path='" + path + '\'' +
                ", level=" + level +
                ", segements=" + segements +
                '}';
    }

    public static void main(String[] args) {
        Router router1 = new Router("/image/00001");
        Router router2 = new Router("/image/{id}");
        long start = System.nanoTime();
        System.out.println(router2.equals(router1));
        long end = System.nanoTime();
        System.out.println(end-start);
    }
}
//377899
//227892