package net.bigyous.gptgodmc.GPT.Json;

public class Parameter {
    private String type;
    private String description;

    public Parameter(String type, String description){
        this.type = type;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
