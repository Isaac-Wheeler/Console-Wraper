package mcc.Player;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Player {

    String uuid, name, rank;
    Ops op;
    URI playerAvatar;

    private void loadSkin() {
        // Source: https://crafatar.com/

        if(uuid == null){
            loadUUID();
        }

        try {
            this.playerAvatar = new URI("https://crafatar.com/avatars/" + uuid);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean loadUUID(){
        String uuid = UUIDGetter.getUUID(this.name);
        if(uuid == null){
            return false;
        }
        this.uuid = uuid;
        return true;
    }




    public Player(String name) {
        this.name = name;
        System.out.println(loadUUID());
        System.out.println(uuid);
        loadSkin();
    }


    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Ops getOp() {
        return this.op;
    }

    public void setOp(Ops op) {
        this.op = op;
    }

    public URI getPlayerAvatar() {
        return this.playerAvatar;
    }

    public void setPlayerAvatar(URI playerAvatar) {
        this.playerAvatar = playerAvatar;
    }



    @Override
    public String toString() {
        return "{" +
            " uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", rank='" + getRank() + "'" +
            ", op='" + getOp() + "'" +
            ", playerAvatar='" + getPlayerAvatar() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Player)) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name);
    }



}