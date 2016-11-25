package me.igorek536.JRPlayer.Core;


import me.igorek536.JRPlayer.Globals.Constants;
import me.igorek536.JRPlayer.Globals.GlobalVars;
import me.igorek536.JRPlayer.Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class BaseUtils {
    /**
     * В этом классе хранятся базовые функции, благодаря которым программа вообще работает.
     * Не в коем случае не изменять и не трогать!
     *
     *
     *
     */

    private static Thread playerThread = new PlayerThread();

    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm:ss");
        return format1.format(date);
    }
    public static String getDatestart() {
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return format1.format(date);
    }

    public static void showLnMessage(String msg){
        System.out.println(msg);
    }
    public static void showMessage(String msg){
        System.out.print(msg);
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static void exit() {
        System.exit(0);
    }

    public static void play() {
        if (GlobalVars.ID != null) {
            if (!(playerThread.isAlive())) {
                if (getId(GlobalVars.ID)) {
                    playerThread = new PlayerThread();
                    playerThread.start();
                    showMessage(Constants.PROG_PLAYER_PLAY);
                    showMessage(getJson(1).get(GlobalVars.ID)+ "\n");
                } else {
                    showLnMessage("No Global var found!");
                }
            } else {
                showLnMessage(Constants.PROG_PLAYER_PLAYING);
            }
        } else {
            showLnMessage("GLobal Var is null!");
        }
    }
    public static void stop() {
        if (playerThread.isAlive()) {
            PlayerThread.player.close();
            playerThread.interrupt();
            showLnMessage(Constants.PROG_PLAYER_STOP);
        } else {
            showLnMessage(Constants.PROG_PLAYER_STOPING);
        }
    }
    public static void restart(){
        try {
            stop(); sleep(500);
            play();
        } catch (InterruptedException e) {
            showLnMessage(Constants.PROG_ERROR_PLAYER);
            e.printStackTrace();
        }
    }
    public static void status() {
        if (playerThread.isAlive()) {
            showMessage(Constants.PROG_PLAYER_PLAY);
            showMessage(getJson(1).get(GlobalVars.ID)+ "\n");
        } else {
            showLnMessage(Constants.PROG_PLAYER_STOP);
        }
    }
    public static Map<String, String> getJson(int param){
        Map< String, String > urlmap = new HashMap<>();
        Map< String, String > namemap = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/" + Constants.PROG_FILELIST_NAME));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray radiolist = (JSONArray) jsonObject.get("radiolist");
            for (Object aRadiolist : radiolist) {
                JSONObject slide = (JSONObject) aRadiolist;
                String name = (String) slide.get("Name");   //не используется
                String url = (String) slide.get("URL");
                String id = (String) slide.get("Id");
                urlmap.put(id, url);
                namemap.put(id, name);
            }
        } catch (IOException | ParseException ignored) {
        }
        if (param == 0) {
            return urlmap;
        } else {
            return namemap;
        }
    }

    public static boolean getId(String param) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/" + Constants.PROG_FILELIST_NAME));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray radiolist = (JSONArray) jsonObject.get("radiolist");
            int i = 0;
            String[][] arr = new String[radiolist.size()][1];
            for (Object aRadiolist : radiolist) {
                JSONObject slide = (JSONObject) aRadiolist;
                String id = (String) slide.get("Id");
                arr[i][0] = id;
                i++;
            }
            for (int j=0; j<radiolist.size(); j++) {
                if (param.equals(arr[j][0])) {
                    return true;
                }
            }
        } catch (IOException | ParseException ignored) {
        }
        return false;
    }

    public static void printJson(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/" + Constants.PROG_FILELIST_NAME));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray radiolist = (JSONArray) jsonObject.get("radiolist");
            showLnMessage("  \033[32mId" + "      " + "Name" + "                                " + "URL\033[32m");
            showLnMessage("=============================================================================================");
            int i = 0;
            String[][] arr = new String[radiolist.size()][3];
            for (Object aRadiolist : radiolist) {
                JSONObject slide = (JSONObject) aRadiolist;
                String name = (String) slide.get("Name");
                String url = (String) slide.get("URL");
                String id = (String) slide.get("Id");
                arr[i][0] = id + "\033[0m";
                arr[i][1] = "\033[34m" + name + "\033[0m";
                arr[i][2] = "\033[37m" + url + "\033[0m";
                i++;
            }
            printFormat(arr, radiolist.size());
            showLnMessage("\033[32m=============================================================================================\033[0m");
        } catch (IOException | ParseException ignored) {
        }
    }
    public static void help(){
        showLnMessage("\033[0m================================== Помощь ==================================\033[0m");
        showLnMessage("\033[31mlist\033[0m - Список радиостанций");
        showLnMessage("\033[31mhelp\033[0m - Помощь");
        showLnMessage("\033[31mselect [ID]\033[0m - Выбрать радиостанцию");
        showLnMessage("\033[31mplay\033[0m - Воспроизвести выбранную радиостанцию");
        showLnMessage("\033[31mstop\033[0m - Остановить воспроизведение радиостанции");
        showLnMessage("\033[31mstatus\033[0m - Посмотреть статус радио");
        showLnMessage("\033[31mreplay\033[0m - Перезапустить радиостанцию");
        showLnMessage("\033[31mexit\033[0m - Выход");
        showLnMessage("Что бы послушать радио выбери радиостанцию '\033[31mselect [id]\033[0m' и пропиши '\033[31mplay\033[0m'");
        showLnMessage("\033[0m=============================================================================\033[0m");
    }

    private static void printFormat(String[][] s, int size) {
        //за помощь в написании функции спасибо ув. MatRiZzA
        int id = 0;
        int name = 0;
        for (int i = 0; i<size; i++) {
            if(s[i][0].length() > id) {
                id = s[i][0].length();
            }
            if(s[i][1].length() > name) {
                name = s[i][1].length();
            }
        }

        for(int i = 0; i<size; i++){
            int mizya;
            mizya = id - s[i][0].length();
            for(int j=0; j<mizya; j++){
                s[i][0] = s[i][0]+" ";
            }
            mizya = name - s[i][1].length();
            for(int j=0; j<mizya; j++){
                s[i][1] = s[i][1]+" ";
            }
        }
        for(int i=0; i<size; i++){
            showLnMessage("\033[31m# " + s[i][0] + "      " + s[i][1] + "        " + s[i][2]);
        }
    }

    public static String setUrl(String id, int param) {
        Map<String, String> urlMap = getJson(0);
        if (urlMap != null) {
            return urlMap.get(id);
        } else {
            // получили null - теперь уточним значение ключа (есть ли вообще такой ключ)
            assert false;
            if (urlMap.containsKey(id)) {
                showLnMessage("Key = null");
            } else {
                showLnMessage("No Key!");
            }
        }
        if (param == 1) {
            Map<String, String> nameMap = getJson(1);
            if (nameMap != null) {
                return nameMap.get(id);
            } else {
                assert false;
                if (urlMap.containsKey(id)) {
                    showLnMessage("Key = null");
                } else {
                    showLnMessage("No Key!");
                }
            }
        }
        return null;
    }


    public static void genFile(int param) throws Exception {
        try {
            File radiolist = new File(Constants.PROG_FILELIST_NAME);
            if (param == 0) {
                if (!(radiolist.exists() && radiolist.isFile())) {
                    ExportResource("/" + Constants.PROG_FILELIST_NAME);
                } else {
                    showLnMessage(Constants.PROG_FILELIST_EXISTS);
                }
            } else {
                ExportResource("/" + Constants.PROG_FILELIST_NAME);
                showLnMessage(Constants.PROG_FILELIST_CREATED);
            }
        } catch (Exception e) {
            showLnMessage(Constants.PROG_FILELIST_ERROR);
        }
    }

    public static String ExportResource(String resourceName) throws Exception {   //Функция выгрузки ресурса из .jar файла в рабочую директорию
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = Main.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } finally {
            assert stream != null;
            stream.close();
            assert resStreamOut != null;
            resStreamOut.close();
        }
        return jarFolder + resourceName;
    }
}
