package com.suyong.kakaobot;

import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private static final FileManager ourInstance = new FileManager();

    public static final File HOME = Environment.getExternalStorageDirectory();
    public static final File KAKAOBOT_HOME = new File(HOME, "KakaoBot");
    public static final String SCRIPT_NAME = "KakaoBot.js";


    public static FileManager getInstance() {
        return ourInstance;
    }

    private FileManager() {}

    public void init() {
        KAKAOBOT_HOME.mkdirs();
    }

    public void createFile(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(KAKAOBOT_HOME, path)));

            writer.write("{}");

            writer.close();
        } catch(IOException err) {
            err.printStackTrace();
        }
    }

    public void saveData(String path, String key, Object params) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(KAKAOBOT_HOME, path)));

            String line;
            StringBuilder result = new StringBuilder();
            while((line = reader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }

            JSONObject json = new JSONObject(result.toString() + "");
            json.put(key, params);

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(KAKAOBOT_HOME, path)));

            writer.write(json.toString());

            reader.close();
            writer.close();
        } catch(IOException err) {
            err.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Object readData(String path, String key) {
        String result = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(KAKAOBOT_HOME, path)));

            String line;
            while((line = reader.readLine()) != null) {
                JSONObject json = new JSONObject(line + "");

                if(json.has(key)) {
                    result = json.get(key) + "";
                }
            }

            reader.close();
        } catch(IOException err) {
            err.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Object read(String path) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));

            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }

            reader.close();
        } catch(IOException err) {
            err.printStackTrace();
        }

        return result.toString();
    }

    public String readScript() {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(KAKAOBOT_HOME, SCRIPT_NAME)));

            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }

            reader.close();
        } catch(IOException err) {
            err.printStackTrace();
        }

        return result.toString();
    }

    private JSONObject read(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            JSONObject json = new JSONObject();
            while((line = reader.readLine()) != null) {
                json = new JSONObject(line);
            }

            reader.close();

            return json;
        } catch(IOException err) {}
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
