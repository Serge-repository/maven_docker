package utils;

public enum Browser {
    CHROME,
    FIREFOX;

    private static Browser browser;

    public static Browser getBrowser(){
        if (browser==null){
            browser = Browser.valueOf(System.getProperty("browser", "chrome").toUpperCase());
        }
        return browser;
    }
}
