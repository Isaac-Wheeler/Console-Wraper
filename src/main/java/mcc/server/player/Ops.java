package mcc.server.player;

import java.util.Objects;

public class Ops {
    String uuid, name;
    Boolean bypassesPlayerLimit;
    int level;


    public Ops() {
    }

    public Ops(String uuid, String name, Boolean bypassesPlayerLimit, int level) {
        this.uuid = uuid;
        this.name = name;
        this.bypassesPlayerLimit = bypassesPlayerLimit;
        this.level = level;
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

    public Boolean isBypassesPlayerLimit() {
        return this.bypassesPlayerLimit;
    }

    public Boolean getBypassesPlayerLimit() {
        return this.bypassesPlayerLimit;
    }

    public void setBypassesPlayerLimit(Boolean bypassesPlayerLimit) {
        this.bypassesPlayerLimit = bypassesPlayerLimit;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

   
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ops)) {
            return false;
        }
        Ops ops = (Ops) o;
        return Objects.equals(uuid, ops.uuid) && Objects.equals(name, ops.name) && Objects.equals(bypassesPlayerLimit, ops.bypassesPlayerLimit) && level == ops.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, bypassesPlayerLimit, level);
    }

    @Override
    public String toString() {
        return "{" +
            " \"uuid\"=\"" + getUuid() + "\"" +
            ", \"name\"=\"" + getName() + "\"" +
            ", \"bypassesPlayerLimit\"=\"" + isBypassesPlayerLimit() + "\"" +
            ", \"level\"=\"" + getLevel() + "\"" +
            "}";
    }
    
}
