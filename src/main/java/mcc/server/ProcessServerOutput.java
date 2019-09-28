package mcc.server;

import mcc.server.player.Player;
import mcc.DBot.DBot;
import mcc.server.State;

public class ProcessServerOutput {

    Server server;

    public ProcessServerOutput(Server server) {
        this.server = server;
    }

    public boolean processLine(String line) {

        String[] array = line.split("]", 3);

        if (array.length < 3) {
            System.out.println(line);
        } else {

            String time = array[0];
            String warnLevel = array[1];
            line = array[2].substring(2);

            DBot.getInstance().writeConsole(server.config.getChannelID(), line);

            if (line.contains("Done (") == true) {
                server.setState(State.RUNNING);
            } else if (line.contains("Stopping server") == true) {
                server.setState(State.STOPPING);
            } else if (line.contains("joined the game")) {
                array = line.split(" ");

                server.players.add(new Player(array[0]));

            } else if (line.contains("left the game")) {
                array = line.split(" ");
                server.players.remove(new Player(array[0]));
            }

        }

        return true;
    }

}