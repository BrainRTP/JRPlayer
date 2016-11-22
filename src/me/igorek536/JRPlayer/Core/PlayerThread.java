package me.igorek536.JRPlayer.Core;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import me.igorek536.JRPlayer.Globals.Constants;
import me.igorek536.JRPlayer.Globals.GlobalVars;

import java.net.URL;
import java.net.URLConnection;

import static me.igorek536.JRPlayer.Core.BaseUtils.setUrl;
import static me.igorek536.JRPlayer.Core.BaseUtils.showLnMessage;

class PlayerThread extends Thread {
    static Player player = null;

    public void run() {
        while (true) {
            try {
                if (GlobalVars.ID != null) {
                    URLConnection urlConnection = new URL(setUrl(GlobalVars.ID, 0)).openConnection();
                    urlConnection.connect();
                    player = new Player(urlConnection.getInputStream ());
                } else {
                    showLnMessage(Constants.PROG_ERROR_PLAYER);
                }

            } catch (Exception e) {
                    System.out.println(e.getMessage());
            }

            try {
                assert player != null;
                player.play();
            } catch (JavaLayerException e) {
                showLnMessage(Constants.PROG_ERROR_PLAYER);
                showLnMessage(e.getMessage());
            }

            try {
                Thread.sleep(1000); //очень важно паузы делать
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // very important
                break;
            }
        }
    }


}
