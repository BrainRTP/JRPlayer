package me.igorek536.JRPlayer;

import me.igorek536.JRPlayer.Globals.Constants;
import me.igorek536.JRPlayer.Globals.GlobalVars;

import java.util.Scanner;

import static java.lang.Thread.sleep;
import static me.igorek536.JRPlayer.Core.BaseUtils.*;

@SuppressWarnings("InfiniteLoopStatement")
public class Main {
    public static void main(String[] args) throws Exception {

        showLnMessage(Constants.PROG_LAUNCHED + getDatestart());
        showLnMessage(Constants.PROG_TYPE_HELP);
        genFile(0);
        printCursor();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            String[] arg = cmd.split(" ");
            switch (arg[0]) {
                case "stop": {
                    stop();
                    sleep(100);
                    printCursor();
                    break;
                }
                case "play": {
                    play();
                    sleep(100);
                    printCursor();
                    break;
                }
                case "exit": {
                    exit();
                    sleep(100);
                    printCursor();
                    break;
                }
                case "replay": {
                    restart();
                    sleep(100);
                    printCursor();
                    break;
                }
                case "status": {
                    status();
                    sleep(100);
                    printCursor();
                    break;
                }
                case "help": {
                    help();
                    sleep(100);
                    printCursor();
                    break;
                }
                case "list": {
                    printJson();
                    sleep(100);
                    printCursor();
                    break;
                }
                case "genfile": {
                    genFile(1);
                    sleep(100);
                    printCursor();
                    break;
                }
                case "select": {
                    try {
                        if (isStringInt(arg[1])) {
                            if ((getId(arg[1]))) {
                                GlobalVars.ID = arg[1];
                                showLnMessage("\033[32mOK!\033[0m");
                            } else {
                                showLnMessage("ERR!");
                            }
                        } else {
                            showLnMessage(Constants.PROG_ERROR_SELECT);
                        }
                    } catch (Exception e) {
                        showLnMessage(Constants.PROG_ERROR_SELECT);
                    }
                    sleep(100);
                    printCursor();
                    break;
                }
                default:
                    showLnMessage(Constants.PROG_ERROR_COMAND);
                    printCursor();
            }
        }
    }
    private static void printCursor() {
        showMessage("\033[0m[" + getDate() + "\033[0m]" + " >_ ");
    }
}
