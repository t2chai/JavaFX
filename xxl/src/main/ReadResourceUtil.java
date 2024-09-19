package main;


import java.net.URL;

public class ReadResourceUtil {

    //读取音频
    public static URL readAudio(String name){
        return getUri("audio/wav01"+"/"+name);
    }

    //读取背景音
    public static URL readMusic(String name){
        return getUri("music/"+name);
    }

    //读取文件
    public static URL getUri(String name){
        try {
            return ReadResourceUtil.class.getClassLoader().getResource(name);
        }catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("fail read"+name);
            return null;
        }
    }

    /**
     * 读取图片
     */
//    private static String readSkinPath(boolean isRandom){
//        if(isRandom){
//            return "/static/skins/a" +random.nextInt(109);
//        }else{
//            return "/static/skins/a108";
//        }
//    }

    /**
     * 读取图片
     */
//    public static List<String> readSkin(boolean isRandom){
//        String path = readSkinPath(isRandom);
//        List<String> list = new ArrayList<>();
//        try {
//            URL url = getUri("/" + path);
//            if (url != null) {
//                try {
//                    final File apps = new File(url.toURI());
//                    for (File app : apps.listFiles()) {
//                        list.add(path+"/"+app.getName());
//                    }
//                } catch (URISyntaxException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }

}

