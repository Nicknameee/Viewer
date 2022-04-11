package application.data.loadableResources.models;

public enum ResourceType {
    IMAGE(1) , VIDEO(2);

    private Integer key;

    ResourceType(Integer key) {
        this.key = key;
    }
}