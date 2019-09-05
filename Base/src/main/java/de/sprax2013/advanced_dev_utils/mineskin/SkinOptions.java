package de.sprax2013.advanced_dev_utils.mineskin;

public class SkinOptions {
    private final String URL_FORMAT = "name=%s&model=%s&visibility=%s";

    private final String name;
    private final Model model;
    private final Visibility visibility;

    public SkinOptions(String name, Model model, Visibility visibility) {
        this.name = name == null ? "" : name;
        this.model = model == null ? Model.DEFAULT : model;
        this.visibility = visibility == null ? Visibility.PRIVATE : visibility;
    }

    protected String toUrlParam() {
        return String.format(URL_FORMAT, this.name, this.model.getName(), this.visibility.getCode());
    }
}